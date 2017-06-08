package com.proj.my.verkeerapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.proj.my.verkeerapp.gameobjects.screens.Screen;
import com.proj.my.verkeerapp.gameobjects.screens.StartScreen;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final MainActivity mainActivity;
    private MainThread thread;

    private Screen currentScreen;

    public GamePanel(Context context, MainActivity mainActivity) {
        super(context);
        this.mainActivity = mainActivity;
        this.currentScreen = new StartScreen(this);
        this.thread = new MainThread(getHolder(), this);

        getHolder().addCallback(this);
        setFocusable(true);
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
        return e.getAction() == MotionEvent.ACTION_DOWN ? currentScreen.onClick(e) : false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);
        currentScreen.draw(canvas);
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

}
