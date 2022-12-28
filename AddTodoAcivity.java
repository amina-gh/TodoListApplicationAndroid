package com.example.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTodoAcivity extends AppCompatActivity {
private String TodoName;
private Databasemanager databasemanager;
private int IdTodoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_acivity);
        databasemanager= new Databasemanager(getApplicationContext());
        IdTodoList=getIntent().getIntExtra("TodoListId",  0);
        Button confirmBtn = findViewById(R.id.confirmAddTodoButton);
        EditText todoNameEditText =findViewById(R.id.TodoNameEditText1);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoName = todoNameEditText.getText().toString();
                databasemanager.insertTodo(TodoName,IdTodoList);
                Intent intent = new Intent();
                intent.putExtra("RESULT_ADD_TODO","OK");
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });


    }
}