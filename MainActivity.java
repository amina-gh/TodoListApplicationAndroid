package com.example.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ListView listviewtodosList;
private ArrayList<TodoList>listOfTodosList = new ArrayList<>();
    private ArrayList<TodoList>listOfTodosListOriginal = new ArrayList<>();

private Databasemanager databasemanager;
private  TodoListAdapter todoListAdapter = new TodoListAdapter(getApplicationContext(), listOfTodosList);

private Button addTodoListButton;
private EditText searchBarListTodoLists;
public static boolean isActionMode = false ;
public static ArrayList<TodoList> UserSelectionListTodoLists = new ArrayList<>();
public static ActionMode actionModeListTodoLists=null;
    public MainActivity() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databasemanager = new Databasemanager(getApplicationContext());
        listviewtodosList=findViewById(R.id.listviewtodos);
        addTodoListButton=findViewById(R.id.addtodobbutton);
        searchBarListTodoLists=findViewById(R.id.search_bar_list_todos);
        listviewtodosList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listviewtodosList.setMultiChoiceModeListener(modeListener);

        listOfTodosListOriginal=databasemanager.getAllTodoists();
        listOfTodosList = databasemanager.getAllTodoists();
        todoListAdapter = new TodoListAdapter(this,listOfTodosList);
        listviewtodosList.setAdapter(todoListAdapter);

       // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listOfTodos);
       // listviewtodos.setAdapter(adapter);
        addTodoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivityAddTodoList = new Intent(getApplicationContext(), addTodoListActivity.class);
                startActivity(startActivityAddTodoList);
            }
        });
        searchBarListTodoLists.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             listOfTodosList=filterArrayListTodoList(charSequence.toString());
             todoListAdapter = new TodoListAdapter(getApplicationContext(),listOfTodosList);
                listviewtodosList.setAdapter(todoListAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        listviewtodosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TodoList todoList=(TodoList)adapterView.getItemAtPosition(i);
                Intent startActivityTodoList =  new Intent(getApplicationContext(),TodoListActivity.class);
                startActivityTodoList.putExtra("TodoList",todoList);
                startActivity(startActivityTodoList);
            }
        });
    }
private ArrayList<TodoList> filterArrayListTodoList(String textFilter){
        ArrayList<TodoList>arrayListTodoListTemp = new ArrayList<>();
        if (textFilter!= null){
            for (int i =0; i<listOfTodosListOriginal.size();i++){
                if(listOfTodosListOriginal.get(i).getName().toUpperCase().contains(textFilter.toUpperCase())){
                    arrayListTodoListTemp.add(listOfTodosListOriginal.get(i));

                }
        }

}
else {
arrayListTodoListTemp =listOfTodosListOriginal;
    }
return arrayListTodoListTemp;
}
AbsListView.MultiChoiceModeListener modeListener= new AbsListView.MultiChoiceModeListener() {
    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
        isActionMode=true;
        actionModeListTodoLists=actionMode;
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.action_delete_menu:
            databasemanager.deleteTodoLists(UserSelectionListTodoLists);

            UpdateDisplayTodoList();
            actionMode.finish();
            return  true;
            default:
                return false;
        }

    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        isActionMode=false;
        actionModeListTodoLists=null;
        UserSelectionListTodoLists.clear();
        UpdateDisplayTodoList();

    }
};
    private void UpdateDisplayTodoList(){
        listOfTodosListOriginal=databasemanager.getAllTodoists();
        listOfTodosList=databasemanager.getAllTodoists();
        todoListAdapter = new TodoListAdapter(getApplicationContext(),listOfTodosList);
listviewtodosList.setAdapter(todoListAdapter);

    }
}