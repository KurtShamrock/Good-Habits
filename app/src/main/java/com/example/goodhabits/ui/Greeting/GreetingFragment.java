package com.example.goodhabits.ui.Greeting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodhabits.viewmodel.DataViewModel;
import com.example.goodhabits.R;
import com.example.goodhabits.model.MeditatingHistory;
import com.example.goodhabits.model.ReadingHistory;
import com.example.goodhabits.model.Todo;
import com.example.goodhabits.model.User;
import com.example.goodhabits.model.UserInfo;
import com.example.goodhabits.model.WoHistory;
import com.example.goodhabits.ui.Meditating.MeditatingFragment;
import com.example.goodhabits.ui.Todo.TodoFragment;

import java.util.Calendar;

public class GreetingFragment extends Fragment {

    private User user;
    private UserInfo userInfo;
    private Todo todo;
    private MeditatingHistory meditatingHistory;
    private ReadingHistory readingHistory;
    private WoHistory woHistory;
    private TextView hello_user, current_streak;
    private ImageView btnExercise, btnReading, btnMeditate, btnTodo;
    private DataViewModel dataViewModel;
    private static boolean check = true;
    private long lastCheckDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_greeting, container, false);
        Log.d("GreetingFragment", "onCreateView");
        hello_user = rootView.findViewById(R.id.hello_user);
        current_streak = rootView.findViewById(R.id.tvCurrentStreakGreeting);
        btnExercise = rootView.findViewById(R.id.btnExercise);
        btnReading = rootView.findViewById(R.id.btnReading);
        btnMeditate = rootView.findViewById(R.id.btnMeditate);
        btnTodo = rootView.findViewById(R.id.btnTodo);

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        user = dataViewModel.getUser();
        userInfo = dataViewModel.getUserInfo();
        todo = dataViewModel.getTodo();
        meditatingHistory = dataViewModel.getMeditatingHistory();
        woHistory = dataViewModel.getWoHistory();
        readingHistory = dataViewModel.getReadingHistory();
        lastCheckDay = dataViewModel.getLastCheckDay();

        //pseudo data
        if ((userInfo != null) && check && (user != null)) {
            userInfo.setCurrentStreak(12);
            userInfo.setCompleteDaily(false);
            userInfo.setHighestStreak(36);
            userInfo.setNumberDayComplete(6);
            userInfo.setPathToImageFile("https://oyster.ignimgs.com/mediawiki/apis.ign.com/pokemon-blue-version/8/89/Pikachu.jpg");
            user.setFullName("Bùi Lê Dũng");
            user.setEmail("leedung201@gmail.com");
            user.setPhoneNumber("0123456789");
            check = false;
        }
        //
        hello_user.setText("Hello " + user.getFullName());
        current_streak.setText(String.valueOf(userInfo.getCurrentStreak()));

        // Meditate
        btnMeditate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScreen(new MeditatingFragment());
            }
        });
        // TodoList
        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScreen(new TodoFragment());
            }
        });
        // Neu qua ngay moi thi reset isCompleted ve false
        resetFlag();

        return rootView;
    }

    public void changeScreen(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void resetFlag() {
        long currentTimeMillis = System.currentTimeMillis();

        Calendar currentDay = Calendar.getInstance();
        currentDay.setTimeInMillis(currentTimeMillis);

        Calendar lastCheckCalendar = Calendar.getInstance();
        lastCheckCalendar.setTimeInMillis(lastCheckDay);

        if (currentDay.get(Calendar.DAY_OF_YEAR) != lastCheckCalendar.get(Calendar.DAY_OF_YEAR)) {
            meditatingHistory.setCompleted(false);
            todo.setCompleted(false);
            woHistory.setCompleted(false);
            readingHistory.setCompleted(false);

            dataViewModel.setLastCheckDay(currentTimeMillis);
        }

    }

}