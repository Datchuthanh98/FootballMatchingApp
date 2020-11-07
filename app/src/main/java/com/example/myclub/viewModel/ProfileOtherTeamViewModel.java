package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.State;
import com.example.myclub.data.repository.RequestJoinTeamRepository;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Evaluate;
import com.example.myclub.model.RequestJoinTeam;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterLisEvaluateVertical;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileOtherTeamViewModel extends ViewModel {
    private RequestJoinTeamRepository requestJoinTeamRepository = RequestJoinTeamRepository.getInstance();
    private MutableLiveData<Boolean> stateRequestJoinTeam = new MutableLiveData<>(false);
    private String key;

    private MutableLiveData<Team> matchMutableLiveData = new MutableLiveData<>(null);
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MutableLiveData<List<Evaluate>> listCommentLiveData = new MutableLiveData<>(null);
    private RecycleViewAdapterLisEvaluateVertical adapterListComment = new RecycleViewAdapterLisEvaluateVertical();
    private MutableLiveData<LoadingState> matchLoadState = new MutableLiveData<>(LoadingState.INIT);
    private MutableLiveData<State> teamAwayStatus = new MutableLiveData<>();
    private String resultMessage = null;

    public String getKey() {
        return key;
    }




    public MutableLiveData<Boolean> getStateRequestJoinTeam() {
        return stateRequestJoinTeam;
    }

    public void addRequestJoinTeam(Map<String, Object> requestJoin) {
        requestJoinTeamRepository.addRequestJoinTeam(requestJoin, new CallBack<String, String>() {
            @Override
            public void onSuccess(String getKey) {
                key = getKey;
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }

            @Override
            public void onFailure(String e) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
            }
        });
    }

    public void cancelRequestJoinTeam(String key) {
        requestJoinTeamRepository.cancelRequestJoinTeam(key, new CallBack<String, String>() {
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

    public void getStateJoinTeam(final Map<String, Object> requestJoin) {
        requestJoinTeamRepository.getStateJoinTeam(requestJoin, new CallBack<RequestJoinTeam, String>() {
            @Override
            public void onSuccess(RequestJoinTeam requestJoinTeam) {
                if (requestJoinTeam == null) {
                    stateRequestJoinTeam.setValue(false);
                } else {
                    stateRequestJoinTeam.setValue(true);
                    key = requestJoinTeam.getId();
                }
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }


    public MutableLiveData<Team> getMatchMutableLiveData() {
        return matchMutableLiveData;
    }



    public RecycleViewAdapterLisEvaluateVertical getAdapterListComment() {
        return adapterListComment;
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
                getListComment();
            }

            @Override
            public void onFailure(String s) {
                matchLoadState.setValue(LoadingState.ERROR);
            }
        });
    }


    public  void getListComment(){
        teamRepository.getListEvaluate(matchMutableLiveData.getValue().getId(), new CallBack<List<Evaluate>, String>() {
            @Override
            public void onSuccess(List<Evaluate> evaluates) {
                if(evaluates == null){
                    adapterListComment.setListEvaluate(new ArrayList<Evaluate>());
                    listCommentLiveData.setValue(new ArrayList<Evaluate>());
                }else {
                    listCommentLiveData.setValue(evaluates);
                    adapterListComment.setListEvaluate(evaluates);
                    adapterListComment.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }

    public  void addComment(Map<String,Object> map){
        teamRepository.addEvaluate(map, new CallBack<String, String>() {
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
