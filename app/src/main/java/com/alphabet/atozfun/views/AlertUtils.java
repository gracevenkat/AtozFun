package com.alphabet.atozfun.views;

import android.app.Dialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.view.Window;


public class AlertUtils {


    public static Dialog getDialog(Context context, int layout) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout);
        return dialog;
    }




}
