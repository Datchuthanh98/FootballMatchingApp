package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.model.Player;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerRequestVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerVertical;

import java.util.ArrayList;
import java.util.List;

public class ListPlayerRequestViewModel extends ViewModel {
    private PlayerRepository playerRepository = PlayerRepository.getInstance();
    private static ListPlayerRequestViewModel instance;
    private RecycleViewAdapterListPlayerRequestVertical adapterListPlayer = new RecycleViewAdapterListPlayerRequestVertical();
    private MutableLiveData<List<Player>> listPlayerLiveData = new MutableLiveData<>();
    private String resultMessage = null;


    public MutableLiveData<List<Player>> getListPlayerLiveData() {
        return listPlayerLiveData;
    }



    public static ListPlayerRequestViewModel getInstance() {
        if (instance == null) {
            instance = new ListPlayerRequestViewModel();
        }
        return instance;
    }

    public void getListPlayer(String idTeam) {
        playerRepository.getListPlayerRequest(idTeam, new CallBack<List<Player>, String>() {
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

    public RecycleViewAdapterListPlayerRequestVertical getAdapterListPlayer() {
        return adapterListPlayer;
    }

    public String getResultMessage() {
        return resultMessage;
    }


}
