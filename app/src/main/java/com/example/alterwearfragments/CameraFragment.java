package com.example.alterwearfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;
import android.provider.MediaStore;
import android.graphics.Color;

import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class CameraFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private static final String TAG = "camera";
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment getInstance() {
        CameraFragment fragment = new CameraFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!OpenCVLoader.initDebug()){
            Toast.makeText(getActivity(), "failed to load OpenCV", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "OpenCV Loaded!", Toast.LENGTH_LONG).show();
        }

        this.mImageView = getView().findViewById(R.id.imageView1);
        final Button button = getView().findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap croppedPhoto;

            if (photo.getWidth() >= photo.getHeight()){
                croppedPhoto = Bitmap.createBitmap(
                        photo,
                        photo.getWidth()/2 - photo.getHeight()/2,
                        0,
                        photo.getHeight(),
                        photo.getHeight()
                );

            }else{
                croppedPhoto = Bitmap.createBitmap(
                        photo,
                        0,
                        photo.getHeight()/2 - photo.getWidth()/2,
                        photo.getWidth(),
                        photo.getWidth()
                );
            }


            Bitmap photoBW = processingBitmap(croppedPhoto);

            //edge detection code
            Mat rgba = new Mat();
            Utils.bitmapToMat(croppedPhoto, rgba);

            Mat edges = new Mat(rgba.size(), CvType.CV_8UC1);
            Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4);
            Imgproc.Canny(edges, edges, 80, 100);
            Bitmap photo_edges = Bitmap.createBitmap(edges.cols(), edges.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(edges, photo_edges);

            mImageView.setImageBitmap(photo_edges);
        }
    }

    // Convert color photo to black and white
    private Bitmap processingBitmap(Bitmap src){
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());

        for (int x = 0; x < src.getWidth(); x++){
            for (int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);

                int pixelBW = (pixelRed + pixelGreen + pixelBlue)/3;
                int newPixel = Color.argb(pixelAlpha, pixelBW, pixelBW, pixelBW);

                dest.setPixel(x, y, newPixel);
            }
        }
        return dest;
    }



}
