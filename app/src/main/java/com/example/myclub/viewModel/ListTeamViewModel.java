package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.DataState;
import com.example.myclub.session.SessionStateData;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;

public class ListTeamViewModel extends ViewModel {

    private TeamRepository teamRepository = TeamRepository.getInstance();
    private RecycleViewAdapterListTeamVertical adapterListTeam = new RecycleViewAdapterListTeamVertical();
    private RecycleViewAdapterListTeamVertical adapterListOtherTeam = new RecycleViewAdapterListTeamVertical();
    private MutableLiveData<List<Team>> listTeamLiveData = new MutableLiveData<>();
    private String resultMessage = null;


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
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
            }


        });
    }

    public void getListOtherTeam(String idPlayer){
        teamRepository.getListOtherTeam(idPlayer,new CallBack<List<Team>, String>() {
            @Override
            public void onSuccess(List<Team> listTeams) {
                if(listTeams == null){
                    adapterListOtherTeam.setListTeam(new ArrayList<Team>());
                    adapterListOtherTeam.notifyDataSetChanged();
                }else {
                    adapterListOtherTeam.setListTeam(listTeams);
                    adapterListOtherTeam.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
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
                SessionStateData.getInstance().setDatalistMyTeam(DataState.NEW);
            }

            @Override
            public void onFailure(String message) {
                SessionStateData.getInstance().setDatalistMyTeam(DataState.NOW);
            }
        });
    }


    public String getResultMessage() {
        return resultMessage;
    }



}
