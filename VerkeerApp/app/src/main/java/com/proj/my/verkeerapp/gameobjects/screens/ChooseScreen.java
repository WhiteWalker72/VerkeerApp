package com.proj.my.verkeerapp.gameobjects.screens;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.GamePanel;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.gameobjects.Button;
import com.proj.my.verkeerapp.gameobjects.Player;
import com.proj.my.verkeerapp.gameobjects.Question;

import java.util.Arrays;
import java.util.List;

public class ChooseScreen extends Screen {

    private final Player player1;
    private final Player player2;

    public ChooseScreen(GamePanel gamePanel) {
        super(gamePanel);
        this.player1 = new Player("Henkie18", gamePanel.getContext().getResources().getDrawable(R.drawable.small_green), 0, 0, 0);
        this.player2 = new Player("Computer", gamePanel.getContext().getResources().getDrawable(R.drawable.small_red), 0, 0, 0);
    }

    @Override
    public boolean onClick(MotionEvent e) {
        for (Button button : getButtons()) {
            if (buttonClicked(e, button)) {
                if (button.getText().equalsIgnoreCase("situaties")) {
                    getGamePanel().setCurrentScreen(new MatchScreen(getGamePanel(),
                            new Match(getSituationQuestions(getGamePanel().getContext()), player1, player2)
                    ));
                    return true;
                } else if (button.getText().equalsIgnoreCase("borden")) {
                    getGamePanel().setCurrentScreen(new MatchScreen(getGamePanel(),
                            new Match(getSignQuestions(getGamePanel().getContext()), player1, player2)
                    ));
                    return true;
                }
            }
        }
        return false;
    }

    private List<Question> getSituationQuestions(Context context) {
        return Arrays.asList(
                new Question("Welke fiets mag eerst?", Arrays.asList("De blauwe fiets", "De gele fiets"),
                       1, context.getResources().getDrawable(R.drawable.situation), context)

        );
    }

    private List<Question> getSignQuestions(Context context) {
        return Arrays.asList(
                new Question("Wat betekent dit bord?", Arrays.asList("Je moet hier omkeren", "Aan het einde van de weg is een sloot", "Deze weg loopt dood"),
                        2, context.getResources().getDrawable(R.drawable.doodlopende_bord), context)
        );
    }

    @Override
    public void draw(Canvas canvas) {
        for (Button button : getButtons())
            button.draw(canvas);
    }

    @Override
    List<Button> getButtons() {
        int borderY = Constants.SCREEN_HEIGHT/20;
        int borderX = Constants.SCREEN_WIDTH/20;
        int buttonLength = (Constants.SCREEN_WIDTH - (2 * borderX))/2;
        Rect rect1 = new Rect(borderX, borderY, borderX + buttonLength, Constants.SCREEN_HEIGHT - borderY);
        Rect rect2 = new Rect(rect1.right + borderX, borderY, rect1.right + buttonLength, Constants.SCREEN_HEIGHT - borderY);

        return Arrays.asList(
                new Button(rect1, getGamePanel().getResources().getDrawable(R.drawable.situation), "Situaties"),
                new Button(rect2, getGamePanel().getResources().getDrawable(R.drawable.borden), "Borden")
        );
    }
}
