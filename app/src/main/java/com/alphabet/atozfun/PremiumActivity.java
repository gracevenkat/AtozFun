//package com.alphabet.atozfun;
//
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.speech.tts.TextToSpeech;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alphabet.atozfun.animation.Techniques;
//import com.alphabet.atozfun.animation.YoYo;
//import com.alphabet.atozfun.utils.ColorFilterUtils;
//import com.alphabet.atozfun.utils.DeviceUtils;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.nineoldandroids.animation.Animator;
//
//import java.util.Locale;
//
//public class PremiumActivity extends AppCompatActivity {
//	private TextView txtTitle;
//	private AdView mAdView;
//	private ImageView imgSpeak,imgPremiumDetail;
//	private MyBean item;
//	private InterstitialAd mPremiumDialogInterstitialAd;
//	private TextToSpeech tts;
//	private YoYo.YoYoString rope;
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_premium);
//
//
//		DeviceUtils.useCompatVectorIfNeeded();
//
//		init();
//		setupevents();
//	}
//
//	private void init() {
//		mPremiumDialogInterstitialAd = new InterstitialAd(this);
//		mPremiumDialogInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));
//
//		mAdView = (AdView) findViewById(R.id.adView);
//		AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//				// Check the LogCat to get your test device ID
//				.addTestDevice(getResources().getString(R.string.test_device_id))
//				.build();
//		mAdView.loadAd(adRequest);
//		mPremiumDialogInterstitialAd.loadAd(adRequest);
//		txtTitle = (TextView) findViewById(R.id.txt_title);
//		imgSpeak =(ImageView)findViewById(R.id.img_mic);
//		ColorFilterUtils.setColorFilter(findViewById(R.id.img_close));
//		imgPremiumDetail =(ImageView)findViewById(R.id.img_premium_detail);
//
//		ColorFilterUtils.setColorFilter(imgSpeak);
//		tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//			@Override
//			public void onInit(int status) {
//				if(status != TextToSpeech.ERROR) {
//					tts.setLanguage(Locale.UK);
//				}
//			}
//		});
//
//		if(getIntent().hasExtra("full_item")){
//			item = (MyBean)getIntent().getSerializableExtra("full_item");
//			imgPremiumDetail.setImageResource(item.premiumImage);
//		}
//		txtTitle.setText(getResources().getString(item.forPremium));
//		DeviceUtils.getDeviceId(PremiumActivity.this,adRequest);
//		doAnimate(Techniques.ZoomInUp);
//	}
//	@Override
//	public void onPause() {
//		if (mAdView != null &&  !PremiumActivity.this.isFinishing()) {
//			mAdView.pause();
//		}
//		super.onPause();
//	}
//
//
//	private void doAnimate(final Techniques tec){
//		Techniques technique = tec;
//		rope =  YoYo.with(technique)
//				.duration(1200)
//				.interpolate(new AccelerateDecelerateInterpolator())
//				.withListener(new Animator.AnimatorListener() {
//					@Override
//					public void onAnimationStart(Animator animation) {
//
//					}
//
//					@Override
//					public void onAnimationEnd(Animator animation) {
//						if(tec == Techniques.ZoomOutDown)
//							finish();
//
//					}
//
//					@Override
//					public void onAnimationCancel(Animator animation) {
//						Toast.makeText(PremiumActivity.this, "canceled", Toast.LENGTH_SHORT).show();
//					}
//
//					@Override
//					public void onAnimationRepeat(Animator animation) {
//
//					}
//				})
//				.playOn(imgPremiumDetail);
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		if (mAdView != null  && !PremiumActivity.this.isFinishing()) {
//			mAdView.resume();
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		// Don't forget to shutdown tts!
//		if (tts != null &&  !PremiumActivity.this.isFinishing()) {
//			tts.stop();
//			tts.shutdown();
//		}
//		if (mAdView != null &&  !PremiumActivity.this.isFinishing()) {
//			mAdView.destroy();
//		}
//		super.onDestroy();
//	}
//
//
//
//	private void setupevents() {
//		mAdView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (mPremiumDialogInterstitialAd.isLoaded()) {
//					mPremiumDialogInterstitialAd.show();
//				} else {
//					//Begin Game, continue with app
//				}
//			}
//		});
//		findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				setResult(RESULT_OK);
//				doAnimate(Techniques.ZoomOutDown);
//			}
//		});
//
//
//
//		imgSpeak.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				speak(item);
//			}
//		});
//		mAdView.setAdListener(new AdListener() {
//			@Override
//			public void onAdLoaded() {
//				//	Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onAdClosed() {
//				//	Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onAdFailedToLoad(int errorCode) {
//				//Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onAdLeftApplication() {
//				//Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onAdOpened() {
//				//Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
//
//	private void speak(MyBean item) {
//		if (Build.VERSION.RELEASE.startsWith("5")) {
//			tts.speak(String.format(getResources().getString(item.title)+" for "+getResources().getString(item.forPremium)), TextToSpeech.QUEUE_FLUSH, null, null);
//		}
//		else {
//			tts.speak(String.format(getResources().getString(item.title)+" for "+getResources().getString(item.forPremium)), TextToSpeech.QUEUE_FLUSH, null);
//		}
//	}
//
//
//}
