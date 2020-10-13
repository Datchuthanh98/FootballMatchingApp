package com.example.myclub.data.repository;

import com.example.myclub.Interface.AcceptJoinTeam;
import com.example.myclub.Interface.AddRequestJoinTeam;
import com.example.myclub.Interface.CancelRequestJoinTeam;
import com.example.myclub.Interface.DeclineJoinTeam;
import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.Interface.GetStateJoinTeam;
import com.example.myclub.data.datasource.RequestJoinTeamDataSource;
import com.example.myclub.data.datasource.TodoDataSource;

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

    public void addRequestJoinTeam(Map<String, Object> requestJoin, AddRequestJoinTeam addRequestJoinTeam){
        requestJoinTeamDataSource.addRequest(requestJoin, addRequestJoinTeam);
    }

    public void cancelRequestJoinTeam(String key, CancelRequestJoinTeam cancelRequestJoinTeam){
        requestJoinTeamDataSource.cancelRequest(key, cancelRequestJoinTeam);
    }

    public void getStateJoinTeam(Map<String, Object> requestJoin, GetStateJoinTeam getStateJoinTeam){
        requestJoinTeamDataSource.getStateJoinTeamByTeam(requestJoin, getStateJoinTeam);
    }

    public void acceptJoinTeam(Map<String, Object> requestJoin, AcceptJoinTeam acceptJoinTeam){
        requestJoinTeamDataSource.acceptJoinTeam(requestJoin, acceptJoinTeam);
    }

    public void declineJoinTeam(Map<String, Object> requestJoin, DeclineJoinTeam declineJoinTeam){
        requestJoinTeamDataSource.declineJoinTeam(requestJoin, declineJoinTeam);
    }



}
