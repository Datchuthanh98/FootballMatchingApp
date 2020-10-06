package com.example.myclub.Interface;


import com.example.myclub.model.Player;
import com.example.myclub.model.Team;

import java.util.List;

public interface LoadListPlayerCallBack {
    void onSuccess(List<Player> listPlayers);

    void onFailure(String message);
}
