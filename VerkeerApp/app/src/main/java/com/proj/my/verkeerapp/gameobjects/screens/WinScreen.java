package com.proj.my.verkeerapp.gameobjects.screens;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.GamePanel;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.gameobjects.Button;
import com.proj.my.verkeerapp.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class WinScreen extends EndScreen {

    private final Drawable background;
    private final Drawable wonText;
    private final Drawable firework;
    private List<Rect> fireworkList = new ArrayList<>();
    private long systemMillis;

    public WinScreen(Match match, GamePanel gamePanel) {
        super(match, gamePanel);
        Context context = gamePanel.getContext();

        this.background = context.getResources().getDrawable(R.drawable.fireworks);
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
            fireworkList.add(new Rect(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH/20 - extraX, y1,
                    Constants.SCREEN_WIDTH - extraX, y2));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // Draw background
        Rect backBounds = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        background.setBounds(backBounds);
        background.draw(canvas);

        super.draw(canvas);
        displayTextOnScreen(wonText, canvas);

        // Add firework every 2 seconds
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

}
