package com.example.todolistapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Databasemanager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TodoListApp.db";

    private static final int DATABASE_VERSION = 2;
    public Databasemanager  (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql="create table TodoList(" +"id integer primary key autoincrement,"
                +"name text not null"
                +")";
        db.execSQL(strSql);
        String strSql2 =" create table Todo("
                +"id integer primary key autoincrement, "
                +"name text not null,"
                +"isDone bool not null,"
                +"IdTodoList int not null"
                +")";
        db.execSQL(strSql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    String strSql = "DROP TABLE Todo";
    db.execSQL(strSql);
        String strSql2 =" create table Todo("
                +"id integer primary key autoincrement, "
                +"name text not null,"
                +"isDone int not null,"
                +"IdTodoList int not null"
                +")";
        db.execSQL(strSql2);
    }
    public ArrayList<TodoList> getAllTodoists() {
        ArrayList<TodoList> listtodos = new ArrayList<TodoList>();
        String strSql = " select  * from TodoList";

        Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
        if (!cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String todolistName = cursor.getString(cursor.getColumnIndex("name"));
            todolistName = todolistName.replace("((S))","");
                TodoList todoListObj = new TodoList();
                todoListObj.setId(id);
                todoListObj.setName(todolistName);
                listtodos.add(todoListObj);
                cursor.moveToNext();
            }
        }
        return listtodos;
        }
        public void insertTodoList(String todoListName){
        String name = todoListName.replace("'","((%))");
        String strSql = "INSERT INTO TodoList"
                +"(name)VALUES('"
                + name+"')";
        this.getWritableDatabase().execSQL(strSql);
        }
        public void updateTodoList(String newText,int idTodoList){
        newText=newText.replace("'","((%))");
        String strSql ="UPDATE TodoList SET name ='" + newText + "'WHERE  id =" + idTodoList;
        this.getWritableDatabase().execSQL(strSql);
        }
        public void deleteTodoLists(ArrayList<TodoList>todoListArrayList){

        for (int i = 0; i<todoListArrayList.size(); i++)
        {
String strSQL = "DELETE FROM TodoList WHERE id = "+todoListArrayList.get(i).getId();
        this.getWritableDatabase().execSQL(strSQL);

        String strSql = "DELETE FROM todo WHERE idTodoList = "+ todoListArrayList.get(i).getId();
        this.getWritableDatabase().execSQL(strSql);

        }
        }
        public ArrayList<Todo>getAllTodos(int todoListId) {
            ArrayList<Todo> todos = new ArrayList<>();
            String strSql = "SELECT * FROM todo WHERE idTodoList =" + todoListId;
            Cursor cursor = this.getWritableDatabase().rawQuery(strSql, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    boolean isDone = cursor.getInt(cursor.getColumnIndex("isDone")) > 0;
Todo todo = new Todo();
todo.setId(id);
todo.setName(name);
todo.setDone(isDone);

todos.add(todo);
cursor.moveToNext();

                }
            }
            return todos;
        }
        public void insertTodo(String todoName,int todoListId){
        todoName=todoName.replace("'","((%))");
        String strSql = "INSERT INTO Todo (name,isDone,idTodoList)VALUES('"+todoName+"',"+0+","+todoListId+")";
        this.getWritableDatabase().execSQL(strSql);
        }
        public void updateTaskStatus(int newIsDone,int todoId){
        String strSql ="UPDATE TODO SET isDone = "+ newIsDone + "WHERE id =" + todoId;
        this.getWritableDatabase().execSQL(strSql);
        }
}


