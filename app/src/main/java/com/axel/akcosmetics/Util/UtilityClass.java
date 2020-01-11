package com.axel.akcosmetics.Util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UtilityClass {


    public static final String TOOLBAR_TITLE = "toolbar_title";

    public static void textViewScaleIconLeft(Context context, View v, int imageId, double opacity) {

        int alpha = (int) (255 * opacity);

        EditText conatiner = (EditText) v;
        int imageResource = imageId;
        Drawable drawable = ContextCompat.getDrawable(context, imageResource);
        drawable.setAlpha(alpha);
        int pixelDrawableSize = (int) Math.round(conatiner.getLineHeight() * 1); // Or the percentage you like (0.8, 0.9, etc.)
        drawable.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize); // setBounds(int left, int top, int right, int bottom), in this case, drawable is a square image
        conatiner.setCompoundDrawables(
                drawable,//left
                null, //top
                null, //right
                null //bottom
        );
    }

    public static void buttonScaleIconLeft(Context context, View v, int imageId, double opacity) {
        int alpha = (int) (255 * opacity);

        Button conatiner = (Button) v;
        int imageResource = imageId;
        Drawable drawable = ContextCompat.getDrawable(context, imageResource);
        drawable.setAlpha(alpha);
        int pixelDrawableSize = (int) Math.round(conatiner.getLineHeight() * 1); // Or the percentage you like (0.8, 0.9, etc.)
        drawable.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize); // setBounds(int left, int top, int right, int bottom), in this case, drawable is a square image
        conatiner.setCompoundDrawables(
                drawable,//left
                null, //top
                null, //right
                null //bottom
        );
    }

    public static void buttonScaleIconRight(Context context, View v, int imageId, double opacity, double size) {
        int alpha = (int) (255 * opacity);

        Button conatiner = (Button) v;
        int imageResource = imageId;
        Drawable drawable = ContextCompat.getDrawable(context, imageResource);
        drawable.setAlpha(alpha);
        int pixelDrawableSize = (int) Math.round(conatiner.getLineHeight() * size); // Or the percentage you like (0.8, 0.9, etc.)
        drawable.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize); // setBounds(int left, int top, int right, int bottom), in this case, drawable is a square image
        conatiner.setCompoundDrawables(
                null,//left
                null, //top
                drawable, //right
                null //bottom
        );
    }


    public static String getNumberFormat(double price) {
        String price2;
        if (price % 1 == 0) {
            DecimalFormat df = new DecimalFormat("#0.0");
            df.setRoundingMode(RoundingMode.DOWN);
            price2 = df.format(price);

        } else {
            DecimalFormat df = new DecimalFormat("#0.00");
            price2 = String.valueOf(df.format(price));
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String currency = String.valueOf(nf.format(23.2).charAt(0));
        return String.valueOf(currency + price2);
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


}
