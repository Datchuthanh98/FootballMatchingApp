package com.example.myclub.data.repository;

import com.example.myclub.data.firestore.TodoDataSource;
import com.example.myclub.Interface.FirebaseLoadListTodo;

public class TestRepository {

    private static volatile TestRepository instance;

    private TodoDataSource todoDataSource = new TodoDataSource();

    private TestRepository(){}

    public static TestRepository getInstance(){
        if (instance == null){
            instance = new TestRepository();
        }
        return instance;
    }

    public void getAllTodo(FirebaseLoadListTodo callback){
        todoDataSource.loadListTodo(callback);
    }
}
