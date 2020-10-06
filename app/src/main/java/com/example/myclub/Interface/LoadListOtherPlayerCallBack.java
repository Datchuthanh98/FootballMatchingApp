package com.example.myclub.Interface;


import com.example.myclub.model.Player;

import java.util.List;

public interface LoadListOtherPlayerCallBack {
    void onSuccess(List<Player> listPlayers);

    void onFailure(String message);
}
