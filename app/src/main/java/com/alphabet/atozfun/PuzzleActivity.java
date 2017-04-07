package com.alphabet.atozfun;

import android.app.Activity;
import android.content.res.TypedArray;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alphabet.atozfun.animation.Techniques;
import com.alphabet.atozfun.animation.YoYo;
import com.alphabet.atozfun.utils.DeviceUtils;
import com.alphabet.atozfun.views.FlowLayout;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;


public class PuzzleActivity extends AppCompatActivity {

    private GestureLibrary gLib;
    private static final String TAG = "com.hascode.android.gesture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        DeviceUtils.useCompatVectorIfNeeded();
        init();
        setupevents();
    }
    private GestureOverlayView.OnGesturePerformedListener handleGestureListener = new GestureOverlayView.OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView gestureView,
                                       Gesture gesture) {

            ArrayList<Prediction> predictions = gLib.recognize(gesture);

            // one prediction needed
            if (predictions.size() > 0) {
                Prediction prediction = predictions.get(0);
                // checking prediction
                if (prediction.score > 1.0) {
                    // and action
                    Toast.makeText(PuzzleActivity.this, prediction.name,
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
    private void setupevents() {


    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        //    finish();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    private void init() {
      /*  gLib = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!gLib.load()) {
            Log.w(TAG, "could not load gesture library");
            finish();
        }*/
    }



}
