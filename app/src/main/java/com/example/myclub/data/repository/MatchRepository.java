package com.example.myclub.data.repository;

import com.example.myclub.Interface.AddBookingField;
import com.example.myclub.Interface.LoadCallBack;
import com.example.myclub.Interface.LoadListBookingCallBack;
import com.example.myclub.Interface.LoadListField;
import com.example.myclub.Interface.LoadListMatchCallBack;
import com.example.myclub.Interface.LoadListTimeCallBack;
import com.example.myclub.data.datasource.FieldDataSource;
import com.example.myclub.data.datasource.MatchDataSource;
import com.example.myclub.model.Booking;

import java.util.List;
import java.util.Map;

public class MatchRepository {

    private static volatile MatchRepository instance;

    private MatchDataSource matchDataSource = new MatchDataSource();

    private MatchRepository(){}

    public static MatchRepository getInstance(){
        if (instance == null){
            instance = new MatchRepository();
        }
        return instance;
    }

    public void getListBooking(LoadCallBack<List<Booking>, String> callBack){
        matchDataSource.loadListBooking(callBack);
    }


    public  void addbookingField(Map<String,Object> map , AddBookingField addBookingField){
        matchDataSource.addBooking(map,addBookingField );
    }

    public void getListMatch(LoadListMatchCallBack loadListMatchCallBack){
        matchDataSource.loadListMatch(loadListMatchCallBack);
    }

}
