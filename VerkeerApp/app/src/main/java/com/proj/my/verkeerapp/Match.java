package com.proj.my.verkeerapp;

import com.proj.my.verkeerapp.gameobjects.Player;
import com.proj.my.verkeerapp.gameobjects.Question;

import java.util.List;

public class Match {

    private final List<Question> questions;
    private final Player player1;
    private final Player player2;
    private int currentQuestIndex = 0;

    public Match(List<Question> questions, Player player1, Player player2) {
        this.questions = questions;
        this.player1 = player1;
        this.player2 = player2;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getFinishScore() {
        return questions.size();
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestIndex);
    }

    public int getCurrentQuestIndex() {
        return currentQuestIndex;
    }

    public void setCurrentQuest(int currentQuestIndex) {
        this.currentQuestIndex = currentQuestIndex;
    }
}
