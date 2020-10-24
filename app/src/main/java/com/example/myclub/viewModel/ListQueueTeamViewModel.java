package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Team;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListQueueTeamVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListQueueTeamViewModel extends ViewModel {
    private MutableLiveData<List<Team>> listQueueTeamLiveData = new MutableLiveData<>(null);
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListQueueTeamVertical adapterListQueueTeam = new RecycleViewAdapterListQueueTeamVertical();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    public String teamLoadMessage;
    private MutableLiveData<Result> resultAddQueueTeam = new MutableLiveData<>(null);




    public MutableLiveData<Result> getResultAddQueueTeam() {
        return resultAddQueueTeam;
    }

    public ListQueueTeamViewModel(List<Team> listQueueTeam) {
        listQueueTeamLiveData.setValue(listQueueTeam);
    }

    public ListQueueTeamViewModel() {
    }

    public MutableLiveData<List<Team>> getListQueueTeam() {
        return listQueueTeamLiveData;
    }


    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public RecycleViewAdapterListQueueTeamVertical getAdapterListQueueTeam() {
        return adapterListQueueTeam;
    }



    public  void getListQueueTeam(String idMatch){
        matchRepository.getlistQueueTeam(idMatch, new CallBack<List<Team>, String>() {
            @Override
            public void onSuccess(List<Team> listTeams) {
                if(listTeams == null){
                    adapterListQueueTeam.setListTeam(new ArrayList<Team>());
                }else {
                    listQueueTeamLiveData.setValue(listTeams);
                    adapterListQueueTeam.setListTeam(listTeams);
                    adapterListQueueTeam.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String s) {
                teamLoadMessage = s ;
            }
        });

    }


    public  void addQueueTeam(Map<String,Object> map){
        matchRepository.addQueueTeam(map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                resultAddQueueTeam.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String s) {
                resultAddQueueTeam.setValue(Result.FAILURE);
            }
        });
    }



}
