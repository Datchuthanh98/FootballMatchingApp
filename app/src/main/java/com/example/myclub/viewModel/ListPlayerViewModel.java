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
    private RecycleViewAdapterListPlayerRequestVertical adapterListPlayerRequest = new RecycleViewAdapterListPlayerRequestVertical();
    private MutableLiveData<List<Player>> listPlayerLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Player>> listPlayerRequestLiveData = new MutableLiveData<>();
    private String resultMessage = null;


    public MutableLiveData<List<Player>> getListPlayerLiveData() {
        return listPlayerLiveData;
    }

    public void setListPlayerLiveData(List<Player> listPlayers) {
        Log.d("json", "setListPlayerLiveData: "+listPlayers.size());
        this.listPlayerLiveData.setValue(listPlayers);
        if (listPlayers == null) {
            adapterListPlayer.setListPlayer(new ArrayList<Player>());
            adapterListPlayerRequest.notifyDataSetChanged();
        } else {
            listPlayerLiveData.setValue(listPlayers);
            adapterListPlayer.setListPlayer(listPlayers);
            adapterListPlayer.notifyDataSetChanged();
        }
    }

    public static ListPlayerViewModel getInstance() {
        if (instance == null) {
            instance = new ListPlayerViewModel();
        }
        return instance;
    }

    public void getListPlayerRequest(String idTeam) {
        playerRepository.getListPlayerRequest(idTeam, new CallBack<List<Player>, String>() {
            @Override
            public void onSuccess(List<Player> listPlayers) {
                if (listPlayers == null) {
                    adapterListPlayerRequest.setListPlayer(new ArrayList<Player>());
                    adapterListPlayerRequest.notifyDataSetChanged();
                } else {
                    listPlayerRequestLiveData.setValue(listPlayers);
                    adapterListPlayerRequest.setListPlayer(listPlayers);
                    adapterListPlayerRequest.notifyDataSetChanged();
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

    public RecycleViewAdapterListPlayerRequestVertical getAdapterListPlayerRequest() {
        return adapterListPlayerRequest;
    }

    public String getResultMessage() {
        return resultMessage;
    }


}
