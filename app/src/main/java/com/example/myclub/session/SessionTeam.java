package com.example.myclub.session;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.Interface.TeamChangeCallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Chat;
import com.example.myclub.model.Evaluate;
import com.example.myclub.model.Match;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterLisEvaluateVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerVertical;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class SessionTeam implements TeamChangeCallBack {
    private static SessionTeam instance;
    private Application application;
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MutableLiveData<Team> teamLiveData = new MutableLiveData<>();
    private MutableLiveData<File> teamAvatarLiveData= new MutableLiveData<>();
    private MutableLiveData<File> teamCoverLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private MutableLiveData<Result> resultPhotoLiveData = new MutableLiveData<>(null);
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    private RecycleViewAdapterLisEvaluateVertical adapterListComment = new RecycleViewAdapterLisEvaluateVertical();
    private RecycleViewAdapterListPlayerVertical adapterListPlayer = new RecycleViewAdapterListPlayerVertical();
    private RecycleViewAdapterListMatchVertical adapterListMatch = new RecycleViewAdapterListMatchVertical();
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private PlayerRepository playerRepository = PlayerRepository.getInstance();
    private String resultMessage = null;


    public static SessionTeam getInstance() {
        if (instance == null) {
            instance = new SessionTeam();
        }
        return instance;
    }

    public Context getApplicationContext(){
        return application.getApplicationContext();
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LiveData<Team> getTeamLiveData() {
        return teamLiveData;
    }

    public void setTeamLiveData(Team team){
        teamLiveData.setValue(team);
    }

    public LiveData<File>  getAvatarLiveData() {return  teamAvatarLiveData ;};

    public LiveData<File>  getCoverLiveData() {return  teamCoverLiveData ;};

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }


    public LiveData<Result> getResultPhotoLiveData() {
        return resultPhotoLiveData;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public LiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }


    @Override
    public void onTeamChange(Team team) {

        teamLiveData.setValue(team);
        getListMatch();
        getListPlayer();
        getListEvaluate();
        teamLoadState.setValue(LoadingState.LOADED);
        if (team != null) {
            if (team.getUrlAvatar() != null) {
                String[] files = team.getUrlAvatar().split("/");
                String fileName = files[files.length-1];
                File photo = new File(getApplicationContext().getCacheDir(), fileName);
                // 24 hours
                if (photo.exists() && photo.lastModified() < Calendar.getInstance().getTimeInMillis() - 86400000){
                    teamAvatarLiveData.setValue(photo);
                    resultPhotoLiveData.setValue(Result.SUCCESS);
                } else {
                    teamRepository.getAvatarPhoto(new CallBack<File, String>() {
                        @Override
                        public void onSuccess(File file) {
                            teamAvatarLiveData.setValue(file);
                            resultPhotoLiveData.setValue(Result.SUCCESS);
                        }

                        @Override
                        public void onFailure(String s) {

                        }

                    }, team.getUrlAvatar(), getApplicationContext());
                }
            }

            if (team.getUrlCover() != null) {
                String[] files = team.getUrlCover().split("/");
                String fileName = files[files.length-1];
                File photo = new File(getApplicationContext().getCacheDir(), fileName);
                // 24 hours
                if (photo.exists() && photo.lastModified() < Calendar.getInstance().getTimeInMillis() - 86400000){
                    teamCoverLiveData.setValue(photo);
                    resultPhotoLiveData.setValue(Result.SUCCESS);
                } else {
                    teamRepository.getCoverPhoto(new CallBack<File, String>() {
                        @Override
                        public void onSuccess(File file) {
                            teamCoverLiveData.setValue(file);
                            resultPhotoLiveData.setValue(Result.SUCCESS);
                        }

                        @Override
                        public void onFailure(String s) {

                        }
                    }, team.getUrlCover(), getApplicationContext());
                }
            }
        }
    }


    public void loadTeam(String id){
        teamLoadState.setValue(LoadingState.LOADING);
        teamAvatarLiveData.setValue(null);
        teamCoverLiveData.setValue(null);
        resultPhotoLiveData.setValue(null);
        teamLiveData.setValue(null);
        teamRepository.loadTeam(id, new CallBack<Team, String>() {
            @Override
            public void onSuccess(Team team) {
                getInstance().onTeamChange(team);
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    public void updateProfile(Map<String, Object> updateBasic) {
        teamRepository.updateProfile(updateBasic, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                resultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public  void updateImage(Uri uri, final String path , final boolean isAvatar){
        teamRepository.updateImage(uri, path, isAvatar, new CallBack<String,String>() {
            @Override
            public void onSuccess(String url) {
                resultPhotoLiveData.setValue(Result.SUCCESS);
                Team team = getInstance().teamLiveData.getValue();
                if (isAvatar){
                    team.setUrlAvatar(url);
                } else {
                    team.setUrlCover(url);
                }
                getInstance().onTeamChange(team);
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultPhotoLiveData.setValue(Result.FAILURE);
            }
        });
    }


    public void resetResult() {
        getInstance().resultLiveData.setValue(null);
    }

    public  void getListEvaluate(){
        teamRepository.getListEvaluate(teamLiveData.getValue().getId(), new CallBack<List<Evaluate>, String>() {
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
        playerRepository.getListPlayer(teamLiveData.getValue().getId(), new CallBack<List<Player>, String>() {
            @Override
            public void onSuccess(List<Player> players) {

                if(players == null){
                    Log.d("player", "onSuccess: 1");
                    adapterListPlayer.setListPlayer(new ArrayList<Player>());
                }else {
                    Log.d("player", "onSuccess: 2");
                    adapterListPlayer.setListPlayer(players);
                    adapterListPlayer.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String s) {

            }
        });
    }


    public  void getListMatch(){
        matchRepository.loadListMatchByTeam(teamLiveData.getValue().getId(), new CallBack<List<Match>, String>() {
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

    public RecycleViewAdapterListPlayerVertical getAdapterListPlayer() {
        return adapterListPlayer;
    }

    public RecycleViewAdapterListMatchVertical getAdapterListMatch() {
        return adapterListMatch;
    }

    public RecycleViewAdapterLisEvaluateVertical getAdapterListEvaluate() {
        return adapterListComment;
    }

}





