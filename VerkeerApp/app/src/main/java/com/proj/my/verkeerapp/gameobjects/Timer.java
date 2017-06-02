package com.proj.my.verkeerapp.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.utils.PaintUtils;

public class Timer {

    private final int startSeconds;
    private int seconds;
    private final Drawable texture;
    private long lastTime;

    public Timer(int seconds, Drawable texture) {
        this.startSeconds = seconds;
        this.seconds = seconds;
        this.texture = texture;
        lastTime = System.currentTimeMillis();
    }

    public void draw(Canvas canvas) {
        System.out.println(System.currentTimeMillis() - lastTime);
        if (seconds > 0 && System.currentTimeMillis() - lastTime >= 1000) {
            seconds -= 1;
            lastTime = System.currentTimeMillis();
        }
        int size = Constants.SCREEN_WIDTH/12;
        Rect imageBounds = new Rect(Constants.SCREEN_WIDTH - size, Constants.SCREEN_HEIGHT - size,
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        texture.setBounds(imageBounds);
        texture.draw(canvas);

        Paint secondsPaint = PaintUtils.createPaint(40);
        String text = "" + seconds;
        float halfTextSize = secondsPaint.measureText(text, 0, text.length()) / 2;
        canvas.drawText(text, Constants.SCREEN_WIDTH - halfTextSize - (size / 2), Constants.SCREEN_HEIGHT + (halfTextSize / 2) - (size / 2), secondsPaint);
    }

    public int getSeconds() {
        return seconds;
    }

    public void resetTime() {
        this.seconds = startSeconds;
    }

}
