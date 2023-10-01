package com.example.goodhabits.model;

import java.util.Date;

public class UserInfo {
    private int currentStreak;
    private int highestStreak;
    private Float weight;
    private Float height;
    private String goal;
    private String lastDateDaily;
    private String dateOfBirth;
    private String pathToImageFile;
    private boolean isCompleteDaily = false;  // Dùng cho việc hiển thị sao, nếu một ngày k hoàn thành 4 tác vụ => false
    private int numberDayComplete = 0;       // Lúc này thiết lập lại numberDayComplete hiện tại về 0

    public UserInfo() {
    }

    public boolean isCompleteDaily() {
        return isCompleteDaily;
    }

    public void setCompleteDaily(boolean completeDaily) {
        isCompleteDaily = completeDaily;
    }

    public int getNumberDayComplete() {
        return numberDayComplete;
    }

    public void setNumberDayComplete(int numberDayComplete) {
        this.numberDayComplete = numberDayComplete;
    }

    public String getPathToImageFile() {
        return pathToImageFile;
    }

    public void setPathToImageFile(String pathToImageFile) {
        this.pathToImageFile = pathToImageFile;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getHighestStreak() {
        return highestStreak;
    }

    public void setHighestStreak(int highestStreak) {
        this.highestStreak = highestStreak;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getLastDateDaily() {
        return lastDateDaily;
    }

    public void setLastDateDaily(String lastDateDaily) {
        this.lastDateDaily = lastDateDaily;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
