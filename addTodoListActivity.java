package com.example.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addTodoListActivity extends AppCompatActivity {
private String TodoListName;
private Databasemanager databasemanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_list);
        databasemanager= new Databasemanager(getApplicationContext());
        Button confirmBtn = findViewById(R.id.confirm_button);
        EditText todoListNameEditText=findViewById(R.id.TodoListNameEditText);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoListName = todoListNameEditText.getText().toString();
                databasemanager.insertTodoList(TodoListName);
                Intent startTodoListActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startTodoListActivity);
            }
        });
    }
}