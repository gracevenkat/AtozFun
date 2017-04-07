package com.alphabet.atozfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivityBounce extends Activity {

    private static final long SLEEP_DURATION = 2000l;
    private boolean isDestroyed = false;
    private ImageView splashImage;

   private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            startActivity(new Intent(SplashActivityBounce.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage =(ImageView)findViewById(R.id.splash_image);
        mHandler.sendEmptyMessageDelayed(1, SLEEP_DURATION);
    }


    @Override
    protected void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }
}
