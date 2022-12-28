package com.example.todolistapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TodoListAdapter extends ArrayAdapter<TodoList> {
    public ArrayList<TodoList> todoListArrayList;
    public Databasemanager databasemanager;
    public TodoListAdapter(Context context, ArrayList<TodoList>todoListArrayList){
        super(context,0,todoListArrayList);
        this.todoListArrayList=todoListArrayList;
        this.databasemanager= new Databasemanager(context);

    }
    @NonNull
    @Override
    public View getView(int position , @Nullable View convertView, @NonNull ViewGroup parent ){
        return intView(position,convertView,parent);
    }
    private View intView (int position , View convertView , ViewGroup parent ) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.todolist_list_view_row, parent, false
            );
        }
        TextView textViewTodoisName = convertView.findViewById(R.id.textView_name_todolist_row);
        TodoList currentTodoList = getItem(position);
        if (currentTodoList != null) {
            textViewTodoisName.setText(currentTodoList.getName());
        }
        CheckBox checkBox = convertView.findViewById(R.id.checkbox_for_selected_item_todolist);
        checkBox.setTag(position);
        ImageButton editImageButton = convertView.findViewById(R.id.edit_name_todolist_row);
        ImageButton checkImageButton= convertView.findViewById(R.id.check_edit_name_todolist);
        EditText nameEditText = convertView.findViewById(R.id.edit_name_todolist_row);
        if (MainActivity.isActionMode){
            checkBox.setVisibility(View.VISIBLE);
            editImageButton.setVisibility(View.VISIBLE);
        }else {
            checkBox.setVisibility(View.GONE);
            editImageButton.setVisibility(View.GONE);
        }
         checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 int position = (int) compoundButton.getTag();
                 if (MainActivity.UserSelectionListTodoLists.contains(todoListArrayList.get(position)))
                 {
                     MainActivity.UserSelectionListTodoLists.remove(todoListArrayList.get(position));
                 }
                 else {
                     MainActivity.UserSelectionListTodoLists.add(todoListArrayList.get(position));
                 }
MainActivity.actionModeListTodoLists.setTitle(MainActivity.UserSelectionListTodoLists.size()+"todolist select ...");

             }
         });
        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setVisibility(View.GONE);
                editImageButton.setVisibility(View.GONE);
                textViewTodoisName.setVisibility(View.GONE);

                nameEditText.setVisibility(View.VISIBLE);
                nameEditText.setText(currentTodoList.getName());
                checkImageButton.setVisibility(View.VISIBLE);

            }
        });
        checkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setVisibility(View.VISIBLE);
                editImageButton.setVisibility(View.VISIBLE);
                textViewTodoisName.setVisibility(View.VISIBLE);
                String newText = nameEditText.getText().toString();
                nameEditText.setVisibility(View.GONE);
                checkImageButton.setVisibility(View.GONE);
                textViewTodoisName.setText(newText);

                databasemanager.updateTodoList(newText,currentTodoList.getId());
            }
        });
        return convertView;
    }
}
