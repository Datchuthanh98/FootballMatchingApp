package com.example.myclub.data.repository;

import com.example.myclub.data.datasource.TestDataSource;
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
