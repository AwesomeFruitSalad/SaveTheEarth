package org.fruitsalad.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.objects.FirebaseVisionObject;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetector;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions;

import org.fruitsalad.R;

import java.util.List;


public class ObjectDetection extends AppCompatActivity {

    private Button snapBtn;
    private Button detectBtn;
    private ImageView imageView;
    private TextView txtView;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_detection_layout);
        FirebaseApp.initializeApp(this);


        snapBtn = findViewById(R.id.snapBtn);
        detectBtn = findViewById(R.id.detectBtn);
        imageView = findViewById(R.id.imageView);
        txtView = findViewById(R.id.txtView);

        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(69, returnIntent);
                finish();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }


    public void detectImage() {
//        FirebaseVisionCloudLandmarkDetector detector =
//                FirebaseVision.getInstance().getVisionCloudLandmarkDetector();
//
//        detector.detectInImage(FirebaseVisionImage.fromBitmap(imageBitmap))
//                .addOnCompleteListener(new OnCompleteListener<List<FirebaseVisionCloudLandmark>>() {
//                    @Override
//                    public void onComplete(@NonNull Task<List<FirebaseVisionCloudLandmark>> task) {
//                        String output = "";
//                        for (FirebaseVisionCloudLandmark label : task.getResult()) {
//                            if (label.getConfidence() > 0.7) {
//                                output += "" + label.getLandmark();
//                            }
//                        }
//                        Toast.makeText(getApplicationContext(), "This is " + output, Toast.LENGTH_LONG).show();
//                    }
//                });
    }

    /**
     * !!!WARNING!!! :DON'T USE THIS METHOD
     */
    public void detectImageShittyVersion() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionObjectDetectorOptions options =
                new FirebaseVisionObjectDetectorOptions.Builder()
                        .setDetectorMode(FirebaseVisionObjectDetectorOptions.STREAM_MODE)
                        .enableClassification()  // Very Optional
                        .build();
        FirebaseVisionObjectDetector objectDetector =
                FirebaseVision.getInstance().getOnDeviceObjectDetector(options);

        objectDetector.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionObject>>() {
            @Override
            public void onSuccess(List<FirebaseVisionObject> firebaseVisionObjects) {
                Toast.makeText(getApplicationContext(), "this is" + firebaseVisionObjects, Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

}
