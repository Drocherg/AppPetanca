package com.example.apppetanca;

// PlayerModel.java
public class PlayerModel {
    private int points;

    public PlayerModel() {
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        this.points++;
    }

    public void resetPoints() {
        this.points = 0;
    }
}

