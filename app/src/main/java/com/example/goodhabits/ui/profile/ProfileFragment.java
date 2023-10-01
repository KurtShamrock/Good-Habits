package com.example.goodhabits.ui.profile;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.goodhabits.viewmodel.DataViewModel;
import com.example.goodhabits.R;
import com.example.goodhabits.model.Todo;
import com.example.goodhabits.model.User;
import com.example.goodhabits.model.UserInfo;
import com.example.goodhabits.model.MeditatingHistory;
import com.example.goodhabits.model.ReadingHistory;
import com.example.goodhabits.model.WoHistory;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private User user;
    private UserInfo userInfo;
    private DataViewModel dataViewModel;
    private CircleImageView imgUser;
    private TextView tvFullName, tvCurrentStreak, tvHighestStreak;
    private ImageView day1, day2, day3, day4, day5, day6, day7;
    private ImageView imgReading, imgMeditate, imgExercise, imgTodo;
    private View streakBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.d("PofileFragment", "onCreateView");
        mapping(rootView);
        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        user = dataViewModel.getUser();
        userInfo = dataViewModel.getUserInfo();

        tvCurrentStreak.setText(String.valueOf(userInfo.getCurrentStreak()));
        tvHighestStreak.setText(String.valueOf(userInfo.getHighestStreak()));
        tvFullName.setText(user.getFullName());

        // Glide
        String url = userInfo.getPathToImageFile();
        Glide.with(this).load(url).centerCrop().into(imgUser);
        //
        updateStreakBar();
        updateHighestStreakBar();
        updateDailyMission();
        //
        return rootView;
    }

    private void updateDailyMission() {
        WoHistory woHistory = dataViewModel.getWoHistory();
        Todo todo = dataViewModel.getTodo();
        ReadingHistory readingHistory = dataViewModel.getReadingHistory();
        MeditatingHistory meditatingHistory = dataViewModel.getMeditatingHistory();

        if (meditatingHistory.isCompleted()) {
            imgMeditate.setVisibility(View.VISIBLE);
        } else {
            imgMeditate.setVisibility(View.GONE);
        }

        if (readingHistory.isCompleted()) {
            imgReading.setVisibility(View.VISIBLE);
        } else {
            imgReading.setVisibility(View.GONE);
        }

        if (todo.isCompleted()) {
            imgTodo.setVisibility(View.VISIBLE);
        } else {
            imgTodo.setVisibility(View.GONE);
        }

        if (woHistory.isCompleted()) {
            imgExercise.setVisibility(View.VISIBLE);
        } else {
            imgExercise.setVisibility(View.GONE);
        }

    }

    private void updateHighestStreakBar() {
        float percent = userInfo.getCurrentStreak() / (userInfo.getHighestStreak() * 1.0f);
        Resources resources = getResources();
        streakBar.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, percent * 340, resources.getDisplayMetrics());
    }

    private void updateStreakBar() {
        int number = userInfo.getNumberDayComplete();
        Log.d("value", ""+number);
        boolean check = userInfo.isCompleteDaily();
        int count = 0;
        while (check && (number > 0)) {
            showStar(++count);
            if (count == number) break;
        }
    }

    private void showStar(int value) {
        switch (value) {
            case 1:
                day1.setVisibility(View.VISIBLE);
                break;
            case 2:
                day2.setVisibility(View.VISIBLE);
                break;
            case 3:
                day3.setVisibility(View.VISIBLE);
                break;
            case 4:
                day4.setVisibility(View.VISIBLE);
                break;
            case 5:
                day5.setVisibility(View.VISIBLE);
                break;
            case 6:
                day6.setVisibility(View.VISIBLE);
                break;
            case 7:
                day7.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void mapping(View rootView) {
        imgUser = rootView.findViewById(R.id.imgUserProfile);
        tvFullName = rootView.findViewById(R.id.tvFullnameProfile);
        tvCurrentStreak = rootView.findViewById(R.id.tvCurrentStreakProfile);
        tvHighestStreak = rootView.findViewById(R.id.tvHighestStreakProfile);
        day1 = rootView.findViewById(R.id.Count7DayStreak_1);
        day2 = rootView.findViewById(R.id.Count7DayStreak_2);
        day3 = rootView.findViewById(R.id.Count7DayStreak_3);
        day4 = rootView.findViewById(R.id.Count7DayStreak_4);
        day5 = rootView.findViewById(R.id.Count7DayStreak_5);
        day6 = rootView.findViewById(R.id.Count7DayStreak_6);
        day7 = rootView.findViewById(R.id.Count7DayStreak_7);
        imgReading = rootView.findViewById(R.id.imgReadingProfile);
        imgExercise = rootView.findViewById(R.id.imgDoingExerciseProfile);
        imgMeditate = rootView.findViewById(R.id.imgMeditatingProfile);
        imgTodo = rootView.findViewById(R.id.imgCheckingTodoProfile);
        streakBar = rootView.findViewById(R.id.HighestStreakBar);
    }
}