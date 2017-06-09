package com.proj.my.verkeerapp.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.utils.PaintUtils;

public class Button {

    private final Rect rect;
    private Drawable texture;
    private final String text;

    public Button(Rect rect, Drawable texture) {
        this(rect, texture, null);
    }

    public Button(Rect rect, Drawable texture, String text) {
        this.rect = rect;
        this.texture = texture;
        this.text = text;
        texture.setBounds(rect);
    }

    public void draw(Canvas canvas) {
        draw(canvas, texture, text);
    }

    public void draw(Canvas canvas, Drawable texture, String text) {
        texture.setBounds(rect);
        texture.draw(canvas);
        if (text == null)
            return;

        Paint paint = PaintUtils.createPaint(10);
        Rect bounds = new Rect();
        while (paint.measureText(text, 0, text.length()) < rect.width() - (rect.width()/3)
                && bounds.height() + rect.height()/2 < rect.height()) {
            paint.setTextSize(paint.getTextSize() + 1);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }
        canvas.drawText(text, rect.centerX() - (paint.measureText(text, 0, text.length() / 2)),
                rect.centerY() + (getHeight(paint, text)/2), paint);
    }

    private int getHeight(Paint paint, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    public Rect getRect() {
        return rect;
    }

    public void setTexture(Drawable texture) {
        this.texture = texture;
    }

    public String getText() {
        return text;
    }
    
}
