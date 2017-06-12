package com.proj.my.verkeerapp.gameobjects.screens;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.proj.my.verkeerapp.Constants;
import com.proj.my.verkeerapp.GamePanel;
import com.proj.my.verkeerapp.Match;
import com.proj.my.verkeerapp.R;
import com.proj.my.verkeerapp.gameobjects.Button;
import com.proj.my.verkeerapp.gameobjects.Player;
import com.proj.my.verkeerapp.utils.PaintUtils;

import java.util.Arrays;
import java.util.List;

public abstract class EndScreen extends Screen {

    private final Match match;
    private final Drawable frame;

    public EndScreen(Match match, GamePanel gamePanel) {
        super(gamePanel);
        Context context = gamePanel.getContext();

        this.match = match;
        this.frame = context.getResources().getDrawable(R.drawable.frame);
    }

    protected void drawPlayerText(Canvas canvas, Player p, int y) {
        Paint paint = PaintUtils.createPaint(40);
        Rect bounds = new Rect();
        String text = p.getName() + " had " + p.getScore() + " " + getVraagStr(p.getScore()) + " goed en "
                + (match.getFinishScore() - p.getScore()) + " " + getVraagStr(match.getFinishScore() - p.getScore())
                + " fout";
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        canvas.drawText(text, x, y, paint);
    }

    private String getVraagStr(int aantal) {
        return aantal == 1 ? "vraag" : "vragen";
    }

    @Override
    public boolean onClick(MotionEvent e) {
        for (Button button : getButtons()) {
            if (button.getRect().contains((int) e.getX(), (int) e.getY())) {
                if (button.getText().equalsIgnoreCase("opnieuw")) {
                    getGamePanel().setCurrentScreen(new ChooseScreen(getGamePanel()));
                } else if (button.getText().equalsIgnoreCase("terug")) {
                    getGamePanel().setCurrentScreen(new StartScreen(getGamePanel()));
                }
            }
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        // Draw frame
        int border = Constants.SCREEN_WIDTH/10;
        Rect frameBounds = new Rect(border, border, Constants.SCREEN_WIDTH - border, Constants.SCREEN_HEIGHT - border);
        frame.setBounds(frameBounds);
        frame.draw(canvas);

        // Draw player text
        drawPlayerText(canvas, match.getPlayer1().getScore() > match.getPlayer2().getScore()
                ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 40);
        drawPlayerText(canvas,match.getPlayer1().getScore() <= match.getPlayer2().getScore()
                ? match.getPlayer1() : match.getPlayer2(), Constants.SCREEN_HEIGHT/2 + 100);

        for (Button button : getButtons()) {
            button.draw(canvas);
        }
    }

    protected Match getMatch() {
        return match;
    }

    protected void displayTextOnScreen(Drawable text, Canvas canvas) {
        // Display text on frame
        int textLength = Constants.SCREEN_WIDTH/2;
        int border = (Constants.SCREEN_WIDTH - textLength) / 2;
        Rect textBounds = new Rect(border, 200, border + textLength, 200 + Constants.SCREEN_HEIGHT/5);
        text.setBounds(textBounds);
        text.draw(canvas);
    }

    @Override
    List<Button> getButtons() {
        Drawable buttonTexture = getGamePanel().getContext().getResources().getDrawable(R.drawable.button);

        // Values of frame
        int border = Constants.SCREEN_WIDTH/7;
        int x1 = border;
        int x2 = Constants.SCREEN_WIDTH - border;
        int buttonLength = (x2 - x1)/2;
        int y1 = Constants.SCREEN_HEIGHT/2 + Constants.SCREEN_HEIGHT/6;
        int y2 = Constants.SCREEN_HEIGHT/2 + Constants.SCREEN_HEIGHT/6 + Constants.SCREEN_HEIGHT/10;

        return Arrays.asList(
                new Button(new Rect(x1, y1, x1 + buttonLength, y2), buttonTexture, "Opnieuw"),
                new Button(new Rect(x1 + buttonLength, y1, x1 + (buttonLength * 2), y2), buttonTexture, "Terug")
        );
    }

}
