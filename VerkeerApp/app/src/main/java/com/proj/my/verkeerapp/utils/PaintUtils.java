package com.proj.my.verkeerapp.utils;


import android.graphics.Color;
import android.graphics.Paint;

public class PaintUtils {

    public static Paint createPaint(int size) {
        Paint paint = new Paint();
        paint.setTextSize(size);
        paint.setColor(Color.WHITE);
        return paint;
    }

}
