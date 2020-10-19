package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.session.SessionUser;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;

public class ListMyTeamViewModel extends ViewModel {

    private TeamRepository teamRepository = TeamRepository.getInstance();
    private SessionUser sessionUser = SessionUser.getInstance();
    private static ListMyTeamViewModel instance;
    private RecycleViewAdapterListTeamVertical adapterListTeam = new RecycleViewAdapterListTeamVertical();
    private RecycleViewAdapterListTeamVertical adapterListOtherTeam = new RecycleViewAdapterListTeamVertical();

    private MutableLiveData<List<Team>> listTeamLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;


    public static ListMyTeamViewModel getInstance() {
        if (instance == null) {
            instance = new ListMyTeamViewModel();
        }
        return instance;
    }

    public void getListTeam(String idPlayer){
        teamRepository.getListTeam(idPlayer,new CallBack<List<Team>, String>() {
            @Override
            public void onSuccess(List<Team> listTeams) {

                if(listTeams == null){
                    adapterListTeam.setListTeam(new ArrayList<Team>());
                }else {
                    listTeamLiveData.setValue(listTeams);
                    adapterListTeam.setListTeam(listTeams);
                    adapterListTeam.notifyDataSetChanged();
                    resultLiveData.setValue(Result.SUCCESS);
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }


        });
    }

    public void getListOtherTeam(String idPlayer){
        teamRepository.getListOtherTeam(idPlayer,new CallBack<List<Team>, String>() {
            @Override
            public void onSuccess(List<Team> listTeams) {
                if(listTeams == null){
                    adapterListOtherTeam.setListTeam(new ArrayList<Team>());
                }else {
                    adapterListOtherTeam.setListTeam(listTeams);
                    adapterListOtherTeam.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public RecycleViewAdapterListTeamVertical getAdapterListTeam() {
        return adapterListTeam;
    }

    public RecycleViewAdapterListTeamVertical getAdapterListOtherTeam() {
        return adapterListOtherTeam;
    }

    public void createTeam(String name , String phone , String email){
        teamRepository.creatTeam(name, phone, email, new CallBack<Team, String>() {
            @Override
            public void onSuccess(Team team) {
                resultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }
    public String getResultMessage() {
        return resultMessage;
    }



}
