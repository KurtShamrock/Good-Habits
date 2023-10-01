package com.example.goodhabits.model;

import java.util.ArrayList;
import java.util.List;

public class Todo {
    private String id;
    private String content;
    private String date;
    private int priority;
    private boolean status;
    private boolean isCompleted = false; // Dùng để check số task onGoing về 0 chưa
    private List<Todo> listOnGoingTask = new ArrayList<>();    // dùng để lưu dữ liệu lấy về
    private List<Todo> listDoneTask = new ArrayList<>();
    public Todo() {

    }
    public List<Todo> getListDoneTask() {
        return listDoneTask;
    }

    public void setListDoneTask(List<Todo> listDoneTask) {
        this.listDoneTask = listDoneTask;
    }

    public List<Todo> getListOnGoingTask() {
        return listOnGoingTask;
    }

    public void setListOnGoingTask(List<Todo> listOnGoingTask) {
        this.listOnGoingTask = listOnGoingTask;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
