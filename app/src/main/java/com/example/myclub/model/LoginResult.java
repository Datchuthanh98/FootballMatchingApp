package com.example.myclub.model;


import com.example.myclub.data.enumeration.Result;

public class LoginResult {
    int loginRequestCode;
    private Result result;
    private Player player;

    public LoginResult(int loginRequestCode, Result result, Player player) {
        this.loginRequestCode = loginRequestCode;
        this.result = result;
        this.player = player;
    }

    public int getLoginRequestCode() {
        return loginRequestCode;
    }

    public Result getResult() {
        return result;
    }

    public Player getPlayer() {
        return player;
    }
}
