package com.dturi.itemscounter.app;

/**
 * Created by DTuri on 15/04/14.
 */
public class InterestingItems {
    private int ID;
    private String Name;
    private int Score;

    public int GetID() {
        return ID;
    }

    public void SetID(int id) {
        this.ID = id;
    }

    public String GetName() {
        return Name;
    }

    public void SetName(String name) {
        this.Name = name;
    }

    public int GetScore() {
        return Score;
    }

    public void AddOne() {
        Score = Score + 1;
    }

    public void RemoveOne() {
        Score = Score - 1;
    }

    public void SetScore(int score) {
        this.Score = score;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return Name;
    }
}