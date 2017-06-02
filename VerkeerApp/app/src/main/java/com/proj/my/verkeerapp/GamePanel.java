package com.proj.my.verkeerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.proj.my.verkeerapp.gameobjects.FinishLine;
import com.proj.my.verkeerapp.gameobjects.Player;
import com.proj.my.verkeerapp.gameobjects.Question;

import java.util.Arrays;
import java.util.List;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private final FinishLine finishLine;
    private final Match match;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.thread = new MainThread(getHolder(), this);
        setFocusable(true);

        Question question = new Question("Welke fiets mag eerst?",
                Arrays.asList("De blauwe fiets", "De gele fiets"), 1, context);
        Player player1 = new Player("Henkie18", context.getResources().getDrawable(R.drawable.small_green), 0, 0, 4);
        Player player2 = new Player("Sjaakie", context.getResources().getDrawable(R.drawable.small_red), 0, 0, 10);

        this.match = new Match(Arrays.asList(question), player1, player2);
        this.finishLine = new FinishLine(context.getResources().getDrawable(R.drawable.finish_line));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            List<Rect> buttons = match.getCurrentQuestion().getButtons();
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).contains((int) e.getX(), (int) e.getY())) {
                    //TODO: add click action
                    
                }
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

        // Draw question  with players
        match.getCurrentQuestion().draw(canvas, match);
        finishLine.draw(canvas, match);
    }

    public void update() {

    }

}
