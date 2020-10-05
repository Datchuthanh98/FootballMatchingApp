package com.example.myclub.viewModel;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.GetTeamPhotoCallBack;
import com.example.myclub.Interface.GetUserCoverCallBack;
import com.example.myclub.Interface.GetUserPhotoCallBack;
import com.example.myclub.Interface.LoadTeamCallBack;
import com.example.myclub.Interface.TeamChangeCallBack;
import com.example.myclub.Interface.UpdateImageCallBack;
import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.Interface.UserChangeCallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

public class TeamViewModel extends ViewModel implements TeamChangeCallBack {
    private final String TAG = "Session";
    private static TeamViewModel instance;
    private Application application;
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MutableLiveData<Team> teamLiveData = new MutableLiveData<>();
    private MutableLiveData<File> teamAvatarLiveData= new MutableLiveData<>();
    private MutableLiveData<File> teamCoverLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private MutableLiveData<Result> resultPhotoLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;

    public static TeamViewModel getInstance() {
        if (instance == null) {
            instance = new TeamViewModel();
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



    @Override
    public void onTeamChange(Team team) {
        teamLiveData.setValue(team);
        if (team != null) {
            if (!team.getUrlAvatar().isEmpty()) {
                String[] files = team.getUrlAvatar().split("/");
                String fileName = files[files.length-1];
                File photo = new File(getApplicationContext().getCacheDir(), fileName);
                // 24 hours
                if (photo.exists() && photo.lastModified() < Calendar.getInstance().getTimeInMillis() - 86400000){
                    teamAvatarLiveData.setValue(photo);
                } else {
                    teamRepository.getAvatarPhoto(new GetTeamPhotoCallBack() {
                        @Override
                        public void onGetTeamPhotoCallBack(File photo) {
                            teamAvatarLiveData.setValue(photo);
                        }
                    }, team.getUrlAvatar(), getApplicationContext());
                }
            }

            if (!team.getUrlCover().isEmpty()) {
                String[] files = team.getUrlCover().split("/");
                String fileName = files[files.length-1];
                File photo = new File(getApplicationContext().getCacheDir(), fileName);
                // 24 hours
                if (photo.exists() && photo.lastModified() < Calendar.getInstance().getTimeInMillis() - 86400000){
                    teamCoverLiveData.setValue(photo);
                } else {
                    teamRepository.getCoverPhoto(new GetTeamPhotoCallBack() {
                        @Override
                        public void onGetTeamPhotoCallBack(File photo) {
                            teamCoverLiveData.setValue(photo);
                        }
                    }, team.getUrlCover(), getApplicationContext());
                }
            }
        }
    }


    public void loadTeam(String id){
        teamRepository.loadTeam(id, new LoadTeamCallBack() {
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
        teamRepository.updateProfile(updateBasic, new UpdateProfileCallBack() {
            @Override
            public void onSuccess() {
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

        teamRepository.updateImage(uri, path, isAvatar, new UpdateImageCallBack() {
            @Override
            public void onSuccess(String url) {
                Log.d("check updateUI", "onSuccess: VAO DAY R NE");
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
}
