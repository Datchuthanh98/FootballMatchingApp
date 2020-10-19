package com.example.myclub.data.repository;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.MatchDataSource;
import com.example.myclub.model.Booking;
import com.example.myclub.model.Match;

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

    public void getListBooking(CallBack<List<Booking>, String> callBack){
        matchDataSource.loadListBooking(callBack);
    }


    public  void addbookingField(Map<String,Object> map , CallBack<String,String> addBookingField){
        matchDataSource.addBooking(map,addBookingField );
    }

    public void getListMatch(CallBack<List<Match>,String> loadListMatchCallBack){
        matchDataSource.loadListMatch(loadListMatchCallBack);
    }

}
