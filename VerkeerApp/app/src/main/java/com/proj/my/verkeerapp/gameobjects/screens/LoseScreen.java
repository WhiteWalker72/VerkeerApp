package com.proj.my.verkeerapp.gameobjects.screens;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.GamePanel;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.gameobjects.Button;

import java.util.List;

public class LoseScreen extends EndScreen {

    private final Drawable loseText;
    private final Drawable background;

    public LoseScreen(Match match, GamePanel gamePanel) {
        super(match, gamePanel);
        this.loseText = gamePanel.getContext().getResources().getDrawable(R.drawable.lose_text);
        this.background = gamePanel.getContext().getResources().getDrawable(R.drawable.lost_background);
    }

    @Override
    public void draw(Canvas canvas) {
        // Draw background
        Rect backBounds = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        background.setBounds(backBounds);
        background.draw(canvas);

        super.draw(canvas);
        displayTextOnScreen(loseText, canvas);
    }

    @Override
    List<Button> getButtons() {
        return null;
    }

}
