package com.example.goodhabits.model;

public class ReadingHistory {
    private boolean isCompleted = true; // (default = false)

    public ReadingHistory() {

    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
