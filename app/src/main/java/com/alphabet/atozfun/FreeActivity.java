package com.alphabet.atozfun;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
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
import com.nineoldandroids.animation.Animator;

import java.util.Locale;

public class FreeActivity extends AppCompatActivity {
	private TextView txtTitle;
	private AdView mAdView;
	private ImageView imgSpeak,imgFreeDetail;
	private MyBean item;
	private InterstitialAd mFreeDialogInterstitialAd;
	private TextToSpeech tts;
	private YoYo.YoYoString rope;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free);
		DeviceUtils.useCompatVectorIfNeeded();
		init();
		setupevents();
	}

	private void init() {
		mFreeDialogInterstitialAd = new InterstitialAd(this);
		mFreeDialogInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));

		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice(getResources().getString(R.string.test_device_id))
				.build();
		mAdView.loadAd(adRequest);
		mFreeDialogInterstitialAd.loadAd(adRequest);
		txtTitle = (TextView) findViewById(R.id.txt_title);
		imgSpeak =(ImageView)findViewById(R.id.img_mic);
		imgFreeDetail =(ImageView)findViewById(R.id.img_free_detail);

		ColorFilterUtils.setColorFilter(imgSpeak);
		ColorFilterUtils.setColorFilter(findViewById(R.id.img_close));
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
			imgFreeDetail.setImageResource(item.freeImage);
		}
		txtTitle.setText(getResources().getString(item.forFree));
		DeviceUtils.getDeviceId(FreeActivity.this,adRequest);
		doAnimate(Techniques.RollIn,imgFreeDetail);
	}
	@Override
	public void onPause() {
		if (mAdView != null &&  !FreeActivity.this.isFinishing()) {
			mAdView.pause();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		MyApplication.getInstance().trackScreenView("Free Screen");
		if (mAdView != null && !FreeActivity.this.isFinishing()) {
			mAdView.resume();
		}
	}

	private void doAnimate(final Techniques tec, View view){
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
						if(tec == Techniques.RollOut)
							finish();
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						Toast.makeText(FreeActivity.this, "canceled", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				})
				.playOn(view);
	}

	@Override
	public void onDestroy() {
		if (tts != null &&  !FreeActivity.this.isFinishing()) {
			tts.stop();
			tts.shutdown();
		}
		if (mAdView != null && !FreeActivity.this.isFinishing()) {
			mAdView.destroy();
		}
		super.onDestroy();
	}



	private void setupevents() {
		mAdView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mFreeDialogInterstitialAd.isLoaded()) {
					mFreeDialogInterstitialAd.show();
				} else {
					//Begin Game, continue with app
				}
			}
		});


		findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				setResult(RESULT_OK);

				doAnimate(Techniques.RollOut,imgFreeDetail);



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
				MyApplication.getInstance().trackEvent("Free", "Ad Opened", "Free_Screen_AD_Open for"+getString(item.title));
			}

			@Override
			public void onAdOpened() {
				MyApplication.getInstance().trackEvent("Free", "Ad Clicked", "Free_Screen_AD_Clickfor "+getString(item.title));
			}
		});
	}

	private void speak(MyBean item) {
		MyApplication.getInstance().trackEvent("Speak", "Clicked", "Free_Screen_Speak_Button_Of_"+getResources().getString(item.title));
		if (Build.VERSION.RELEASE.startsWith("5")) {
			tts.speak(String.format(getResources().getString(item.title)+" for "+getResources().getString(item.forFree)), TextToSpeech.QUEUE_FLUSH, null, null);
		}
		else {
			tts.speak(String.format(getResources().getString(item.title)+" for "+getResources().getString(item.forFree)), TextToSpeech.QUEUE_FLUSH, null);
		}
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
			finish();
			return true;
		}

		return super.onTouchEvent(event);
	}
}
