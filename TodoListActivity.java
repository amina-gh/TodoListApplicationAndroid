package com.example.todolistapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {
private  TodoList todoList;
private int todoListId;
private Databasemanager databasemanager;
private ArrayList<Todo>todos;
private ListView listViewTodos;
private TextView todoListNameTextView;
private Button addTodoBtn;
private TodoAdapter adapter;
private ActivityResultLauncher<Intent>launcherAddTodoActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        todoListNameTextView=findViewById(R.id.todolistnametextView);
        listViewTodos=findViewById(R.id.listviewtodos);
        addTodoBtn=findViewById(R.id.addTodoButton);
        databasemanager= new Databasemanager(getApplicationContext());
        todos =new ArrayList<>();


        todoList = getIntent().getParcelableExtra("TodoList");
        todoListId=todoList.getId();

        todoListNameTextView.setText(todoList.getName());
        todos =databasemanager.getAllTodos(todoListId);
        adapter = new TodoAdapter(getApplicationContext(), todos);
        listViewTodos.setAdapter(adapter);
        UpdateDisplay();
        launcherAddTodoActivity=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){

                        String message = result.getData().getStringExtra("RESULT_ADD_TODO");
                        UpdateDisplay();


                    }

                }

        );
        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddTodoActivity = new Intent(getApplicationContext(), AddTodoAcivity.class);
                startAddTodoActivity.putExtra("TodoListId",todoListId);
                launcherAddTodoActivity.launch(startAddTodoActivity);


            }
        });



    }
    private void UpdateDisplay(){
        todos = databasemanager.getAllTodos(todoListId);

        adapter = new TodoAdapter(getApplicationContext(),todos);
        listViewTodos.setAdapter(adapter);
    }
}