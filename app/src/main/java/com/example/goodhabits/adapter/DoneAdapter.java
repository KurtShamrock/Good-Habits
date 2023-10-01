package com.example.goodhabits.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.goodhabits.R;
import com.example.goodhabits.model.Todo;

import java.util.List;

public class DoneAdapter extends BaseAdapter {
    private Context context;
    private List<Todo> list;
    private OnTvClickListener onTvClickListener;

    public interface OnTvClickListener{
        void onTvClickListener(Todo task);
    }
    public DoneAdapter(Context context, List<Todo> list) {
        this.context = context;
        this.list = list;
    }
    public void setOnTvClickListener(DoneAdapter.OnTvClickListener onTvClickListener) {
        this.onTvClickListener = onTvClickListener;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        if (view == null) {
            itemView = View.inflate(context, R.layout.list_task, null);
        } else itemView = view;

        CheckBox checkBox = itemView.findViewById(R.id.checkBoxTask);
        TextView textView = itemView.findViewById(R.id.tvContentTask);

        Todo task = list.get(i);
        checkBox.setChecked(task.isStatus());
        textView.setText(task.getContent());
        if (task.isStatus())
            checkBox.setEnabled(false);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTvClickListener != null){
                    onTvClickListener.onTvClickListener(task);
                }
            }
        });

        return itemView;
    }

}
