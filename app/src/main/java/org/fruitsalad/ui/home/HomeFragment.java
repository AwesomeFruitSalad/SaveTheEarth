package org.fruitsalad.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HeatDataEntry;
import com.anychart.charts.HeatMap;
import com.anychart.enums.SelectionMode;
import com.anychart.graphics.vector.SolidFill;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.fruitsalad.R;

import java.util.ArrayList;
import java.util.List;

import ir.alirezaiyan.progressbar.LevelProgressBar;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }


    private class CustomHeatDataEntry extends HeatDataEntry {
        CustomHeatDataEntry(String x, String y, Integer heat, String fill) {
            super(x, y, heat);
            setValue("fill", fill);
        }
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

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomHeatDataEntry("Jan", "Monday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Wednsday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Monday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Jan", "Thursday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Wednsday", 0, "#90caf9"));
        data.add(new CustomHeatDataEntry("Feb", "Thursday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Wednsday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Wednsday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("Feb", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Tuesday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Tuesday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Monday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("March", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("April", "Thursday", 0, "#90caf9"));
        data.add(new CustomHeatDataEntry("April", "Thursday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("April", "Monday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("May", "Tuesday", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("May", "Tuesday", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("May", "Friday", 0, "#90caf9"));
        data.add(new CustomHeatDataEntry("May", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Major", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Tuesday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("June", "Extreme", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("July", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("July", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("July", "Saturday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Thursday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Saturday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("August", "Friday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Thursday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Friday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Thursday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Sep", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Wednsday", 3, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Friday", 1, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Friday", 0, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Sunday", 2, "#43a047"));
        data.add(new CustomHeatDataEntry("Oct", "Saturday", 3, "#43a047"));

        riskMap.data(data);


        anyChartView.setChart(riskMap);
        super.onViewCreated(view, savedInstanceState);
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
}