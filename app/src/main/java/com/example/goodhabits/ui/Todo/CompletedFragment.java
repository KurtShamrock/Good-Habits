package com.example.goodhabits.ui.Todo;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goodhabits.viewmodel.DataViewModel;
import com.example.goodhabits.R;
import com.example.goodhabits.model.MeditatingHistory;
import com.example.goodhabits.model.ReadingHistory;
import com.example.goodhabits.model.Todo;
import com.example.goodhabits.model.WoHistory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CompletedFragment extends Fragment {
    private TextView tvDay1, tvDay2, tvDay3, tvCongralutation;
    private AppCompatButton btnOk;
    private Todo todo;
    private MeditatingHistory meditatingHistory;
    private ReadingHistory readingHistory;
    private WoHistory woHistory;
    private DataViewModel dataViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_completed, container, false);

        tvDay1 = rootView.findViewById(R.id.tvDay1);
        tvDay2 = rootView.findViewById(R.id.tvDay2);
        tvDay3 = rootView.findViewById(R.id.tvDay3);
        tvCongralutation = rootView.findViewById(R.id.tvCongratulation);
        btnOk = rootView.findViewById(R.id.btnOK);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date previousDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date nextDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE", Locale.getDefault());
        String day2 = dateFormat.format(currentDate);
        String day1 = dateFormat.format(previousDate);
        String day3 = dateFormat.format(nextDate);

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        todo = dataViewModel.getTodo();
        meditatingHistory = dataViewModel.getMeditatingHistory();
        woHistory = dataViewModel.getWoHistory();
        readingHistory = dataViewModel.getReadingHistory();

        int count = 4;
        if(todo.isCompleted()) count--;
        if (readingHistory.isCompleted()) count--;
        if(woHistory.isCompleted()) count--;
        if(meditatingHistory.isCompleted()) count--;
        String s = "Hoàn thành "+count+" Task còn lại để hoàn thành Streak tổng ngày hôm nay!";

        tvCongralutation.setText(s);
        tvDay1.setText(day1);
        tvDay2.setText(day2);
        tvDay3.setText(day3);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_layout, new TodoFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootView;
    }
}