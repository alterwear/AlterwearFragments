package com.example.alterwearfragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    private DrawFragment drawFragment;
    private CameraFragment cameraFragment;
    private TextFragment textFragment;

    private Bitmap canvasBitmap;

    private TextView mTextMessage;

    private BottomNavigationView nav;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_draw:
                    displayDrawFragment();
                    return true;
                case R.id.navigation_camera:
                    displayCameraFragment();
                    return true;
                case R.id.navigation_text:
                    displayTextFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            drawFragment = DrawFragment.getInstance();
            cameraFragment = CameraFragment.getInstance();
            textFragment = TextFragment.getInstance();
        }

        displayDrawFragment();
    }

    // Replace the switch method
    protected void displayDrawFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (drawFragment.isAdded()) { // if the fragment is already in container
            ft.show(drawFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragment_frame, drawFragment, "draw");
        }
        // Hide fragment B
        if (cameraFragment.isAdded()) { ft.hide(cameraFragment); }
        // Hide fragment C
        if (textFragment.isAdded()) { ft.hide(textFragment); }
        // Commit changes
        ft.commit();
    }

    // Replace the switch method
    protected void displayCameraFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (cameraFragment.isAdded()) { // if the fragment is already in container
            ft.show(cameraFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragment_frame, cameraFragment, "camera");
        }
        // Hide fragment B
        if (drawFragment.isAdded()) { ft.hide(drawFragment); }
        // Hide fragment C
        if (textFragment.isAdded()) { ft.hide(textFragment); }
        // Commit changes
        ft.commit();
    }

    // Replace the switch method
    protected void displayTextFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (textFragment.isAdded()) { // if the fragment is already in container
            ft.show(textFragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fragment_frame, textFragment, "text");
        }
        // Hide fragment B
        if (drawFragment.isAdded()) { ft.hide(drawFragment); }
        // Hide fragment C
        if (cameraFragment.isAdded()) { ft.hide(cameraFragment); }
        // Commit changes
        ft.commit();
    }
//
//    @Override
//    public void onNewIntent (final Intent intent) {
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment);
//        if (f instanceof TextFragment) {
//            // Pass intent or its data to the fragment's method
//            f = (TextFragment) f;
//            Bundle bundle = new Bundle();
//            bundle.putString("edttext", "From Activity");
//            // set Fragmentclass Arguments
//            f.setArguments(bundle);
//            f.handleNewIntent();
//        }
//        super.onNewIntent( intent );
//    }

}
