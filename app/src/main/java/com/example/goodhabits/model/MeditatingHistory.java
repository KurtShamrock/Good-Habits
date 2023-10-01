package com.example.goodhabits.model;

public class MeditatingHistory {
    private boolean isCompleted = false; // (default = false)
    private String date;

    public MeditatingHistory() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
