package com.alphabet.atozfun.preference;

import android.content.Context;
import android.content.SharedPreferences;


public class AppPreference {
    private static final String RM_APP_PREF = "rm_app_pref";
    private final Context mContext;
    private SharedPreferences mPreference;
    private SharedPreferences.Editor mEditor;

    private static final String PREF_USER_EMAIL = "pref_user_email";
    private static final String PREF_USER_NAME = "pref_user_name";


    public AppPreference(Context context) {
        this.mContext = context;
        mPreference = context.getSharedPreferences(RM_APP_PREF, Context.MODE_PRIVATE);
        mEditor = mPreference.edit();
    }



    public String getUserEmail() {
        return mPreference.getString(PREF_USER_EMAIL, "");
    }
    public void setUserEmail(String value) {
        mEditor.putString(PREF_USER_EMAIL, value);
        mEditor.commit();
    }

    public void setUserName(String value) {
        mEditor.putString(PREF_USER_NAME, value);
        mEditor.commit();
    }

    public String getUserName() {
        return mPreference.getString(PREF_USER_NAME, "");
    }


}
