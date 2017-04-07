package com.alphabet.atozfun.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.AppCompatDrawableManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alphabet.atozfun.R;
import com.google.android.gms.ads.AdRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class DeviceUtils {


    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    public static int getDensityDpi(Context context) {
        return getDisplayMetrics(context).densityDpi;
    }

    public static void hideSoftKeyboard(Context context, View paramView) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                paramView.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showSoftKeyboard(Dialog paramDialog) {
        paramDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static void showSoftKeyboard(Context context, View paramView) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(paramView,
                InputMethodManager.SHOW_IMPLICIT);
    }

    public static int getScreenWidth(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        return display.getWidth();

    }

    public static int getScreenHeight(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        return display.getHeight();
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        boolean isConnected = false;

        if (info != null && info.isConnectedOrConnecting()) {
            isConnected = true;
        }

        return isConnected;
    }
    public static void showCustomToast(Activity activity, String msg) {
        View layout = activity.getLayoutInflater().inflate(R.layout.view_custom_toast_view, (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public static void getDeviceId(Context context, AdRequest adRequest) {


         adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                 // Check the LogCat to get your test device ID
                .addTestDevice("E93D583CCB672336EBE881A834A3CE48")
                .build();


    }

    public static void useCompatVectorIfNeeded() {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt == 21 || sdkInt == 22) { //vector drawables scale correctly in API level 23
            try {
                AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();
                Class<?> inflateDelegateClass = Class.forName("android.support.v7.widget.AppCompatDrawableManager$InflateDelegate");
                Class<?> vdcInflateDelegateClass = Class.forName("android.support.v7.widget.AppCompatDrawableManager$VdcInflateDelegate");

                Constructor<?> constructor = vdcInflateDelegateClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object vdcInflateDelegate = constructor.newInstance();

                Class<?> args[] = {String.class, inflateDelegateClass};
                Method addDelegate = AppCompatDrawableManager.class.getDeclaredMethod("addDelegate", args);
                addDelegate.setAccessible(true);
                addDelegate.invoke(drawableManager, "vector", vdcInflateDelegate);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getPixelFromDp(Context context, int dpUnits) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpUnits, getDisplayMetrics(context));
        return (int) px;
    }

    public static float getPixelFromDp(Context context, float dpInFloat) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpInFloat, getDisplayMetrics(context));
    }

    public static float getFontPixelFromSp(Context context, float spUnits) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spUnits, getDisplayMetrics(context));
    }

    public static void showSoftInput(final Context context, final EditText view, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftKeyboard(context, view);
            }
        }, delay);
    }

    public static void hideSoftInput(final Activity context, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideSoftKeyboard(context);
            }
        }, delay);
    }

    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getMobileNo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }


    public static boolean isPre4_0() {
        return getSDKVersion() < VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void setSystemUiVisibility(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }



    public static void setStatusBarColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, colorId));
        }
    }


public static ArrayList<Integer> getRandomInteger(){
    ArrayList<Integer> list = new ArrayList<Integer>();
    for (int i=0; i<26; i++) {
        list.add(new Integer(i));
    }
    Collections.shuffle(list);
    for (int i=0; i<26; i++) {
        System.out.println("===="+list.get(i));
    }
    return  list;

}
    static int[] myNumbers = null;

    public static  int[] randomArrayList(int n)
    {
        try
        {
            myNumbers = new int[26];
            Random r = new Random();
            int total_elements_cnt = 0;
            boolean loop_status = true;
            while(loop_status)
            {
                int next_num = r.nextInt(26);
                if(!isCompleted()){
                    if(!isDuplicate(next_num)){
                        myNumbers[total_elements_cnt] = next_num;
                        total_elements_cnt++;
                    }else{
                        continue;
                    }
                }else{
                    loop_status = false;
                }
            }
            for (int i = 0; i < myNumbers.length; i++) {
                System.out.println("PrintLn"+myNumbers[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myNumbers;
    }


    public  static  boolean isCompleted(){
        boolean status = true;
        for (int i = 0; i < myNumbers.length; i++){
            if(myNumbers[i]==0){
                status = false;
                break;
            }
        }
        return  status;
    }
    public static boolean isDuplicate(int num){
        boolean status = false;
        for (int i = 0; i < myNumbers.length; i++){
            if(myNumbers[i]== num){
                status = true;
                break;
            }
        }
        return  status;
    }
    public static int randomColor(){
        float[] TEMP_HSL = new float[]{0, 0, 0};
        float[] hsl = TEMP_HSL;
        hsl[0] = (float) (Math.random() * 360);
        hsl[1] = (float) (40 + (Math.random() * 60));
        hsl[2] = (float) (40 + (Math.random() * 60));
        return ColorUtils.HSLToColor(hsl);
    }



}
