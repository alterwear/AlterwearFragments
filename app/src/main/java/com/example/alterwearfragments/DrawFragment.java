package com.example.alterwearfragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DrawFragment extends Fragment {

    private static final String TAG = "draw";
    private static Bitmap bitmap;


    public DrawFragment() {
        // Required empty public constructor
    }

    public static DrawFragment getInstance() {
        DrawFragment fragment = new DrawFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_draw, container, false);

        return v;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button clear_button = view.findViewById(R.id.clear_button);
        clear_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                CustomView customView = getView().findViewById(R.id.customView);
                customView.clear();
            }
        });
    }

    public void setDrawBitmap() {
        final CustomView customView = getView().findViewById(R.id.customView);
        Bitmap parentCanvasBitmap = MainActivity.getCanvasBitmap();
        customView.setDrawBitmap(parentCanvasBitmap);
        Log.d(TAG, "set draw bitmap from draw fragment to custom view");

    }


}
