package com.example.myclub.data.session;

import androidx.lifecycle.MutableLiveData;

import com.example.myclub.model.Team;

public class SessionJoinGame {
    private static SessionJoinGame instance;
    private MutableLiveData<Team> teamLiveData = new MutableLiveData<>();


    private SessionJoinGame(){}

    public static SessionJoinGame getInstance() {
        if (instance == null) {
            instance = new SessionJoinGame();
        }
        return instance;
    }


    public MutableLiveData<Team> getTeamLiveData() {
        return teamLiveData;
    }
    public void setTeamLiveData(Team teamLiveData) {
        this.teamLiveData.setValue(teamLiveData);
    }
}
