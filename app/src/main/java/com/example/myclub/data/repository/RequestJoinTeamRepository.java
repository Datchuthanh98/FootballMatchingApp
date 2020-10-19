package com.example.myclub.data.repository;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.RequestJoinTeamDataSource;
import com.example.myclub.model.RequestJoinTeam;

import java.util.Map;

public class RequestJoinTeamRepository {

    private static volatile RequestJoinTeamRepository instance;

    private RequestJoinTeamDataSource requestJoinTeamDataSource = new RequestJoinTeamDataSource();

    private RequestJoinTeamRepository(){}

    public static RequestJoinTeamRepository getInstance(){
        if (instance == null){
            instance = new RequestJoinTeamRepository();
        }
        return instance;
    }

    public void addRequestJoinTeam(Map<String, Object> requestJoin, CallBack<String,String> addRequestJoinTeam){
        requestJoinTeamDataSource.addRequest(requestJoin, addRequestJoinTeam);
    }

    public void cancelRequestJoinTeam(String key, CallBack<String,String> cancelRequestJoinTeam){
        requestJoinTeamDataSource.cancelRequest(key, cancelRequestJoinTeam);
    }

    public void getStateJoinTeam(Map<String, Object> requestJoin, CallBack<RequestJoinTeam,String> getStateJoinTeam){
        requestJoinTeamDataSource.getStateJoinTeamByTeam(requestJoin, getStateJoinTeam);
    }

    public void acceptJoinTeam(Map<String, Object> requestJoin,  CallBack< String,String > acceptJoinTeam){
        requestJoinTeamDataSource.acceptJoinTeam(requestJoin, acceptJoinTeam);
    }

    public void declineJoinTeam(Map<String, Object> requestJoin, CallBack<String,String> declineJoinTeam){
        requestJoinTeamDataSource.declineJoinTeam(requestJoin, declineJoinTeam);
    }



}
