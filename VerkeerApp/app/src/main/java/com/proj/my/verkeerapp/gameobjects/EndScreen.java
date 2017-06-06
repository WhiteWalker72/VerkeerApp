package com.proj.my.verkeerapp.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.utils.PaintUtils;

public class EndScreen {

    private final Match match;
    private final Drawable fireworks;
    private final Drawable frame;

    private final Drawable wonText;

    public EndScreen(Match match, Context context) {
        this.match = match;
        this.fireworks = context.getResources().getDrawable(R.drawable.fireworks);
        this.frame = context.getResources().getDrawable(R.drawable.frame);
        this.wonText = context.getResources().getDrawable(R.drawable.won_text);
    }

    public void draw(Canvas canvas) {
        // Draw background
        Rect backBounds = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        fireworks.setBounds(backBounds);
        fireworks.draw(canvas);

        // Draw frame
        int border = Constants.SCREEN_WIDTH/10;
        Rect frameBounds = new Rect(0 + border, border, Constants.SCREEN_WIDTH - border, Constants.SCREEN_HEIGHT - border);
        frame.setBounds(frameBounds);
        frame.draw(canvas);

        // Display text on frame
        int textLength = Constants.SCREEN_WIDTH/3;
        border = (Constants.SCREEN_WIDTH - textLength) / 2;
        Rect textBounds = new Rect(border, 200, border + textLength, 200 + Constants.SCREEN_HEIGHT/5);
        wonText.setBounds(textBounds);
        wonText.draw(canvas);

        drawPlayerText(canvas, match.getPlayer1().getScore() > match.getPlayer2().getScore() ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 40);
        drawPlayerText(canvas,match.getPlayer1().getScore() <= match.getPlayer2().getScore() ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 80);

    }

    private void drawPlayerText(Canvas canvas, Player p, int y) {
        Paint paint = PaintUtils.createPaint(40);
        Rect bounds = new Rect();
        String text = p.getName() + " had " + p.getScore() + " " + getVraagStr(p.getScore()) + " goed en "
                + (match.getFinishScore() - p.getScore()) + " " + getVraagStr(match.getFinishScore() - p.getScore())
                + " fout";
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        canvas.drawText(text, x, y, paint);
    }
 
    private String getVraagStr(int aantal) {
        return aantal == 1 ? "vraag" : "vragen";
    }

}
