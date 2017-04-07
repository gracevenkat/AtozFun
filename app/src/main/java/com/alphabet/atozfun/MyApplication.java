package com.alphabet.atozfun;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;

import com.alphabet.atozfun.google_analytics.AnalyticsTrackers;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;


import io.fabric.sdk.android.Fabric;


public class MyApplication extends Application {

    private static Context mContext;
    private DisplayMetrics displayMetrics = null;
    protected static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.ENABLE_CRASHLYTICS)
            Fabric.with(this, new Crashlytics());
        mContext = getApplicationContext();
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    public MyApplication(){
        mInstance = this;
    }

    public static MyApplication getApp() {
        if (mInstance != null) {
            return (MyApplication) mInstance;
        } else {
            mInstance = new MyApplication();
            mInstance.onCreate();
            return (MyApplication) mInstance;
        }
    }
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }


    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }


    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }
    public static Context getAppContext() {
        return mContext;
    }

    public String getCacheDirPath() {
        return getCacheDir().getAbsolutePath();
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public String getUsername(Context context) {

       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Cursor c = context.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
            c.moveToFirst();
            c.moveToFirst();
            return c.getString(c.getColumnIndex("display_name"));
        }*/
        return "";

    }


    public String getMailId(Context context) {

        /*Account[] accounts=AccountManager.get(context).getAccountsByType("com.google");
        String myEmailid=accounts[0].name;
        for(Account account: accounts)
        {
            Log.d("My email id that i want", account.name);
        }
        return myEmailid;*/
       return "";
    }
}