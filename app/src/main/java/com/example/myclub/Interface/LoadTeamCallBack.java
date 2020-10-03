package com.example.myclub.Interface;


import com.example.myclub.model.Team;

public interface LoadTeamCallBack {
    void onSuccess(Team team);

    void onFailure(String message);
}
