package com.example.myclub.Interface;

import com.example.myclub.model.Player;

import java.util.List;

public interface PlayerConnectFirebase {
    void onPlayerLoadedFromFireBase(List<Player> players);
}
