package com.alphabet.atozfun.views;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.alphabet.atozfun.R;
import com.alphabet.atozfun.utils.TypefaceUtils;


public class RMTextView extends TextView {

    public RMTextView(Context context) {
        super(context);
    }

    public RMTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyTypeFace(context, attrs);
    }

    public RMTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyTypeFace(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RMTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyTypeFace(context, attrs);
    }

    private void applyTypeFace(Context context, AttributeSet attr) {
        if (isInEditMode())
            return;

        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.RMTypeface);
        int typefaceId = array.getInt(R.styleable.RMTypeface_typeface, TypefaceUtils.SIG_REGULAR);
        array.recycle();

        Typeface typeFace = TypefaceUtils.getTypeFace(context, typefaceId);

        if (typeFace != null) {
            setTypeface(typeFace);
        }
    }

    public interface OnRMClickListener {
        void onRMClick(View v, Dialog dialog);
    }
}
