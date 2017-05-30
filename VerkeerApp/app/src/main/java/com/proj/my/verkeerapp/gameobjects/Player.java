package com.proj.my.verkeerapp.gameobjects;

import android.graphics.drawable.Drawable;

public class Player {

    private final String name;
    private final Drawable texture;
    private int wins;
    private int losses;
    private int score;

    public Player(String name, Drawable texture, int wins, int losses, int score) {
        this.name = name;
        this.texture = texture;
        this.wins = wins;
        this.losses = losses;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Drawable getTexture() {
        return texture;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

}
