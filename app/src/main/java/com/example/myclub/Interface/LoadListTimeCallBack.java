package com.example.myclub.Interface;


import com.example.myclub.model.Team;
import com.example.myclub.model.TimeGame;

import java.util.List;

public interface LoadListTimeCallBack {
    void onSuccess(List<TimeGame> listTimeGames);

    void onFailure(String message);
}
