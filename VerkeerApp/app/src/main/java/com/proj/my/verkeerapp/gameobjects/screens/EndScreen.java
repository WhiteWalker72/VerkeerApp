package com.proj.my.verkeerapp.gameobjects.screens;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.GamePanel;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.gameobjects.Button;
import com.proj.my.verkeerapp.gameobjects.Player;
import com.proj.my.verkeerapp.utils.MathUtils;
import com.proj.my.verkeerapp.utils.PaintUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class EndScreen extends Screen {

    private final Match match;
    private final Drawable frame;

    public EndScreen(Match match, GamePanel gamePanel) {
        super(gamePanel);
        Context context = gamePanel.getContext();

        this.match = match;
        this.frame = context.getResources().getDrawable(R.drawable.frame);
    }

    protected void drawPlayerText(Canvas canvas, Player p, int y) {
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

    @Override
    public boolean onClick(MotionEvent e) {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        // Draw frame
        int border = Constants.SCREEN_WIDTH/10;
        Rect frameBounds = new Rect(0 + border, border, Constants.SCREEN_WIDTH - border, Constants.SCREEN_HEIGHT - border);
        frame.setBounds(frameBounds);
        frame.draw(canvas);

        // Draw player text
        drawPlayerText(canvas, match.getPlayer1().getScore() > match.getPlayer2().getScore()
                ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 40);
        drawPlayerText(canvas,match.getPlayer1().getScore() <= match.getPlayer2().getScore()
                ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 100);
    }

    protected Match getMatch() {
        return match;
    }

    protected void displayTextOnScreen(Drawable text, Canvas canvas) {
        // Display text on frame
        int textLength = Constants.SCREEN_WIDTH/2;
        int border = (Constants.SCREEN_WIDTH - textLength) / 2;
        Rect textBounds = new Rect(border, 200, border + textLength, 200 + Constants.SCREEN_HEIGHT/5);
        text.setBounds(textBounds);
        text.draw(canvas);
    }



}
