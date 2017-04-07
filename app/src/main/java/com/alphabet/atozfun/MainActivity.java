package com.alphabet.atozfun;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphabet.atozfun.animation.Techniques;
import com.alphabet.atozfun.animation.YoYo;
import com.alphabet.atozfun.circle_page_indicator.CirclePageIndicator;
import com.alphabet.atozfun.preference.AppPreference;
import com.alphabet.atozfun.utils.ColorFilterUtils;
import com.alphabet.atozfun.utils.DeviceUtils;
import com.alphabet.atozfun.views.AlertUtils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.nineoldandroids.animation.Animator;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
	private boolean isExitPressed = false;
	private TextToSpeech tts;
	private TextView txtTitle;
	private AdView mAdView;
	private ImageView imgSpeak,imgFree,imgPremium, imgRight,imgLeft;
	private MyBean item;
	private InterstitialAd mInterstitialAd;
	private ViewPager viewPager;
	private PagerAdapter adapter;
	int currentItemPosition =0;
	private AppPreference preference;
	private MyApplication myApplication	 = new MyApplication();
	final VunglePub vunglePub = VunglePub.getInstance();

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//logUser();
		setContentView(R.layout.activity_main);
		DeviceUtils.useCompatVectorIfNeeded();

		final String vongle_app_id = "57ee2a4073150cf328000016";

		// initialize the Publisher SDK
		vunglePub.init(this, vongle_app_id);

		vunglePub.setEventListeners(vungleDefaultListener, vungleSecondListener);


		init();
		setupevents();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/*if(requestCode == 101 && resultCode == RESULT_OK){
			Intent intent = new Intent(MainActivity.this,PremiumActivity.class);
			intent.putExtra("full_item",item);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			overridePendingTransition (0, 0);
		}*/
	}


	private class tempTask extends AsyncTask {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Please wait...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Object doInBackground(Object... params) {



			tts = new TextToSpeech(getApplicationContext(),
					new TextToSpeech.OnInitListener() {
						@Override
						public void onInit(int status) {
							if(status != TextToSpeech.ERROR){
								tts.setLanguage(Locale.US);
							}
						}
					});

			return null;
		}

		@Override
		protected void onPostExecute(Object o) {
			progressDialog.dismiss();
			Log.d("onPostExecute","OnPostExecute");
		}
	}


	private void init() {
		// TODO Auto-generated method stub

		mInterstitialAd = new InterstitialAd(this);
		setFinishOnTouchOutside(true);

		mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));
		mAdView = (AdView) findViewById(R.id.adView_main);
		AdRequest adRequest = new AdRequest.Builder()
			.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				// Check the LogCat to get your test device ID
				.addTestDevice(getResources().getString(R.string.test_device_id))
				.build();
		mAdView.loadAd(adRequest);
		mInterstitialAd.loadAd(adRequest);
		preference = new AppPreference(MainActivity.this);
		item  = PagerAdapter.items[0];

		txtTitle = (TextView) findViewById(R.id.txt_title);
		imgSpeak =(ImageView)findViewById(R.id.img_mic);
		imgRight = (ImageView)findViewById(R.id.img_right) ;
		imgLeft = (ImageView)findViewById(R.id.img_left) ;
		imgLeft.setVisibility(View.GONE);
		ColorFilterUtils.setColorFilter(imgLeft);
		ColorFilterUtils.setColorFilter(imgRight);
		imgFree =(ImageView)findViewById(R.id.img_free);
		imgPremium =(ImageView)findViewById(R.id.img_premium);
		imgPremium.setImageResource(item.premiumImage);
		imgFree.setImageResource(item.freeImage);
		tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.US);
				}
			}
		});
		if(DeviceUtils.isInternetConnected(MainActivity.this))
		  new  tempTask().execute();
		else
			Toast.makeText(getApplicationContext(), "Please check the internet connection.", Toast.LENGTH_SHORT).show();
		 viewPager = (ViewPager) findViewById(R.id.view_pager);
		CirclePageIndicator pagerIndicator = (CirclePageIndicator) findViewById(R.id.pager_indicator);

		 adapter = new PagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		pagerIndicator.setViewPager(viewPager);
		pagerIndicator.setOnPageChangeListener(onPageChangeListener);
		ColorFilterUtils.setColorFilter(imgFree);
		ColorFilterUtils.setColorFilter(imgPremium);
		ColorFilterUtils.setColorFilter(imgSpeak);

	}
	@Override
	public void onPause() {
		if (mAdView != null &&  !MainActivity.this.isFinishing()) {
			mAdView.pause();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		MyApplication.getInstance().trackScreenView("Main Screen");
		if (mAdView != null &&  !MainActivity.this.isFinishing()) {
			mAdView.resume();
		}
		this.isExitPressed = false;
	}


	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			MyApplication.getInstance().trackEvent("Main", "Main Screen Swipe Action", "Main_Screen_Swipe==="+preference.getUserName()+" "+preference.getUserEmail());
			item = PagerAdapter.items[arg0];
			imgPremium.setImageResource(item.premiumImage);
			imgFree.setImageResource(item.freeImage);
			currentItemPosition =arg0;
			if(getResources().getString(item.title).equalsIgnoreCase(getResources().getString(R.string.a))){
				imgLeft.setVisibility(View.GONE);

			}else
				imgLeft.setVisibility(View.VISIBLE);


			if(getResources().getString(item.title).equalsIgnoreCase(getResources().getString(R.string.z))){
				imgRight.setImageResource(R.drawable.m_refresh);

			}else
				imgRight.setImageResource(R.drawable.right_arrow);

			speak(item);
		}

		@Override
		public void onPageScrolled(int arg0
				, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	private void setupevents() {

		//new TaskDownloadImage().execute();

		imgRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(currentItemPosition >= 25)
			    	viewPager.setCurrentItem(0, true);
				else
					viewPager.setCurrentItem(currentItemPosition+1, true);
				adapter.notifyDataSetChanged();

			}
		});
		imgLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(currentItemPosition-1, true);
				adapter.notifyDataSetChanged();

			}
		});
		mAdView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mInterstitialAd.isLoaded()) {
					mInterstitialAd.show();
				} else {
					//Begin Game, continue with app
				}
			}
		});


		imgFree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,FreeActivity.class);
				intent.putExtra("full_item",item);
				startActivity(intent);
			}
		});

		imgPremium.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(DeviceUtils.isInternetConnected(MainActivity.this)) {
							Intent intent = new Intent(MainActivity.this, FullScreenAdActivity.class);
							intent.putExtra("full_item", item);
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivityForResult(intent, 101);
							overridePendingTransition (0, 0);

				}else{
					DeviceUtils.showCustomToast(MainActivity.this,"No Internet Connection!");
				}
			}
		});

		imgSpeak.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
				MyApplication.getInstance().trackEvent("Main", "Ad Opened", "Main_Screen_AD_Open for "+getString(item.title));
			}

			@Override
			public void onAdOpened() {
				MyApplication.getInstance().trackEvent("Main", "Ad Clicked", "Main_Screen_AD_Click for "+getString(item.title));
			}
		});

	}

	private void speak(MyBean item) {
		MyApplication.getInstance().trackEvent("Speak", "Clicked", "Main_Screen_Speak_Button_Of_"+getResources().getString(item.title));
		if (Build.VERSION.RELEASE.startsWith("5")) {
			tts.speak(getResources().getString(item.title), TextToSpeech.QUEUE_FLUSH, null, null);
		}
		else {
			tts.speak(getResources().getString(item.title), TextToSpeech.QUEUE_FLUSH, null);
		}
	}


	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null &&  !MainActivity.this.isFinishing()) {
			tts.stop();
			tts.shutdown();
		}
		if (mAdView != null &&  !MainActivity.this.isFinishing()) {
			mAdView.destroy();
		}
		super.onDestroy();
	}
	@Override
	public void onBackPressed() {
		if(!MainActivity.this.isFinishing()){
			if (isExitPressed) {
				super.onBackPressed();
				return;
			}
			this.isExitPressed = true;
			Toast.makeText(MainActivity.this,"Press again to exit.", Toast.LENGTH_LONG).show();

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					isExitPressed = false;
				}
			}, 2000);
		}

	}
	/*private class TaskDownloadImage extends AsyncTask<String, Void, String> {
ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			preference.setUserName(myApplication.getUsername(MainActivity.this));
			preference.setUserEmail(myApplication.getMailId(MainActivity.this));
		return null;
		}

		@Override
		protected void onPostExecute(String str) {
			super.onPostExecute(str);
			progressDialog.dismiss();
		}
	}*/



	private final EventListener vungleSecondListener = new EventListener() {
		// Vungle SDK allows for multiple listeners to be attached. This secondary event listener is only
		// going to print some logs for now, but it could be used to Pause music, update a badge icon, etc.
		@Deprecated
		@Override
		public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {}

		@Override
		public void onAdStart() {}

		@Override
		public void onAdUnavailable(String reason) {}

		@Override
		public void onAdEnd(boolean wasSuccessfulView, boolean wasCallToActionClicked) {}

		@Override
		public void onAdPlayableChanged(boolean isAdPlayable) {
			Log.d("SecondListener", String.format("This is a second event listener! Ad playability has changed, and is now: %s", isAdPlayable));
		}
	};
	private void PlayAd() {
		vunglePub.playAd();
	}

	private final EventListener vungleDefaultListener = new EventListener() {
		@Deprecated
		@Override
		public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) {
			// This method is deprecated and will be removed. Please use onAdEnd() instead.
		}

		@Override
		public void onAdStart() {
			// Called before playing an ad.
		}

		@Override
		public void onAdUnavailable(String reason) {
			// Called when VunglePub.playAd() was called but no ad is available to show to the user.
		}

		@Override
		public void onAdEnd(boolean wasSuccessfulView, boolean wasCallToActionClicked) {
			// Called when the user leaves the ad and control is returned to your application.
		}

		@Override
		public void onAdPlayableChanged(boolean isAdPlayable) {
			// Called when ad playability changes.
			Log.d("DefaultListener", "This is a default eventlistener.");
			final boolean enabled = isAdPlayable;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// Called when ad playability changes.
//                    setButtonState(buttonPlayAd, enabled);
//                    setButtonState(buttonPlayAdIncentivized, enabled);
//                    setButtonState(buttonPlayAdOptions, enabled);
				}
			});
		}
	};



}
