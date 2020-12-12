package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListMatchViewModel extends ViewModel {
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListMatchVertical adapterListMatchVertical = new RecycleViewAdapterListMatchVertical();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    private MutableLiveData<Status> statusData = new MutableLiveData<>();

    public MutableLiveData<Status> getStatusData() {
        return statusData;
    }

    public void setStatusData(Status statusData) {
        this.statusData.setValue(statusData);
    }

    public ListMatchViewModel() {
        getListMatchByDate(getDateNow());
    }

    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public void getListMatchByDate(String date) {
        teamLoadState.setValue(LoadingState.INIT);
        matchRepository.getListMatchByDate(date,new CallBack<List<Match>, String>() {
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

    public String getDateNow(){
        Date date = new Date();
        SimpleDateFormat dateFor = new SimpleDateFormat("dd/MM/yyyy");
        return dateFor.format(date);
    };



}
