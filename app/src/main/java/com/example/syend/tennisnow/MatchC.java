package com.example.syend.tennisnow;

import java.util.ArrayList;

/**
 * Created by syend on 12/6/2017.
 */

public class MatchC {

    private String date;
    private String time;
    private String status;
    private String tb;
    private ArrayList<String> player1;
    private ArrayList<String> player2;

    public MatchC(String date, String time, String status, String tb, ArrayList<String> player1, ArrayList<String> player2) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.tb = tb;
        this.player1 = player1;
        this.player2 = player2;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getStatus() {
        return status;
    }
    public String getTb() {
        return tb;
    }
    public ArrayList<String> getPlayer1() {
        return player1;
    }
    public ArrayList<String> getPlayer2() {
        return player2;
    }
    public String toString() {
        String p1 ="";
        String p2 = "";
        for(int i =0; i<player1.size(); i++) {
            p1 += "#"+player1.get(i) + " ";
        }
        for(int i =0; i<player2.size(); i++) {
            p2 += "#"+player2.get(i) + " ";
        }
        return "LIVE: " + "Date: " + date  + " Time: " + time + " Status: " +  status + " Tiebreak?: " + tb + p1 + p2;
    }
}
