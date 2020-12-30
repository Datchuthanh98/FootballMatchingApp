package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.State;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.data.repository.RequestJoinTeamRepository;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Evaluate;
import com.example.myclub.model.Match;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;
import com.example.myclub.session.SessionUser;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterLisEvaluateVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerHorizontal;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerVertical;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileOtherTeamViewModel extends ViewModel {
    private RequestJoinTeamRepository requestJoinTeamRepository = RequestJoinTeamRepository.getInstance();
    private MutableLiveData<Boolean> stateRequestJoinTeam = new MutableLiveData<>(false);
    private MutableLiveData<Team> matchMutableLiveData = new MutableLiveData<>(null);
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private PlayerRepository playerRepository = PlayerRepository.getInstance();

    private RecycleViewAdapterLisEvaluateVertical adapterListComment = new RecycleViewAdapterLisEvaluateVertical();
    private RecycleViewAdapterListPlayerHorizontal adapterListPlayer = new RecycleViewAdapterListPlayerHorizontal();
    private RecycleViewAdapterListMatchVertical adapterListMatch = new RecycleViewAdapterListMatchVertical();


    private MutableLiveData<LoadingState> matchLoadState = new MutableLiveData<>(LoadingState.INIT);


    public MutableLiveData<Boolean> getStateRequestJoinTeam() {
        return stateRequestJoinTeam;
    }

    public void addRequestJoinTeam(Map<String, Object> requestJoin) {
        requestJoinTeamRepository.addRequestJoinTeam(matchMutableLiveData.getValue().getId(), requestJoin, new CallBack<String, String>() {
            @Override
            public void onSuccess(String getKey) {
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(String e) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
            }
        });
    }

    public void cancelRequestJoinTeam() {
        requestJoinTeamRepository.cancelRequestJoinTeam(matchMutableLiveData.getValue().getId(), SessionUser.getInstance().getPlayerLiveData().getValue().getId(), new CallBack<String, String>() {
            @Override
            public void onSuccess(String sucess) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
            }

            @Override
            public void onFailure(String e) {
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }
        });
    }

    public void getStateJoinTeam() {
        requestJoinTeamRepository.getStateJoinTeam(matchMutableLiveData.getValue().getId(), SessionUser.getInstance().getPlayerLiveData().getValue().getId(), new CallBack<Boolean, String>() {
            @Override
            public void onSuccess(Boolean requestJoinTeam) {
                if (requestJoinTeam == Boolean.FALSE) {
                    stateRequestJoinTeam.setValue(false);
                } else {
                    stateRequestJoinTeam.setValue(true);
                }
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }

    public MutableLiveData<LoadingState> getMatchLoadState() {
        return matchLoadState;
    }


    public void getInformationTeam(String idMatch) {
        matchLoadState.setValue(LoadingState.INIT);
        teamRepository.loadTeam(idMatch, new CallBack<Team, String>() {
            @Override
            public void onSuccess(Team team) {
                matchMutableLiveData.setValue(team);
                matchLoadState.setValue(LoadingState.LOADED);
                getListPlayer();
                getListEvaluate();
                getListMatch();
            }

            @Override
            public void onFailure(String s) {
                matchLoadState.setValue(LoadingState.ERROR);
            }
        });
    }


    public  void getListEvaluate(){
        teamRepository.getListEvaluate(matchMutableLiveData.getValue().getId(), new CallBack<List<Evaluate>, String>() {
            @Override
            public void onSuccess(List<Evaluate> evaluates) {
                if(evaluates == null){
                    adapterListComment.setListEvaluate(new ArrayList<Evaluate>());
                }else {

                    adapterListComment.setListEvaluate(evaluates);
                    adapterListComment.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }

    public  void getListPlayer(){
        playerRepository.getListPlayer(matchMutableLiveData.getValue().getId(), new CallBack<List<Player>, String>() {
            @Override
            public void onSuccess(List<Player> players) {
                if(players == null){
                    adapterListPlayer.setListPlayer(new ArrayList<Player>());
                }else {

                    adapterListPlayer.setListPlayer(players);
                    adapterListComment.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String s) {

            }
        });
    }


    public  void getListMatch(){
        matchRepository.loadListMatchByTeam(matchMutableLiveData.getValue().getId(), new CallBack<List<Match>, String>() {
            @Override
            public void onSuccess(List<Match> matches) {
                if(matches == null){
                    adapterListMatch.setListMatch(new ArrayList<Match>());
                }else {
                    adapterListMatch.setListMatch(matches);
                    adapterListMatch.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }


    public RecycleViewAdapterLisEvaluateVertical getAdapterListEvaluate() {
        return adapterListComment;
    }


    public RecycleViewAdapterListPlayerHorizontal getAdapterListPlayer() {
        return adapterListPlayer;
    }

    public RecycleViewAdapterListMatchVertical getAdapterListMatch() {
        return adapterListMatch;
    }



}
