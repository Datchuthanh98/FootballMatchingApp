package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListTeamOtherViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private RecycleViewAdapterListTeamVertical adapterListTeam = new RecycleViewAdapterListTeamVertical();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    private MutableLiveData<Status> statusData = new MutableLiveData<>();

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
        adapterListTeam.setListTeam(listSearch);
        adapterListTeam.notifyDataSetChanged();

    }

    public void getListTeamOhter(String idPlayer){
        teamLoadState.setValue(LoadingState.INIT);
        teamRepository.getListOtherTeam(idPlayer,new CallBack<List<Team>, String>() {
            @Override
            public void onSuccess(List<Team> listTeams) {
                teamLoadState.setValue(LoadingState.LOADED);
                if(listTeams.size() == 0 || listTeams == null){
                    statusData.setValue(Status.NO_DATA);
                    listField = new ArrayList<Team>();
                    adapterListTeam.setListTeam(new ArrayList<Team>());
                }else {
                    listField = listTeams;
                    adapterListTeam.setListTeam(listTeams);
                    adapterListTeam.notifyDataSetChanged();
                    statusData.setValue(Status.EXIST_DATA);
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



    public void createTeam(Map<String,Object> map){
        teamRepository.creatTeam(map, new CallBack<Team, String>() {
            @Override
            public void onSuccess(Team team) {
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

    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public void setTeamLoadState(MutableLiveData<LoadingState> teamLoadState) {
        this.teamLoadState = teamLoadState;
    }

    public MutableLiveData<Status> getStatusData() {
        return statusData;
    }

    public void setStatusData(MutableLiveData<Status> statusData) {
        this.statusData = statusData;
    }

    public void setResult(MutableLiveData<Result> result) {
        this.result = result;
    }
}
