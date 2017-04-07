package com.alphabet.atozfun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alphabet.atozfun.animation.Techniques;
import com.alphabet.atozfun.animation.YoYo;
import com.alphabet.atozfun.utils.ColorFilterUtils;
import com.alphabet.atozfun.utils.DeviceUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.appinvite.PreviewActivity;
import com.nineoldandroids.animation.Animator;

import java.util.Locale;

public class FullScreenAdActivity extends AppCompatActivity {

    private String TAG = FullScreenAdActivity.class.getSimpleName();
    private  InterstitialAd mInterstitialAd;
    private  ProgressDialog progressDialog;


    private TextView txtTitle;
    private AdView mAdView;
    private ImageView imgSpeak,imgPremiumDetail;
    private MyBean item;
    private InterstitialAd mPremiumDialogInterstitialAd;
    private TextToSpeech tts;
    private boolean isAdOpened =false;
    private YoYo.YoYoString rope;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Venkat", "On act result trigged in ful screen");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_premium);
        DeviceUtils.useCompatVectorIfNeeded();
        init();
        setupevents();
    }

    private void init() {



        DeviceUtils.useCompatVectorIfNeeded();
        progressDialog = new ProgressDialog(FullScreenAdActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
               .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                  //Check the LogCat to get your test device ID
                .addTestDevice(getString(R.string.test_device_id))
                .build();
        mInterstitialAd.loadAd(adRequest);


    }

    private void doAnimateTitle(final Techniques tec){
        Techniques technique = tec;
        rope =  YoYo.with(technique)
                .duration(1200)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(FullScreenAdActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(txtTitle);
    }

    private void doAnimate(final Techniques tec, View view){
        Techniques technique = tec;
        rope =  YoYo.with(technique)
                .duration(800)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(tec == Techniques.ZoomOutDown)
                            finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(FullScreenAdActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(view);
    }

    @Override
    public void onPause() {
        if (mAdView != null &&  !FullScreenAdActivity.this.isFinishing()) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnresumeCalled","OnResume");
        MyApplication.getInstance().trackScreenView("Premium Screen");
        if (mAdView != null  && !FullScreenAdActivity.this.isFinishing()) {
            mAdView.resume();
        }
        if(isAdOpened){
            imgPremiumDetail.setVisibility(View.VISIBLE);
            doAnimate(Techniques.ZoomInUp,imgPremiumDetail);
        }

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null &&  !FullScreenAdActivity.this.isFinishing()) {
            tts.stop();
            tts.shutdown();
        }
        if (mAdView != null &&  !FullScreenAdActivity.this.isFinishing()) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void setupevents() {

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                progressDialog.dismiss();

                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if(!isAdOpened){
                    finish();
                }
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                setContentView(R.layout.activity_premium);

                isAdOpened = true;
                initPremium();
                setupeventsPremium();
            }
        });

    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void initPremium() {
        mPremiumDialogInterstitialAd = new InterstitialAd(this);
        mPremiumDialogInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequestPremium = new AdRequest.Builder()
             //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
              //  .addTestDevice(getResources().getString(R.string.test_device_id))
                .build();
        mAdView.loadAd(adRequestPremium);
        mPremiumDialogInterstitialAd.loadAd(adRequestPremium);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgSpeak =(ImageView)findViewById(R.id.img_mic);
        ColorFilterUtils.setColorFilter(findViewById(R.id.img_close));
        imgPremiumDetail =(ImageView)findViewById(R.id.img_premium_detail);

        ColorFilterUtils.setColorFilter(imgSpeak);
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        if(getIntent().hasExtra("full_item")){
            item = (MyBean)getIntent().getSerializableExtra("full_item");
            imgPremiumDetail.setImageResource(item.premiumImage);
            imgPremiumDetail.setVisibility(View.GONE);
        }
        txtTitle.setText(getResources().getString(item.forPremium));
        DeviceUtils.getDeviceId(FullScreenAdActivity.this,adRequestPremium);
     //   doAnimate(Techniques.ZoomInUp);
    }
    private void setupeventsPremium() {
        mAdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPremiumDialogInterstitialAd.isLoaded()) {
                    mPremiumDialogInterstitialAd.show();
                } else {
                    //Begin Game, continue with app
                }
            }
        });
        findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnimate(Techniques.ZoomOutDown,imgPremiumDetail);
            }
        });



        imgSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnimate(Techniques.Wave,txtTitle);
                speak(item);
            }
        });
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdLeftApplication() {
                MyApplication.getInstance().trackEvent("Premium", "Ad Opened", "Premium_Screen_AD_Open for"+getString(item.title));
            }

            @Override
            public void onAdOpened() {
                MyApplication.getInstance().trackEvent("Premium", "Ad Clicked", "Premium_Screen_AD_Click for"+getString(item.title));
            }
        });
    }

    private void speak(MyBean item) {
        MyApplication.getInstance().trackEvent("Speak", "Clicked", "Premium_Screen_Speak_Button_Of_"+getResources().getString(item.title));
        if (Build.VERSION.RELEASE.startsWith("5")) {
            tts.speak(String.format(getResources().getString(item.title)+" for "+getResources().getString(item.forPremium)), TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            tts.speak(String.format(getResources().getString(item.title)+" for "+getResources().getString(item.forPremium)), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Venkat","on back pressed");
        if(!isAdOpened){
            finish();
        }

    }
}



