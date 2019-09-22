package com.example.syend.tennisnow;

/**
 * Created by syend on 1/13/2018.
 */

public class ConstructH2H {
    private String result;
    private String date;
    private String tournament;
    private String player1;
    private String player2;
    private String score;
    private String mainP;

    public ConstructH2H(String date, String tournament, String player1, String player2, String score) {
        this.date = date;
        this.tournament = tournament;
        this.player1 = player1;
        this.player2 = player2;
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }


    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    @Override
    public String toString() {
        return "#Date: #" + date + " #Tournament: #" + tournament + " #Player1: #" + player1+ " #Player2: #" + player2 + " #Score: #"+ score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
