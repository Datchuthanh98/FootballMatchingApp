package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.State;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Match;
import com.example.myclub.model.Team;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListQueueTeamVertical;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListCommentVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileMatchViewModel extends ViewModel {
    private MutableLiveData<Match> matchMutableLiveData = new MutableLiveData<>(null);
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private MutableLiveData<List<Comment>> listCommentLiveData = new MutableLiveData<>(null);
    private RecycleViewAdapterListCommentVertical adapterListComment = new RecycleViewAdapterListCommentVertical();
    private MutableLiveData<List<Team>> listQueueTeamLiveData = new MutableLiveData<>(null);
    private RecycleViewAdapterListQueueTeamVertical adapterListQueueTeam = new RecycleViewAdapterListQueueTeamVertical();
    private MutableLiveData<LoadingState> matchLoadState = new MutableLiveData<>(LoadingState.INIT);
    private  MutableLiveData<State> teamAwayStatus = new MutableLiveData<>();


    public MutableLiveData<State> getTeamAwayStatus() {
        return teamAwayStatus;
    }

    public void setTeamAwayStatus(MutableLiveData<State> teamAwayStatus) {
        this.teamAwayStatus = teamAwayStatus;
    }

    private String idMatch;

    public String teamLoadMessage;

    public MutableLiveData<Match> getMatchMutableLiveData() {
        return matchMutableLiveData;
    }

    public MutableLiveData<LoadingState> getMatchLoadState() {
        return matchLoadState;
    }


    public RecycleViewAdapterListQueueTeamVertical getAdapterListQueueTeam() {
        return adapterListQueueTeam;
    }

    public RecycleViewAdapterListCommentVertical getAdapterListComment() {
        return adapterListComment;
    }


    public  void getInformationMatch(String idMatch){
        this.idMatch = idMatch;
        matchLoadState.setValue(LoadingState.INIT);
        matchRepository.getInformationMatch(idMatch, new CallBack<Match, String>() {
            @Override
            public void onSuccess(Match match) {
                matchMutableLiveData.setValue(match);
                matchLoadState.setValue(LoadingState.LOADED);

                if(match.getIdBooking().getIdTeamAway() !=null){
                    teamAwayStatus.setValue(State.DONE);
                }else {
                    getListQueueTeam();
                    teamAwayStatus.setValue(State.WAIT);
                }

                getListComment();
            }

            @Override
            public void onFailure(String message) {
                matchLoadState.setValue(LoadingState.ERROR);
                teamLoadMessage = message;
            }
        });
    }

    public void acceptTeam( Map<String, Object> map) {
        matchRepository.acceptTeam(matchMutableLiveData.getValue().getIdBooking().getId(),map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                getInformationMatch(idMatch);
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }


    public  void addQueueTeam(Map<String,Object> map){
        matchRepository.addQueueTeam(map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                getListQueueTeam();
            }

            @Override
            public void onFailure(String s){

            }
        });
    }


    public  void getListQueueTeam(){
        matchRepository.getlistQueueTeam(matchMutableLiveData.getValue().getId(), new CallBack<List<Team>, String>() {
            @Override
            public void onSuccess(List<Team> lists) {
                if(lists == null){
                    adapterListQueueTeam.setListTeam(new ArrayList<Team>());
                    listQueueTeamLiveData.setValue(new ArrayList<Team>());
                    adapterListQueueTeam.notifyDataSetChanged();
                }else {
                    listQueueTeamLiveData.setValue(lists);
                    adapterListQueueTeam.setListTeam(lists);
                    adapterListQueueTeam.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String s) {
                teamLoadMessage = s ;
            }
        });
    }


    public  void getListComment(){
        matchRepository.getlistComment(matchMutableLiveData.getValue().getId(), new CallBack<List<Comment>, String>() {
            @Override
            public void onSuccess(List<Comment> listComments) {
                if(listComments == null){
                    adapterListComment.setListComment(new ArrayList<Comment>());
                    listCommentLiveData.setValue(new ArrayList<Comment>());
                }else {
                    listCommentLiveData.setValue(listComments);
                    adapterListComment.setListComment(listComments);
                    adapterListComment.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String s) {
                teamLoadMessage = s ;
            }
        });
    }

    public  void addComment(Map<String,Object> map){
        matchRepository.addComment(map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                getListComment();
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }



}
