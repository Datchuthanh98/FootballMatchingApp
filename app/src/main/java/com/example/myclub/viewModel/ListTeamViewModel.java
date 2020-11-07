package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListTeamViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private RecycleViewAdapterListTeamVertical adapterListTeam = new RecycleViewAdapterListTeamVertical();
    private RecycleViewAdapterListTeamVertical adapterListOtherTeam = new RecycleViewAdapterListTeamVertical();
    private MutableLiveData<List<Team>> listTeamLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> result = new MutableLiveData<>();
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

    public void createTeam(Map<String,Object> map){
        teamRepository.creatTeam(map, new CallBack<Team, String>() {
            @Override
            public void onSuccess(Team team) {
                Log.d("meme", "onSuccess: 123 ");
                result.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                result.setValue(Result.FAILURE);
            }
        });
    }


    public String getResultMessage() {
        return resultMessage;
    }

    public MutableLiveData<Result> getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result.setValue(result);
    }
}
