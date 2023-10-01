package com.example.goodhabits.ui.Meditating;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.goodhabits.Utils;
import com.example.goodhabits.viewmodel.DataViewModel;
import com.example.goodhabits.R;
import com.example.goodhabits.model.MeditatingHistory;
import com.example.goodhabits.model.ReadingHistory;
import com.example.goodhabits.model.Todo;
import com.example.goodhabits.model.UserInfo;
import com.example.goodhabits.model.WoHistory;
import com.example.goodhabits.viewmodel.SoundViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MeditatingFragment extends Fragment {
    private Todo todo;
    private ReadingHistory readingHistory;
    private WoHistory woHistory;
    private MeditatingHistory meditatingHistory;
    private UserInfo userInfo;
    private DataViewModel dataViewModel;
    private SoundViewModel soundViewModel;
    private Spinner spinnerMin, spinnerSound;
    private ImageView imgPlay, imgStop;
    private TextView tv;
    private ArrayAdapter<String> adapter;
    private LiveData<String> timerData;
    private LiveData<Boolean> isFinished;
    private String currentSound, currentTimer;
    private static boolean check = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meditating, container, false);
        initViews(rootView);

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        userInfo = dataViewModel.getUserInfo();
        todo = dataViewModel.getTodo();
        meditatingHistory = dataViewModel.getMeditatingHistory();
        woHistory = dataViewModel.getWoHistory();
        readingHistory = dataViewModel.getReadingHistory();

        soundViewModel = new SoundViewModel(getActivity().getApplication());
        ViewModelProvider provider = new ViewModelProvider(this);
        soundViewModel = provider.get(SoundViewModel.class);
        provider.get(SoundViewModel.class);
        timerData = soundViewModel.getTimerData();
        isFinished = soundViewModel.getIsFinished();
        timerData.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if (o.toString().isEmpty()) {
                    imgPlay.setEnabled(true);
                    imgStop.setEnabled(false);
                    spinnerMin.setEnabled(true);
                    spinnerSound.setEnabled(true);
                    soundViewModel.onStop(currentSound);
                } else if (o.toString().equals("")) {
                    imgPlay.setEnabled(true);
                    imgStop.setEnabled(false);
                    spinnerMin.setEnabled(true);
                    spinnerSound.setEnabled(true);
                    tv.setText(o.toString());
                } else if (!o.toString().equals("")) {
                    tv.setText(o.toString());
                    imgPlay.setEnabled(false);
                    imgStop.setEnabled(true);
                    spinnerMin.setEnabled(false);
                    spinnerSound.setEnabled(false);
                }
            }
        });

        isFinished.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(isFinished.getValue()){
                    updateData();
                }
                Log.d("MF", "isFinished: "+isFinished.getValue());
            }
        });
        spinnerSound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSound = adapterView.getItemAtPosition(i).toString();
//                dataViewModel.setCurrentSound(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentTimer = adapterView.getItemAtPosition(i).toString();
//                dataViewModel.setCurrentTimer(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundViewModel.onPlay(currentSound, currentTimer);
            }
        });

        imgStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPlay.setEnabled(false);
                imgStop.setEnabled(true);
                spinnerSound.setEnabled(false);
                spinnerMin.setEnabled(false);

//                soundViewModel.setTimerData("");
                if(check){
                    soundViewModel.continueCountDownTimer(currentSound);
                    check = false;
                } else {
                    soundViewModel.onStop(currentSound);
                    check = true;
                }
//                tv.setText("");
            }
        });
        //
        checkingStreak();
        //
        return rootView;
    }

    private void initViews(View rootView) {
        tv = rootView.findViewById(R.id.textView);
        spinnerMin = rootView.findViewById(R.id.spinnerMinutes);
        spinnerSound = rootView.findViewById(R.id.spinnerSound);
        imgPlay = rootView.findViewById(R.id.playBtn);
        imgStop = rootView.findViewById(R.id.stopBtn);

        String[] minutes = new String[]{"2 minutes", "5 minutes", "10 minutes",
                "15 minutes", "30 minutes", "60 minutes"};
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, minutes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMin.setAdapter(adapter);
        spinnerMin.setPrompt("2 minutes");

        String[] sounds = new String[]{"rain", "flame", "forrest"};
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sounds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSound.setAdapter(adapter);
        spinnerSound.setPrompt("rain");
    }

    public void checkingStreak() {
        boolean reading = readingHistory.isCompleted();
        boolean exercise = woHistory.isCompleted();
        boolean checkingToDo = todo.isCompleted();
        boolean meditate = meditatingHistory.isCompleted();

        int numberDayComplete = userInfo.getNumberDayComplete();
        int currentStreak = userInfo.getCurrentStreak();
        int highestStreak = userInfo.getHighestStreak();

        if (reading && exercise && checkingToDo && meditate) {
            userInfo.setCompleteDaily(true);
            if (numberDayComplete > 0) userInfo.setNumberDayComplete(++numberDayComplete);
            if (currentStreak == highestStreak) {
                userInfo.setCurrentStreak(++currentStreak);
                userInfo.setHighestStreak(++highestStreak);
            } else userInfo.setCurrentStreak(++currentStreak);
        } else {
            userInfo.setCompleteDaily(false);
            userInfo.setNumberDayComplete(0);
        }
    }

    private void updateData() {
        // Day du lieu len
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        // Thiet lap gia tri hoan thanh meditate, dung de checkingStreak
        meditatingHistory.setCompleted(true);
        meditatingHistory.setDate(currentDate);
        // Chuyen man hinh
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new CompletedMeditateFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStop() {
        soundViewModel.turnOffPlayer(currentSound);
        super.onStop();
    }
}