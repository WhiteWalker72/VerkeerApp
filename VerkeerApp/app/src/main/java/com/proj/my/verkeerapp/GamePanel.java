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
import com.proj.my.verkeerapp.gameobjects.EndScreen;
import com.proj.my.verkeerapp.utils.MathUtils;

import java.util.Arrays;
import java.util.List;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private final FinishLine finishLine;
    private final Match match;
    private final EndScreen endScreen;
    private Long displayResultTime = null;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.thread = new MainThread(getHolder(), this);
        setFocusable(true);

        Player player1 = new Player("Henkie18", context.getResources().getDrawable(R.drawable.small_green), 0, 0, 0);
        Player player2 = new Player("Computer", context.getResources().getDrawable(R.drawable.small_red), 0, 0, 0);

        this.match = new Match(getQuestions(context), player1, player2);
        this.finishLine = new FinishLine(context.getResources().getDrawable(R.drawable.finish_line));
        this.endScreen = new EndScreen(match, context);
    }

    private List<Question> getQuestions(Context context) {
        return Arrays.asList(
                new Question("Welke fiets mag eerst?", Arrays.asList("De blauwe fiets", "De gele fiets"),
                         1, context.getResources().getDrawable(R.drawable.situation), context),
                new Question("Wat betekent dit bord?", Arrays.asList("Je moet hier omkeren", "Aan het einde van de weg is een sloot", "Deze weg loopt dood"),
                        2, context.getResources().getDrawable(R.drawable.doodlopende_bord), context)

        );
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
                    // Right answer so add score
                    if (i == match.getCurrentQuestion().getRightAnswer()) {
                        match.getPlayer1().setScore(match.getPlayer1().getScore() + 1);
                    }
                    displayResultTime = System.currentTimeMillis();
                    match.getCurrentQuestion().getTimer().setSeconds(5);
                    handleComputerScore();
                }
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

        if (match.getCurrentQuestIndex() >= match.getQuestions().size()) {
            endScreen.draw(canvas);
            return;
        }

        Question currQuestion = match.getCurrentQuestion();
        if (currQuestion.getTimer().getSeconds() <= 0 && displayResultTime == null) {
            // Set time to show result screen for 5 seconds
            displayResultTime = System.currentTimeMillis();
            currQuestion.getTimer().setSeconds(5);
            handleComputerScore();
        }

        // Go to the next question
        if (displayResultTime != null && System.currentTimeMillis() - displayResultTime >= 5000) {
            match.setCurrentQuest(match.getCurrentQuestIndex() + 1);
            currQuestion = match.getCurrentQuestion();
            displayResultTime = null;
        }

        //// TODO: remove
//        endScreen.draw(canvas);
        // Draw question with players
        currQuestion.draw(canvas, match, displayResultTime != null);
        finishLine.draw(canvas, match);
    }

    private void handleComputerScore() {
        if (MathUtils.getRandom(1,2) == 1) {
            match.getPlayer2().setScore(match.getPlayer2().getScore() + 1);
        }
    }

    public void update() {

    }

}
