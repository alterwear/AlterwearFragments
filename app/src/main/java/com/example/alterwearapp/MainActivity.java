package com.example.alterwearapp;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    private DrawFragment drawFragment;
    private TextView mTextMessage;

    private BottomNavigationView nav;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_draw:
                    startDrawFragment();
                    return true;
                case R.id.navigation_camera:
                    startCameraFragment();
                    return true;
                case R.id.navigation_text:
//                    mTextMessage.setText(R.string.title_text);
                    startTextFragment();
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

        startDrawFragment();
//        onTabSelected(0, false);
    }

//    @Override
//    public void onTabSelected(int position, boolean wasSelected) {
//        // Pop off everything up to and including the current tab
//        FragmentManager fManager = getSupportFragmentManager();
//        FragmentTransaction fTransaction = fManager.beginTransaction();
//        fManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//        // Add the new tab fragment
//        fTransaction.replace(R.id.fragment, TabFragment.newInstance(position + 1), String.valueOf(position))
//                .addToBackStack(BACK_STACK_ROOT_TAG)
//                .commit();
//    }

//    /**
//     * Add a fragment on top of the current stack
//     */
//    public void addFragmentOnTop(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container, fragment)
//                .addToBackStack(null)
//                .commit();
//    }

//    @Override
//    public void onBackPressed() {
//        FragmentManager fragments = getSupportFragmentManager();
//        Fragment homeFrag = fragments.findFragmentByTag("0");
//
//        if (fragments.getBackStackEntryCount() > 1) {
//            // We have fragments on the backstack that are poppable
//            fragments.popBackStackImmediate();
//        } else if (homeFrag == null || !homeFrag.isVisible()) {
//            // We aren't showing the home screen, so that is the next stop on the back journey
//            nav.setCurrentItem(0);
//        } else {
//            // We are already showing the home screen, so the next stop is out of the app.
//            supportFinishAfterTransition();
//        }
//    }

    /** Called when the user taps the Draw button */
    public void startDrawFragment() {
        // Do something in response to button

        // Begin the transaction
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();

        // Now later we can lookup the fragment by tag
//        DrawFragment drawFragment = (DrawFragment) fManager.findFragmentByTag("draw");

        // set Draw Fragment
        drawFragment = DrawFragment.getInstance();
//        fTransaction.add(R.id.fragment, drawFragment, "draw");

        // Let's first dynamically add a fragment into a frame container
//        ft.replace(R.id.fragment, drawFragment);

        // Complete the changes added above
        fTransaction.commit();



    }

    /** Called when the user taps the Camera button */
    public void startCameraFragment() {
        // Do something in response to button

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.add(R.id.fragment, new CameraFragment(), "camera");
        // Complete the changes added above
        ft.commit();

    }

    /** Called when the user taps the Camera button */
    public void startTextFragment() {
        // Do something in response to button

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment, new TextFragment());
        // Complete the changes added above
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
