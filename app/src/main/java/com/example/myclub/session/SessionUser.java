package com.example.myclub.session;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.Interface.UserChangeCallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.model.Player;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

public class SessionUser implements  UserChangeCallBack {
    private static SessionUser instance;
    private Application application;
    private PlayerRepository playerRepository = PlayerRepository.getInstance();
    private MutableLiveData<Player> playerLiveData = new MutableLiveData<>();
    private MutableLiveData<File> playerAvatarLiveData= new MutableLiveData<>();
    private MutableLiveData<File> playerCoverLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private MutableLiveData<Result> resultPhotoLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;

    public static SessionUser getInstance() {
        if (instance == null) {
            instance = new SessionUser();
        }
        return instance;
    }

    public Context getApplicationContext(){
        return application.getApplicationContext();
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LiveData<Player> getPlayerLiveData() {
        return playerLiveData;
    }

    public void setPlayerLiveData(Player player){
        playerLiveData.setValue(player);
    }

    public LiveData<File>  getAvatarLiveData() {return  playerAvatarLiveData ;};

    public LiveData<File>  getCoverLiveData() {return  playerCoverLiveData ;};


    @Override
    public void onUserChange(Player player) {
        // reset registration token when login
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        playerLiveData.setValue(player);
        if (player != null) {
            if (!player.getUrlAvatar().isEmpty()) {
                String[] files = player.getUrlAvatar().split("/");
                String fileName = files[files.length-1];
                File photo = new File(getApplicationContext().getCacheDir(), fileName);
                // 24 hours
                if (photo.exists() && photo.lastModified() < Calendar.getInstance().getTimeInMillis() - 86400000){
                    playerAvatarLiveData.setValue(photo);
                } else {
                    playerRepository.getUserPhoto(new CallBack<File,String>() {
                        @Override
                        public void onSuccess(File file) {
                            playerAvatarLiveData.setValue(file);
                        }

                        @Override
                        public void onFailure(String message) {
                            resultMessage = message;
                            resultLiveData.setValue(Result.FAILURE);
                        }
                    }, player.getUrlAvatar(), getApplicationContext());
                }
            }

            if (!player.getUrlCover().isEmpty()) {
                String[] files = player.getUrlCover().split("/");
                String fileName = files[files.length-1];
                File photo = new File(getApplicationContext().getCacheDir(), fileName);
                // 24 hours
                if (photo.exists() && photo.lastModified() < Calendar.getInstance().getTimeInMillis() - 86400000){
                    playerCoverLiveData.setValue(photo);
                } else {
                    playerRepository.getCoverPhoto(new CallBack<File, String>() {
                        @Override
                        public void onSuccess(File file) {
                            playerCoverLiveData.setValue(file);
                        }

                        @Override
                        public void onFailure(String message) {
                            resultMessage = message;
                            resultLiveData.setValue(Result.FAILURE);
                        }

                    }, player.getUrlCover(), getApplicationContext());
                }
            }


        }
    }

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }
    public LiveData<Result> getResultPhotoLiveData() {
        return resultPhotoLiveData;
    }
    public String getResultMessage() {
        return resultMessage;
    }

    public void updateProfile(Map<String, Object> updateBasic) {
        playerRepository.updateProfile(updateBasic, new CallBack<String, String>() {
            @Override
            public void onSuccess(String sucess) {
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

        playerRepository.updateImage(uri, path, isAvatar, new CallBack<String, String>() {
            @Override
            public void onSuccess(String url) {
                resultPhotoLiveData.setValue(Result.SUCCESS);
                Player player = getInstance().playerLiveData.getValue();
                if (isAvatar){
                    player.setUrlAvatar(url);
                } else {
                    player.setUrlCover(url);
                }
                getInstance().onUserChange(player);
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
