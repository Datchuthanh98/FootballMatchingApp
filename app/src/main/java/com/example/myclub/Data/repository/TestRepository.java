package com.example.myclub.Data.repository;

import com.example.myclub.Data.Firebase.TestDataSource;
import com.example.myclub.Interface.FirebaseLoadListTodo;

public class TestRepository {

    private static volatile TestRepository instance;

    private TestDataSource testDataSource = new TestDataSource();

    private TestRepository(){}

    public static TestRepository getInstance(){
        if (instance == null){
            instance = new TestRepository();
        }
        return instance;
    }

    public void getAllTodo(FirebaseLoadListTodo callback){
        testDataSource.loadListTodo(callback);
    }
}
