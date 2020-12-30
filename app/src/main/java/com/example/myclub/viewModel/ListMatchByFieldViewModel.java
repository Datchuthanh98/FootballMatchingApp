package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;

import java.util.ArrayList;
import java.util.List;

public class ListMatchByFieldViewModel extends ViewModel {
    private FieldRepository matchRepository = FieldRepository.getInstance();
    private RecycleViewAdapterListMatchVertical adapterListMatchVertical = new RecycleViewAdapterListMatchVertical();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    private MutableLiveData<Status> statusData = new MutableLiveData<>();

    public MutableLiveData<Status> getStatusData() {
        return statusData;
    }

    public void setStatusData(Status statusData) {
        this.statusData.setValue(statusData);
    }

    public ListMatchByFieldViewModel() {

    }

    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public void getListMatchByTeam(String idTeam) {
        teamLoadState.setValue(LoadingState.LOADING);
        matchRepository.getListMatch(idTeam,new CallBack<List<Match>, String>() {
            @Override
            public void onSuccess(List<Match> matchList) {
                teamLoadState.setValue(LoadingState.LOADED);
                if (matchList == null) {
                    adapterListMatchVertical.setListMatch(new ArrayList<Match>());
                    adapterListMatchVertical.notifyDataSetChanged();
                    statusData.setValue(Status.NO_DATA);
                } else {
                    adapterListMatchVertical.setListMatch(matchList);
                    adapterListMatchVertical.notifyDataSetChanged();
                    statusData.setValue(Status.EXIST_DATA);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public RecycleViewAdapterListMatchVertical getAdapterListMatch() {
        return adapterListMatchVertical;
    }





}
