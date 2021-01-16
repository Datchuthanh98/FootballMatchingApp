package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.data.repository.RequestJoinTeamRepository;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Player;
import com.example.myclub.session.SessionTeam;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerRequestVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerVertical;

import java.util.ArrayList;
import java.util.List;

public class ListPlayerRequestViewModel extends ViewModel {
    private PlayerRepository playerRepository = PlayerRepository.getInstance();
    private RequestJoinTeamRepository teamRepository = RequestJoinTeamRepository.getInstance();
    private static ListPlayerRequestViewModel instance;
    private RecycleViewAdapterListPlayerRequestVertical adapterListPlayer = new RecycleViewAdapterListPlayerRequestVertical();
    private MutableLiveData<List<Player>> listPlayerLiveData = new MutableLiveData<>();
    private String resultMessage = null;
    private String idTeam;

    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    private MutableLiveData<Status> statusData = new MutableLiveData<>();


    public MutableLiveData<Status> getStatusData() {
        return statusData;
    }

    public void setStatusData(Status statusData) {
        this.statusData.setValue(statusData);
    }


    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }


    public MutableLiveData<List<Player>> getListPlayerLiveData() {
        return listPlayerLiveData;
    }





    public void getListPlayer(String idTeam) {
        teamLoadState.setValue(LoadingState.INIT);
        playerRepository.getListPlayerRequest(idTeam, new CallBack<List<Player>, String>() {
            @Override
            public void onSuccess(List<Player> listPlayers) {
                teamLoadState.setValue(LoadingState.LOADED);
                if (listPlayers == null) {
                    adapterListPlayer.setListPlayer(new ArrayList<Player>());
                    adapterListPlayer.notifyDataSetChanged();
                    statusData.setValue(Status.NO_DATA);
                } else {
                    listPlayerLiveData.setValue(listPlayers);
                    adapterListPlayer.setListPlayer(listPlayers);
                    adapterListPlayer.notifyDataSetChanged();
                    statusData.setValue(Status.EXIST_DATA);
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
            }
        });
    }

    public void acceptJointTeam(String idPlayer){
        teamRepository.acceptJoinTeam(idTeam, idPlayer, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                getListPlayer(idTeam);
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }

    public void declineTeam(String idPlayer){
        teamRepository.declineJoinTeam(idTeam, idPlayer, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                getListPlayer(idTeam);
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }

    public RecycleViewAdapterListPlayerRequestVertical getAdapterListPlayer() {
        return adapterListPlayer;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setTeam (String idTeam){
        this.idTeam = idTeam;
    }

}
