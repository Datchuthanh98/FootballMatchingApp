package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.model.Player;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerRequestVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerVertical;

import java.util.ArrayList;
import java.util.List;

public class ListPlayerViewModel extends ViewModel {
    private PlayerRepository playerRepository = PlayerRepository.getInstance();
    private static ListPlayerViewModel instance;
    private RecycleViewAdapterListPlayerVertical adapterListPlayer = new RecycleViewAdapterListPlayerVertical();
    private MutableLiveData<List<Player>> listPlayerLiveData = new MutableLiveData<>();
    private String resultMessage = null;


    public MutableLiveData<List<Player>> getListPlayerLiveData() {
        return listPlayerLiveData;
    }



    public static ListPlayerViewModel getInstance() {
        if (instance == null) {
            instance = new ListPlayerViewModel();
        }
        return instance;
    }

    public void getListPlayer(String idTeam) {
        playerRepository.getListPlayer(idTeam, new CallBack<List<Player>, String>() {
            @Override
            public void onSuccess(List<Player> listPlayers) {
                if (listPlayers == null) {
                    adapterListPlayer.setListPlayer(new ArrayList<Player>());
                    adapterListPlayer.notifyDataSetChanged();
                } else {
                    listPlayerLiveData.setValue(listPlayers);
                    adapterListPlayer.setListPlayer(listPlayers);
                    adapterListPlayer.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
            }
        });
    }

    public RecycleViewAdapterListPlayerVertical getAdapterListPlayer() {
        return adapterListPlayer;
    }

    public String getResultMessage() {
        return resultMessage;
    }


}
