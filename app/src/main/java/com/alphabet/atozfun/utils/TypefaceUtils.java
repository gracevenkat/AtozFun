package com.alphabet.atozfun.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class TypefaceUtils {

    public static final int SIG_REGULAR = 2;

    public static final String SIGNIKA_REGULAR = "kidds.ttf";

    public static Typeface getTypeFace(Context context, int tf) {
        String typeFace = null;
        typeFace = SIGNIKA_REGULAR;


        AssetManager assets = context.getAssets();
        return Typeface.createFromAsset(assets, "fonts/" + typeFace);
    }

}
