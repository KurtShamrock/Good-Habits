package com.example.goodhabits.model;

public class WoHistory {
    private boolean isCompleted = true; // (default = false)

    public WoHistory() {

    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
