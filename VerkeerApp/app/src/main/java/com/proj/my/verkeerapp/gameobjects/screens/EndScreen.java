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

public class EndScreen extends Screen {

    private final Match match;
    private final Drawable fireworks;
    private final Drawable frame;

    private final Drawable wonText;
    private final Drawable firework;
    private List<Rect> fireworkList = new ArrayList<>();
    private long systemMillis;

    public EndScreen(Match match, GamePanel gamePanel) {
        super(gamePanel);
        Context context = gamePanel.getContext();

        this.match = match;
        this.fireworks = context.getResources().getDrawable(R.drawable.fireworks);
        this.frame = context.getResources().getDrawable(R.drawable.frame);
        this.wonText = context.getResources().getDrawable(R.drawable.won_text);
        this.firework = context.getResources().getDrawable(R.drawable.firework);
        this.systemMillis = System.currentTimeMillis();
        addFireWork();
    }

    private void addFireWork() {
        if (fireworkList.size() > 5)
            return;
        int y1 = Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/7;
        int y2 = Constants.SCREEN_HEIGHT;
        int extraX = MathUtils.getRandom(0, Constants.SCREEN_WIDTH/10);

        if (MathUtils.getRandom(1,2) == 1) {
            fireworkList.add(new Rect(extraX, y1, Constants.SCREEN_WIDTH/20 + extraX, y2));
        } else {
            fireworkList.add(new Rect(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH/20 - extraX, y1, Constants.SCREEN_WIDTH - extraX, y2));
        }
    }

    @Override
    public boolean onClick(MotionEvent e) {
        return false;
    }

    @Override
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
        int textLength = Constants.SCREEN_WIDTH/2;
        border = (Constants.SCREEN_WIDTH - textLength) / 2;
        Rect textBounds = new Rect(border, 200, border + textLength, 200 + Constants.SCREEN_HEIGHT/5);
        wonText.setBounds(textBounds);
        wonText.draw(canvas);

        // Draw player text
        drawPlayerText(canvas, match.getPlayer1().getScore() > match.getPlayer2().getScore()
                ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 40);
        drawPlayerText(canvas,match.getPlayer1().getScore() <= match.getPlayer2().getScore()
                ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 100);

        if (System.currentTimeMillis() - systemMillis >= 2000) {
            systemMillis = System.currentTimeMillis();
            addFireWork();
        }

        // Display fireworks
        List newFireWorkList = new ArrayList();
        for (Rect rect : fireworkList)
            if (rect.bottom > 0)
                newFireWorkList.add(rect);
        fireworkList = newFireWorkList;
        if (fireworkList.isEmpty())
            addFireWork();

        for (Rect rect : fireworkList) {
            rect.set(rect.left, rect.top - 10, rect.right, rect.bottom - 10);
            firework.setBounds(rect);
            firework.draw(canvas);
        }
    }

    @Override
    List<Button> getButtons() {
        return null;
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
