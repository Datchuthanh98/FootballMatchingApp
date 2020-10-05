package com.example.myclub.Interface;


import com.example.myclub.model.Team;

import java.util.List;

public interface LoadListOtherTeamCallBack {
    void onSuccess(List<Team> listTeams);

    void onFailure(String message);
}
