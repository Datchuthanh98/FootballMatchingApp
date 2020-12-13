package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListTeamOtherViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();

    private RecycleViewAdapterListTeamVertical adapterListOtherTeam = new RecycleViewAdapterListTeamVertical();

    private MutableLiveData<Result> result = new MutableLiveData<>();
    private String resultMessage = null;

    private List<Team> listField = new ArrayList<>();

    public void  searchField(String nameField){
        List<Team> listSearch = new ArrayList<>();
        for(int i = 0 ;i < listField.size(); i++) {
            if(listField.get(i).getName().contains(nameField)){
                listSearch.add(listField.get(i));
            }
        }
        adapterListOtherTeam.setListTeam(listSearch);
        adapterListOtherTeam.notifyDataSetChanged();

    }


    public void getListOtherTeam(String idPlayer){
        teamRepository.getListOtherTeam(idPlayer,new CallBack<List<Team>, String>() {
            @Override
            public void onSuccess(List<Team> listTeams) {
                if(listTeams == null){
                    listField = new ArrayList<>();
                    adapterListOtherTeam.setListTeam(new ArrayList<Team>());
                    adapterListOtherTeam.notifyDataSetChanged();
                }else {
                    listField = listTeams;
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


    public RecycleViewAdapterListTeamVertical getAdapterListOtherTeam() {
        return adapterListOtherTeam;
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
