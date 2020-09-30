package com.example.myclub.data.session;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myclub.data.firestore.PlayerDataSource;
import com.example.myclub.model.Player;

public class Session {
    private final String TAG = "Session";
    private static Session instance;
    private MutableLiveData<Player> playerSession = new MutableLiveData<>();

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public LiveData<Player> getPlayerLiveData() {
        return playerSession;
    }

    public void setPlayerLiveData(Player player){
           playerSession.setValue(player);
    }
}
