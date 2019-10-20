package org.fruitsalad.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.HeatMap;
import com.anychart.enums.SelectionMode;
import com.anychart.graphics.vector.SolidFill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.fruitsalad.R;
import org.fruitsalad.roomdb.UserDatabase;
import org.fruitsalad.utility.MockData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ir.alirezaiyan.progressbar.LevelProgressBar;

public class HomeFragment extends Fragment {

    private FloatingActionButton fab;
    private TextView textScore;
    private int score;
    private UserDatabase userDatabase;
    private final int INCREMENT_SCORE = 70;
    View root;
    ImageButton button_share;
    File imagePath;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);

        fab = root.findViewById(R.id.fab_increase_score);
        button_share = root.findViewById(R.id.fab_share);
        textScore = root.findViewById(R.id.textView_score);

        userDatabase = Room.databaseBuilder(getActivity().getBaseContext(), UserDatabase.class, "SaviourOfEarth")
                .build();
        initializeViewsTask();
        onFABClicked();

        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Do the file write

                    Bitmap bitmap = takeScreenshot();
                    saveBitmap(bitmap);
                    shareIt();
                } else {
                    // Request permission from the user
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                    Bitmap bitmap = takeScreenshot();
                    saveBitmap(bitmap);
                    shareIt();

                }
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AnyChartView anyChartView = getActivity().findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(getActivity().findViewById(R.id.progress_bar));

        HeatMap riskMap = AnyChart.heatMap();

        riskMap.stroke("1 #fff");
        riskMap.hovered()
                .stroke("6 #fff")
                .fill(new SolidFill("#545f69", 1d))
                .labels("{ fontColor: '#fff' }");

        riskMap.interactivity().selectionMode(SelectionMode.NONE);

        riskMap.title().enabled(true);
        riskMap.title()
                .text("Oxygen Heat Map")
                .padding(0d, 0d, 20d, 0d);

        riskMap.labels().enabled(true);
        riskMap.labels()
                .minFontSize(14d)
                .format("function() {\n" +
                        "      var namesList = [\"Low\", \"Medium\", \"High\", \"Extreme\"];\n" +
                        "      return namesList[this.heat];\n" +
                        "    }");

        riskMap.yAxis(0).stroke(null);
        riskMap.yAxis(0).labels().padding(0d, 15d, 0d, 0d);
        riskMap.yAxis(0).ticks(false);
        riskMap.xAxis(0).stroke(null);
        riskMap.xAxis(0).ticks(false);

        riskMap.tooltip().title().useHtml(true);
        riskMap.tooltip()
                .useHtml(true)
                .titleFormat("function() {\n" +
                        "      var namesList = [\"Low\", \"Medium\", \"High\", \"Extreme\"];\n" +
                        "      return '<b>' + namesList[this.heat] + '</b> Residual Risk';\n" +
                        "    }")
                .format("function () {\n" +
                        "       return '<span style=\"color: #CECECE\">Likelihood: </span>' + this.x + '<br/>' +\n" +
                        "           '<span style=\"color: #CECECE\">Consequence: </span>' + this.y;\n" +
                        "   }");

        riskMap.data(MockData.getMockHeatMapData());

        anyChartView.setChart(riskMap);
        super.onViewCreated(view, savedInstanceState);
    }


    private void onFABClicked() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ObjectDetection.class);
                startActivityForResult(intent, INCREMENT_SCORE);
            }
        });
    }

    private void increaseScoreTask() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                score = userDatabase.daoAccess().getScoreByName("Abhilash");
                score++;
                userDatabase.daoAccess().updateSaviourOfEarth("Abhilash", score);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                textScore.setText(Integer.toString(score));
            }
        }.execute();
    }

    private void initializeViewsTask() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                score = userDatabase.daoAccess().getScoreByName("Abhilash");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                textScore.setText(Integer.toString(score));
                // TODO : Initialize other views like HEATMAP, PROFILE PICTURE
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        LevelProgressBar levelProgressBar = getActivity().findViewById(R.id.p1);
        levelProgressBar.setProgressWithAnimation(5F);
        CircularImageView circularImageView = getActivity().findViewById(R.id.circularImageView);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.vector_abhijit_1);
        circularImageView.setImageBitmap(largeIcon);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 69) {
            increaseScoreTask();
        }
    }

    public Bitmap takeScreenshot() {
        View rootView = root;
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    private void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/scrnshot.png"); ////File imagePath
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Uri uri = FileProvider.getUriForFile(getContext(), "org.fruitsalad.fileprovider", imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "My highest score with Heat map "+score+" install and explore yourself";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Catch score");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}