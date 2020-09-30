package com.example.myclub.Interface;


import com.example.myclub.model.Player;

public interface LoginCallBack {
    void onSuccess(Player player);

    void onFailure(String message);
}
