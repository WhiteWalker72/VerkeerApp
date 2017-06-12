package com.proj.my.verkeerapp.gameobjects.screens;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.proj.my.verkeerapp.GamePanel;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.gameobjects.Button;
import com.proj.my.verkeerapp.gameobjects.FinishLine;
import com.proj.my.verkeerapp.gameobjects.Player;
import com.proj.my.verkeerapp.gameobjects.Question;
import com.proj.my.verkeerapp.utils.MathUtils;

import java.util.List;

public class MatchScreen extends Screen {

    private final FinishLine finishLine;
    private final Match match;
    private Long displayResultTime = null;

    public MatchScreen(GamePanel gamePanel, Match match) {
        super(gamePanel);
        Context context = gamePanel.getContext();



        this.match = match;
        this.finishLine = new FinishLine(context.getResources().getDrawable(R.drawable.finish_line));
    }

    @Override
    public boolean onClick(MotionEvent e) {
        if (displayResultTime != null)
            return false;
        List<Button> buttons = getButtons();

        for (int i = 0; i < buttons.size(); i++) {
            if (buttonClicked(e, buttons.get(i))) {
                // Right answer so add score
                if (i == match.getCurrentQuestion().getRightAnswer()) {
                    match.getPlayer1().setScore(match.getPlayer1().getScore() + 1);
                }
                displayResultTime = System.currentTimeMillis();
                match.getCurrentQuestion().getTimer().setSeconds(5);
                handleComputerScore();
            }
        }
        return true;
    }

    private void handleComputerScore() {
        if (MathUtils.getRandom(1,2) == 1) {
            match.getPlayer2().setScore(match.getPlayer2().getScore() + 1);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (match.getCurrentQuestIndex() >= match.getQuestions().size()) {
            if (match.getPlayer1().getScore() > match.getPlayer2().getScore())
                getGamePanel().setCurrentScreen(new WinScreen(match, getGamePanel()));
            else
                getGamePanel().setCurrentScreen(new LoseScreen(match, getGamePanel()));
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

        // Draw question with players
        currQuestion.draw(canvas, match, displayResultTime != null);
        finishLine.draw(canvas, match);
    }

    @Override
    List<Button> getButtons() {
        return match.getCurrentQuestion().getButtons();
    }

}
