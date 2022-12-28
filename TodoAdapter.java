package com.example.todolistapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<Todo> {
    public ArrayList<Todo> todos;
    public Databasemanager databasemanager;

    public TodoAdapter(@NonNull Context context, ArrayList<Todo>todos) {
        super(context, 0, todos);
        this.todos=todos;
        this.databasemanager = new Databasemanager(context);
    }
    @NonNull
    @Override
    public View getView(int position , @Nullable View convertView, @NonNull ViewGroup parent ){
        return intView(position,convertView,parent);
    }
    private View intView (int position , View convertView , ViewGroup parent ) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.todo_listview_row, parent, false
            );
        }
        TextView textViewTodoName = convertView.findViewById(R.id.textView_name_todolist_row);
        CheckBox checkBox=convertView.findViewById(R.id.checkbox_for_task_todo);

        Todo currentTodoList = getItem(position);
        if (currentTodoList != null) {
            textViewTodoName.setText(currentTodoList.getName());
        }
        checkBox.setChecked(currentTodoList.isDone());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    databasemanager.updateTaskStatus(1,currentTodoList.getId());
            }else {
                databasemanager.updateTaskStatus(0,currentTodoList.getId());
            }}
        });

        return convertView;}
}
