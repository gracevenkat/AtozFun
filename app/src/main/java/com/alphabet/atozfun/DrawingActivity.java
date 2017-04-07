package com.alphabet.atozfun;

import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alphabet.atozfun.utils.DeviceUtils;
import com.alphabet.atozfun.views.DrawingImageView;

import java.util.ArrayList;


public class DrawingActivity extends AppCompatActivity implements DrawingImageView.DrawFinishListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        LinearLayout llMain = (LinearLayout) findViewById(R.id.llMain);

        int drawable = R.drawable.ic_launcher;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawable);
        DrawingImageView image = new DrawingImageView(this, bitmap);
        image.setImageBitmap(bitmap);

        llMain.addView(image);
    }

    @Override
    public void onDrawFinish() {
        Toast.makeText(getApplicationContext(), "Draw finished", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDrawStop() {
        Log.i("Action: ", "Draw stop");
    }

    @Override
    public void onDrawStart() {
        Log.i("Action: ", "Draw start");
    }
}