package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.Match;

import java.util.Map;

public class ProfileMatchViewModel extends ViewModel {
    private MutableLiveData<Match> matchMutableLiveData = new MutableLiveData<>(null);
    private MatchRepository matchRepository = MatchRepository.getInstance();

    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    public String teamLoadMessage;

    public MutableLiveData<Match> getMatchMutableLiveData() {
        return matchMutableLiveData;
    }
    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }
    public MutableLiveData<Result> resultExecuteTeam = new MutableLiveData<>(null);

    public MutableLiveData<Result> getResultExecuteTeam() {
        return resultExecuteTeam;
    }

    public  void getInformationMatch(String idMatch){
        matchRepository.getInformationMatch(idMatch, new CallBack<Match, String>() {
            @Override
            public void onSuccess(Match match) {
                matchMutableLiveData.setValue(match);
                teamLoadState.setValue(LoadingState.LOADED);
            }

            @Override
            public void onFailure(String message) {
                teamLoadState.setValue(LoadingState.ERROR);
                teamLoadMessage = message;
            }
        });
    }

    public void acceptTeam(String idBooking, Map<String, Object> map) {
        matchRepository.acceptTeam(idBooking,map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                resultExecuteTeam.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String s) {
                resultExecuteTeam.setValue(Result.FAILURE);
            }
        });
    }
}
