package com.example.goodhabits.ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.goodhabits.R;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private AppCompatButton btnPickDate, btnSave, btnCancel;
    public AppCompatEditText edtContent, edtDueDate;
    public RadioButton rbVeryImportant, rbImportant, rbNomarl, rbLeisurely;
    public RadioGroup rgSubChoice1, rgSubChoice2;
    private OnButtonClickListener listener;

    public interface OnButtonClickListener {
        void onCancelClick();

        void onSaveClick();

        void onPickDateClick();
    }

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        btnPickDate = findViewById(R.id.btnPickDate);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        edtContent = findViewById(R.id.edtContent);
        edtDueDate = findViewById(R.id.edtShowDate);
        rbImportant = findViewById(R.id.rbImportant);
        rbVeryImportant = findViewById(R.id.rvVeryImportant);
        rbNomarl = findViewById(R.id.rbNormal);
        rbLeisurely = findViewById(R.id.rbLeisurely);
        rgSubChoice1 = findViewById(R.id.rgSubChoice1);
        rgSubChoice2 = findViewById(R.id.rgSubChoice2);

        rgSubChoice1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rbImportant.setChecked(false);
                rbLeisurely.setChecked(false);
            }
        });

        rgSubChoice2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rbVeryImportant.setChecked(false);
                rbNomarl.setChecked(false);
            }
        });

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnPickDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                if (listener != null) {
                    listener.onCancelClick();
                }
                break;
            case R.id.btnSave:
                if (listener != null) {
                    listener.onSaveClick();
                }
                break;
            case R.id.btnPickDate:
                if (listener != null) {
                    listener.onPickDateClick();
                }
                break;
        }
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

}
