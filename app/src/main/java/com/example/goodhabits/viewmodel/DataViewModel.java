package com.example.goodhabits.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goodhabits.model.MeditatingHistory;
import com.example.goodhabits.model.ReadingHistory;
import com.example.goodhabits.model.Todo;
import com.example.goodhabits.model.User;
import com.example.goodhabits.model.UserInfo;
import com.example.goodhabits.model.WoHistory;

public class DataViewModel extends ViewModel {
    private User user;
    private UserInfo userInfo;
    private Todo todo;
    private WoHistory woHistory;
    private ReadingHistory readingHistory;
    private MeditatingHistory meditatingHistory;
    private MutableLiveData<String> userName = new MutableLiveData<>();
    private Long lastCheckDay;

    public Long getLastCheckDay() {
        return lastCheckDay;
    }

    public void setLastCheckDay(Long lastCheckDay) {
        this.lastCheckDay = lastCheckDay;
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName.setValue(name);
    }

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInfo getUserInfo() {
        if (userInfo == null)
            userInfo = new UserInfo();
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Todo getTodo() {
        if (todo == null)
            todo = new Todo();
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public WoHistory getWoHistory() {
        if (woHistory == null)
            woHistory = new WoHistory();
        return woHistory;
    }

    public void setWoHistory(WoHistory woHistory) {
        this.woHistory = woHistory;
    }

    public ReadingHistory getReadingHistory() {
        if (readingHistory == null)
            readingHistory = new ReadingHistory();
        return readingHistory;
    }

    public void setReadingHistory(ReadingHistory readingHistory) {
        this.readingHistory = readingHistory;
    }

    public MeditatingHistory getMeditatingHistory() {
        if (meditatingHistory == null)
            meditatingHistory = new MeditatingHistory();
        return meditatingHistory;
    }

    public void setMeditatingHistory(MeditatingHistory meditatingHistory) {
        this.meditatingHistory = meditatingHistory;
    }
}
