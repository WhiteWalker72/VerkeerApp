package com.proj.my.verkeerapp.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.utils.PaintUtils;

import java.util.List;

public class Question {

    private final String question;
    private final List<String> answers;
    private final int rightAnswer;
    private final Drawable situationImg;
    // add description

    public Question(String question, List<String> answers, int rightAnswer, Drawable situationImg) {
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
        this.situationImg = situationImg;
    }

    public void draw(Canvas canvas, Match match) {
        Player player1 = match.getPlayer1();
        Player player2 = match.getPlayer2();

        int size = Constants.SCREEN_HEIGHT/10 * 7;
        int border = Constants.SCREEN_WIDTH/30;

        // Draw situation image
        Rect imageBounds = new Rect(border, Constants.SCREEN_HEIGHT - size, size, Constants.SCREEN_HEIGHT - border);
        situationImg.setBounds(imageBounds);
        situationImg.draw(canvas);

        // Draw players
        Paint paint = PaintUtils.createPaint(25);

        String player1Text = player1.getName() + ":   " + player1.getScore() + " punten";
        String player2Text = player2.getName() + ":   " + player2.getScore() + " punten";

        // Get the highest text height
        Rect bounds1 = new Rect();
        paint.getTextBounds(player1Text, 0, player1Text.length(), bounds1);
        Rect bounds2 = new Rect();
        paint.getTextBounds(player2Text, 0, player2Text.length(), bounds2);
        int height = bounds1.height() > bounds2.height() ? bounds1.height() : bounds2.height();

        int space = Constants.SCREEN_WIDTH/50;
        canvas.drawText(player1Text, space, height + space, paint);
        canvas.drawText(player2Text, Constants.SCREEN_WIDTH - bounds2.width() - space, height + space, paint);

        // Draw question text
        Paint questionPaint = PaintUtils.createPaint(35);
        canvas.drawText(question, Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 3, questionPaint);
    }

    public void update() {

    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public Drawable getSituationImg() {
        return situationImg;
    }

}