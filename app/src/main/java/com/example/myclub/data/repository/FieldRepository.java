package com.example.myclub.data.repository;

import com.example.myclub.Interface.AddBookingField;
import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.Interface.LoadListField;
import com.example.myclub.Interface.LoadListTimeCallBack;
import com.example.myclub.data.datasource.FieldDataSource;
import com.example.myclub.data.datasource.TestDataSource;

import java.util.HashMap;
import java.util.Map;

public class FieldRepository {

    private static volatile FieldRepository instance;

    private FieldDataSource fieldDataSource = new FieldDataSource();

    private FieldRepository(){}

    public static FieldRepository getInstance(){
        if (instance == null){
            instance = new FieldRepository();
        }
        return instance;
    }

    public void getListField(LoadListField loadListField){
        fieldDataSource.loadListField(loadListField);
    }

    public void getListTimeByField(String idTeam, LoadListTimeCallBack loadListTimeCallBack){
        fieldDataSource.loadListTime(idTeam,loadListTimeCallBack);
    }

    public  void addbookingField(Map<String,Object> map , AddBookingField addBookingField){
        fieldDataSource.addBookingField(map,addBookingField );


    }
}
