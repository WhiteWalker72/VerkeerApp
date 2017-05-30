package com.proj.my.verkeerapp.gameobjects;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.utils.PaintUtils;

public class FinishLine {

    private final Drawable finishLine;

    public FinishLine(Drawable finishLine) {
        this.finishLine = finishLine;
    }

    public void draw(Canvas canvas, Match match) {
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        int space = Constants.SCREEN_WIDTH/50;
        int y2 = Constants.SCREEN_HEIGHT/10 + Constants.SCREEN_HEIGHT/6;

        Rect imageBounds = new Rect(
                space, // x1
                Constants.SCREEN_HEIGHT/10, // y1
                Constants.SCREEN_WIDTH - space, // x2
                y2); // y2
        finishLine.setBounds(imageBounds);
        finishLine.draw(canvas);

        int xDiff = Constants.SCREEN_WIDTH -  2 * space;
        int playerSize = Constants.SCREEN_WIDTH/15;

        // Draw player1
        int lowX1 = space + xDiff/match.getFinishScore() * player1.getScore();
        Rect player1Bounds = new Rect(lowX1, y2 - playerSize,
                lowX1 + playerSize, y2);
        player1.getTexture().setBounds(player1Bounds);
        player1.getTexture().draw(canvas);

        // Draw player1 name
        Paint playerPaint = PaintUtils.createPaint(20);
        canvas.drawText(player1.getName(), lowX1, y2 - playerSize - 10, playerPaint);

        // Draw player2
        int lowX2 = space + xDiff/match.getFinishScore() * player2.getScore();
        Rect player2Bounds = new Rect(lowX2, y2 - playerSize,
                lowX2 + playerSize, y2);
        player2.getTexture().setBounds(player2Bounds);
        player2.getTexture().draw(canvas);

        // Draw player2 name
        canvas.drawText(player2.getName(), lowX2, y2 - playerSize - 10, playerPaint);
    }

}
