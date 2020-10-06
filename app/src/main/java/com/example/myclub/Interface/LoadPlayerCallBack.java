package com.example.myclub.Interface;


import com.example.myclub.model.Player;
import com.example.myclub.model.Team;

public interface LoadPlayerCallBack {
    void onSuccess(Player player);

    void onFailure(String message);
}
