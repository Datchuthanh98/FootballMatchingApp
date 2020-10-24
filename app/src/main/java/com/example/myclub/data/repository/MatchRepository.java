package com.example.myclub.data.repository;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.MatchDataSource;
import com.example.myclub.model.Booking;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Match;
import com.example.myclub.model.Team;

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

    public void getListMyMatch(String idPlayer ,CallBack<List<Match>,String> loadListMatchCallBack){
        matchDataSource.loadListMyMatch(idPlayer,loadListMatchCallBack);
    }

    public void getInformationMatch(String idTeam , CallBack<Match,String> getInformationMatch){
        matchDataSource.getMatchDetail(idTeam,getInformationMatch);
    }

    public void getlistQueueTeam(String idTeam , CallBack<List<Team>,String> getListQueueTeam){
        matchDataSource.getListQueueTeam(idTeam,getListQueueTeam);
    }

    public void getlistComment(String idMatch , CallBack<List<Comment>,String> getListComment){
        matchDataSource.getListComment(idMatch,getListComment);
    }

    public void addComment(Map<String,Object> map , CallBack<String,String> addCommentCallback){
        matchDataSource.addComment(map,addCommentCallback);
    }

    public void addQueueTeam(Map<String,Object> map , CallBack<String,String> addQueueTeamCallback){
        matchDataSource.addQueueTeam(map,addQueueTeamCallback);
    }

    public void acceptTeam(String idBooking ,Map<String, Object> map, CallBack<String, String> acceptCallBack) {
        matchDataSource.acceptTeam(idBooking,map,acceptCallBack);
    }
}
