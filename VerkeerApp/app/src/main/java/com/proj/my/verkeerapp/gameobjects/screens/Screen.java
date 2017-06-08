package com.proj.my.verkeerapp.gameobjects.screens;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.proj.my.verkeerapp.GamePanel;
import com.proj.my.verkeerapp.gameobjects.Button;

import java.util.List;

public abstract class Screen {

    private final GamePanel gamePanel;

    public Screen(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public abstract boolean onClick(MotionEvent e);

    public abstract void draw(Canvas canvas);

    abstract List<Button> getButtons();

    protected GamePanel getGamePanel() {
        return gamePanel;
    }

}
