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
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.gameobjects.Button;
import com.proj.my.verkeerapp.utils.PaintUtils;

import java.util.ArrayList;
import java.util.List;

public class StartScreen extends Screen {

    private final Drawable buttonTexture;
    private final Drawable icon;
    private final String text = "FietsQuiz";

    public StartScreen(GamePanel gamePanel) {
        super(gamePanel);
        Context context = gamePanel.getContext();

        this.buttonTexture = context.getResources().getDrawable(R.drawable.button);
        this.icon = context.getResources().getDrawable(R.drawable.icon);

        // Set icon bounds
        int border = Constants.SCREEN_HEIGHT/15;
        int iconSize = (Constants.SCREEN_HEIGHT/2);
        int spareSpace = Constants.SCREEN_HEIGHT - (2 * border) - iconSize;
        Rect iconRect = new Rect(spareSpace, border, Constants.SCREEN_WIDTH - spareSpace, iconSize - border);
        icon.setBounds(iconRect);
    }

    @Override
    public boolean onClick(MotionEvent e) {
        List<Button> buttons = getButtons();
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getRect().contains((int) e.getX(), (int) e.getY())) {
                //TODO: add more button actions

                if (i == 1) {
                    getGamePanel().setCurrentScreen(new MatchScreen(getGamePanel()));
                }

            }
        }

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        for (Button button : getButtons())
            button.draw(canvas);
        icon.draw(canvas);

        Paint paint = PaintUtils.createPaint(80);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(
                text, canvas.getWidth() / 2,
                ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) - Constants.SCREEN_HEIGHT/12,  paint
        );
    }

    @Override
    public List<Button> getButtons() {
        int x1 = Constants.SCREEN_WIDTH/10;
        int x2 = Constants.SCREEN_WIDTH - x1;
        int space = Constants.SCREEN_HEIGHT/10;
        int y1 = Constants.SCREEN_HEIGHT/2;
        List<Button> buttons = new ArrayList<>();

        buttons.add(new Button(new Rect(x1, y1, x2, (int) (y1 + space/1.5)), buttonTexture, "Oefenen"));
        y1 += space;
        buttons.add(new Button(new Rect(x1, y1, x2, (int) (y1 + space/1.5)), buttonTexture, "Tegen computer"));
        y1 += space;
        buttons.add(new Button(new Rect(x1, y1, x2, (int) (y1 + space/1.5)), buttonTexture, "Speel online"));
        y1 += space;
        buttons.add(new Button(new Rect(x1, y1, x2, (int) (y1 + space/1.5)), buttonTexture, "Profiel en prestaties"));
        y1 += space;
        buttons.add(new Button(new Rect(x1, y1, x2, (int) (y1 + space/1.5)), buttonTexture, "Logout"));
        return buttons;
    }

}
