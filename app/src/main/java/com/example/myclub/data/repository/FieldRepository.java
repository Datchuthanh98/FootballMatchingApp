package com.example.myclub.data.repository;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.FieldDataSource;
import com.example.myclub.model.Field;
import com.example.myclub.model.TimeGame;


import java.util.HashMap;
import java.util.List;
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

    public void getListField(CallBack<List<Field>,String> loadListField){
        fieldDataSource.loadListField(loadListField);
    }

    public void getListTimeByField(String idTeam, CallBack<List<TimeGame>,String> loadListTimeCallBack){
        fieldDataSource.loadListTime(idTeam,loadListTimeCallBack);
    }


}
