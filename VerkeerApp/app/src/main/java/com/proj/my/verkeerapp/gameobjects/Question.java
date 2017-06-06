package com.proj.my.verkeerapp.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.utils.PaintUtils;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private final String question;
    private final List<String> answers;
    private final List<Rect> buttons = new ArrayList<>();
    private final int rightAnswer;
    private final Drawable situationImg;
    private final Timer timer;
    private final Drawable buttonTexture;
    private final Drawable greenButtonTexture;
    private final Drawable redButtonTexture;
    // add description

    public Question(String question, List<String> answers, int rightAnswer, Drawable situationImg, Context context) {
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
        this.situationImg = situationImg;
        this.timer = new Timer(10, context.getResources().getDrawable(R.drawable.timer));
        this.buttonTexture = context.getResources().getDrawable(R.drawable.button);
        this.greenButtonTexture = context.getResources().getDrawable(R.drawable.green_button);
        this.redButtonTexture = context.getResources().getDrawable(R.drawable.red_button);
        addButtons();
    }

    public void draw(Canvas canvas, Match match, boolean resultScreen) {
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
        canvas.drawText("Vraag " + (match.getCurrentQuestIndex() + 1) + ": " + question,
                Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 3, questionPaint);

        Paint buttonPaint = new Paint();
        buttonPaint.setColor(Color.BLUE);
        if (resultScreen) {
            for (int i = 0; i < buttons.size(); i++) {
                Drawable texture = i == rightAnswer ? greenButtonTexture : redButtonTexture;
                Rect buttonRect = buttons.get(i);
                texture.setBounds(buttonRect);
                texture.draw(canvas);
                drawRectText(answers.get(i), canvas, buttonRect);
            }
        } else {
            for (int i = 0; i < buttons.size(); i++) {
                Rect buttonRect = buttons.get(i);
                buttonTexture.setBounds(buttonRect);
                buttonTexture.draw(canvas);
                drawRectText(answers.get(i), canvas, buttonRect);
            }
        }

        // Draw timer
        timer.draw(canvas);
    }

    private void addButtons() {
        int space = Constants.SCREEN_WIDTH/20;
        int buttonLength = Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 2 + space;
        int buttonHeight = (((Constants.SCREEN_HEIGHT - space) / 3) * 2) / (answers.size() * 2);
        int buttonY = Constants.SCREEN_HEIGHT / 3 + (2 * space);
        int buttonX = Constants.SCREEN_WIDTH / 2;

        for (int i = 0; i < answers.size(); i++) {
            buttons.add(new Rect(buttonX, buttonY - space,
                    buttonX + buttonLength - space - Constants.SCREEN_WIDTH/10, buttonY + buttonHeight - space));
            buttonY += (buttonHeight * 2);
        }
    }

    private void drawRectText(String text, Canvas canvas, Rect r) {
        int counter = 0;
        int textLength = text.length();
        while (textLength > 0) {
            counter += 3;
            textLength -= 10;
        }

        Paint textPaint = PaintUtils.createPaint(text.length() <= 12 ? 30 : 30 - counter);
        textPaint.setTextAlign(Paint.Align.CENTER);
        int width = r.width();

        int numOfChars = textPaint.breakText(text, true, width, null);
        int start = (text.length() - numOfChars)/2;
        canvas.drawText(text, start ,start+numOfChars, r.exactCenterX(), r.exactCenterY(), textPaint);
    }

    public List<Rect> getButtons() {
        return buttons;
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

    public Timer getTimer() {
        return timer;
    }
}
