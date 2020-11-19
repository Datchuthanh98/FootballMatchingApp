package com.example.myclub.data.repository;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.RequestJoinTeamDataSource;


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

    public void addRequestJoinTeam(String idTeam,Map<String, Object> requestJoin, CallBack<String,String> addRequestJoinTeam){
        requestJoinTeamDataSource.addRequest(idTeam,requestJoin, addRequestJoinTeam);
    }

    public void cancelRequestJoinTeam(String idTeam,String idPlayer, CallBack<String,String> cancelRequestJoinTeam){
        requestJoinTeamDataSource.cancelRequest(idTeam,idPlayer, cancelRequestJoinTeam);
    }

    public void getStateJoinTeam(String idTeam,String idPlayer, CallBack<Boolean,String> getStateJoinTeam){
        requestJoinTeamDataSource.getStateJoinTeamByTeam(idTeam,idPlayer, getStateJoinTeam);
    }

    public void acceptJoinTeam(String idTeam,String idPlayer,  CallBack< String,String > acceptJoinTeam){
        requestJoinTeamDataSource.acceptJoinTeam(idTeam,idPlayer, acceptJoinTeam);
    }

    public void declineJoinTeam(String idTeam,String idPlayer, CallBack<String,String> declineJoinTeam){
        requestJoinTeamDataSource.declineJoinTeam(idTeam,idPlayer, declineJoinTeam);
    }



}
