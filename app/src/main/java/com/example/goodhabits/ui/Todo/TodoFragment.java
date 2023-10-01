package com.example.goodhabits.ui.Todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.goodhabits.ui.Dialog.CustomDialog;
import com.example.goodhabits.viewmodel.DataViewModel;
import com.example.goodhabits.adapter.DoneAdapter;
import com.example.goodhabits.adapter.OnGoingAdapter;
import com.example.goodhabits.adapter.OnCheckedChangeListener;
import com.example.goodhabits.R;
import com.example.goodhabits.model.MeditatingHistory;
import com.example.goodhabits.model.ReadingHistory;
import com.example.goodhabits.model.Todo;
import com.example.goodhabits.model.UserInfo;
import com.example.goodhabits.model.WoHistory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TodoFragment extends Fragment {
    private ImageView imgDone, imgOnGoing;
    private FloatingActionButton btnAddTask;
    private ListView listView;
    private List<Todo> todoListOnGoing, todoListDone;
    private OnGoingAdapter onGoingAdapter;
    private DoneAdapter doneAdapter;
    private Todo todo;
    private ReadingHistory readingHistory;
    private WoHistory woHistory;
    private MeditatingHistory meditatingHistory;
    private UserInfo userInfo;
    private DataViewModel dataViewModel;
    private static int number = 0;
    private static boolean check = true; // dung de load pseudo data 1 lan

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        Log.d("TodoFragment", "onCreateView");
        imgDone = rootView.findViewById(R.id.imgDone);
        imgOnGoing = rootView.findViewById(R.id.imgOnGoing);
        btnAddTask = rootView.findViewById(R.id.btnAddTask);
        listView = rootView.findViewById(R.id.lv_TodoList);

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        userInfo = dataViewModel.getUserInfo();
        todo = dataViewModel.getTodo();
        meditatingHistory = dataViewModel.getMeditatingHistory();
        woHistory = dataViewModel.getWoHistory();
        readingHistory = dataViewModel.getReadingHistory();

        todoListDone = todo.getListDoneTask();
        todoListOnGoing = todo.getListOnGoingTask();
        number = todo.getListOnGoingTask().size();
        // pseudo data
        if (check) {
            Todo todo1 = new Todo();
            todo1.setContent("Reading");
            todo1.setStatus(false);
            Todo todo2 = new Todo();
            todo2.setContent("Meditate");
            todo2.setStatus(false);
            Todo todo3 = new Todo();
            todo3.setContent("Checking Todo");
            todo3.setStatus(true);
            number = 2;
            todoListOnGoing.add(todo1);
            todoListOnGoing.add(todo2);
            todoListDone.add(todo3);
            check = false;
        }
        //
        onGoingAdapter = new OnGoingAdapter(getContext(), todoListOnGoing);
        doneAdapter = new DoneAdapter(getContext(), todoListDone);
        listView.setAdapter(onGoingAdapter);
        //
        onDoneTab();
        onGoindTab();
        addNewTask();
        checkingOnGoingList();
        checkingStreak();
        showInfoOnGoingTask();
        showInfoDoneTask();
        //
        return rootView;
    }

    private void showInfoDoneTask() {
        doneAdapter.setOnTvClickListener(new DoneAdapter.OnTvClickListener() {
            @Override
            public void onTvClickListener(Todo task) {
                Dialog newDialog = new Dialog(getContext());
                newDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                newDialog.setContentView(R.layout.custom_dialog);

                AppCompatButton btnCancel = newDialog.findViewById(R.id.btnCancel);
                AppCompatButton btnSave = newDialog.findViewById(R.id.btnSave);
                AppCompatButton btnPickDate = newDialog.findViewById(R.id.btnPickDate);
                AppCompatEditText edtContent = newDialog.findViewById(R.id.edtContent);
                AppCompatEditText edtDueDate = newDialog.findViewById(R.id.edtShowDate);
                RadioButton rbImportant = newDialog.findViewById(R.id.rbImportant);
                RadioButton rbVeryImportant = newDialog.findViewById(R.id.rvVeryImportant);
                RadioButton rbNomarl = newDialog.findViewById(R.id.rbNormal);
                RadioButton rbLeisurely = newDialog.findViewById(R.id.rbLeisurely);
                btnSave.setEnabled(false);
                btnPickDate.setEnabled(false);

                edtContent.setText(task.getContent());
                edtDueDate.setText(task.getDate());
                int value = task.getPriority();

                switch (value) {
                    case 1:
                        rbLeisurely.setChecked(true);
                        break;
                    case 2:
                        rbNomarl.setChecked(true);
                        break;
                    case 3:
                        rbImportant.setChecked(true);
                        break;
                    case 4:
                        rbVeryImportant.setChecked(true);
                        break;
                }

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newDialog.dismiss();
                    }
                });

                newDialog.create();
                newDialog.show();
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showInfoOnGoingTask() {
        onGoingAdapter.setOnTvClickListener(new OnGoingAdapter.OnTvClickListener() {
            @Override
            public void onTvClickListener(Todo task, List<Todo> list) {
                Dialog newDialog = new Dialog(getContext());
                newDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                newDialog.setContentView(R.layout.custom_dialog);

                AppCompatButton btnCancel = newDialog.findViewById(R.id.btnCancel);
                AppCompatButton btnSave = newDialog.findViewById(R.id.btnSave);
                AppCompatButton btnPickDate = newDialog.findViewById(R.id.btnPickDate);
                AppCompatEditText edtContent = newDialog.findViewById(R.id.edtContent);
                AppCompatEditText edtDueDate = newDialog.findViewById(R.id.edtShowDate);
                RadioButton rbImportant = newDialog.findViewById(R.id.rbImportant);
                RadioButton rbVeryImportant = newDialog.findViewById(R.id.rvVeryImportant);
                RadioButton rbNomarl = newDialog.findViewById(R.id.rbNormal);
                RadioButton rbLeisurely = newDialog.findViewById(R.id.rbLeisurely);
                ImageView imgDelete = newDialog.findViewById(R.id.imgDeleteTask);

                edtContent.setText(task.getContent());
                edtDueDate.setText(task.getDate());
                int value = task.getPriority();
                imgDelete.setVisibility(View.VISIBLE);

                switch (value) {
                    case 1:
                        rbLeisurely.setChecked(true);
                        break;
                    case 2:
                        rbNomarl.setChecked(true);
                        break;
                    case 3:
                        rbImportant.setChecked(true);
                        break;
                    case 4:
                        rbVeryImportant.setChecked(true);
                        break;
                }

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newDialog.dismiss();
                    }
                });

                btnPickDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        calendar.set(Calendar.YEAR, year);
                                        calendar.set(Calendar.MONTH, month);
                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        String formattedDate = dateFormat.format(calendar.getTime());

                                        edtDueDate.setText(formattedDate);
                                    }
                                },
                                year, month, day
                        );
                        datePickerDialog.show();
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String dueDate = edtDueDate.getText().toString().trim();
                        String content = edtContent.getText().toString().trim();
                        int priority = 2;
                        boolean status = false;

                        if (rbVeryImportant.isChecked())
                            priority = 4;
                        else if (rbImportant.isChecked()) {
                            priority = 3;
                        } else if (rbNomarl.isChecked()) {
                            priority = 2;
                        } else if (rbLeisurely.isChecked()) {
                            priority = 1;
                        }

                        task.setDate(dueDate);
                        task.setPriority(priority);
                        task.setContent(content);
                        task.setStatus(status);
                        onGoingAdapter.notifyDataSetChanged();

                        Toast.makeText(requireContext(), "Cập nhật task thành công", Toast.LENGTH_SHORT).show();
                        newDialog.dismiss();
                    }
                });

                imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Confirm");
                        builder.setMessage("Bạn chắc chắn muốn xóa Task này!");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                list.remove(task);
                                onGoingAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                number--;
                                newDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });

                newDialog.create();
                newDialog.show();
            }
        });
    }

    private void updateData() {
        // Day du lieu len
        // ...
        // ...

        // Thiet lap gia tri hoan thanh task, dung de checkingStreak
        todo.setCompleted(true);
        // Chuyen man hinh
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new CompletedFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onGoindTab() {
        imgOnGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(onGoingAdapter);
            }
        });
    }

    public void onDoneTab() {
        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(doneAdapter);
            }
        });
    }

    public void addNewTask() {
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog dialog = new CustomDialog(getContext());
                dialog.show();
                dialog.setOnButtonClickListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onCancelClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSaveClick() {
                        String dueDate = dialog.edtDueDate.getText().toString().trim();
                        String content = dialog.edtContent.getText().toString().trim();
                        int priority = 2;
                        boolean status = false;

                        if (dialog.rbVeryImportant.isChecked())
                            priority = 4;
                        else if (dialog.rbImportant.isChecked()) {
                            priority = 3;
                        } else if (dialog.rbNomarl.isChecked()) {
                            priority = 2;
                        } else if (dialog.rbLeisurely.isChecked()) {
                            priority = 1;
                        }

                        Todo task = new Todo();
                        task.setDate(dueDate);
                        task.setPriority(priority);
                        task.setContent(content);
                        task.setStatus(status);

                        number++;
                        todoListOnGoing.add(task);
                        onGoingAdapter.notifyDataSetChanged();

                        Toast.makeText(requireContext(), "Thêm task thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onPickDateClick() {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        calendar.set(Calendar.YEAR, year);
                                        calendar.set(Calendar.MONTH, month);
                                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        String formattedDate = dateFormat.format(calendar.getTime());

                                        dialog.edtDueDate.setText(formattedDate);
                                    }
                                },
                                year, month, day
                        );
                        datePickerDialog.show();
                    }
                });

            }
        });
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

    public void checkingOnGoingList() {
        onGoingAdapter.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onItemChecked(Todo todo, boolean isChecked) {
                if (isChecked) {
                    todo.setStatus(isChecked);
                    todoListDone.add(todo);
                    if (--number == 0) {
                        updateData();
                    }
                }
            }
        });
    }

}