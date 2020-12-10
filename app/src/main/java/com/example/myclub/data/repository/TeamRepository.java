package com.example.myclub.data.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.TeamDataSource;
import com.example.myclub.model.Chat;
import com.example.myclub.model.Evaluate;
import com.example.myclub.model.Field;
import com.example.myclub.model.Team;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TeamRepository {
    private static TeamRepository instance;
    private TeamDataSource teamDataSource = TeamDataSource.getInstance();

    private TeamRepository() {
    }

    public static TeamRepository getInstance() {
        if (instance == null) {
            instance = new TeamRepository();
        }
        return instance;
    }


    public void loadTeam(String id , CallBack<Team,String> loadTeamCallBack){
        teamDataSource.loadTeam(id,loadTeamCallBack);
    }

    public void updateProfile(Map<String, Object> updateInformation, CallBack<String,String> callBack) {
        teamDataSource.updateProfile(updateInformation, callBack);
    }

    public void updateImage(Uri uri, String path ,boolean isAvatar, final CallBack<String,String> callBack){
        teamDataSource.updateImage(uri,path,isAvatar,callBack);
    }


    public void getAvatarPhoto(final CallBack<File,String> callBack, String url, Context context) {
        if (url.isEmpty()) {
            callBack.onSuccess(null);
            return;
        }
        String[] files = url.split("/");
        String fileName = files[files.length-1];
        final File cachePhoto = new File(context.getCacheDir(), fileName);
        FileDownloadTask downloadTask = teamDataSource.getUserPhoto(url, cachePhoto);
        downloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                callBack.onSuccess(cachePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onSuccess(null);
            }
        });
    }


    public void getCoverPhoto(final CallBack<File,String> callBack, String url, Context context) {
        if (url.isEmpty()) {
            callBack.onSuccess(null);
            return;
        }
        String[] files = url.split("/");
        String fileName = files[files.length-1];
        final File cachePhoto = new File(context.getCacheDir(), fileName);
        FileDownloadTask downloadTask = teamDataSource.getUserPhoto(url, cachePhoto);
        downloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                callBack.onSuccess(cachePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onSuccess(null);
            }
        });
    }


    public void creatTeam(Map<String,Object> map, CallBack<Team,String> checkteam) {
        teamDataSource.createTeam(map,checkteam);
    }

    public void getListTeam(String id,CallBack<List<Team>,String> listTeamCallBack){
        teamDataSource.loadListTeam(id,listTeamCallBack);
    }

    public void getListOtherTeam(String id,CallBack<List<Team>,String> listTeamCallBack){
        teamDataSource.loadListOtherTeam(id,listTeamCallBack);
    }

    public  void loadChat(String idTeam , CallBack<List<Chat>,String> callBack){
        teamDataSource.loadChatTeam(idTeam,callBack);
    }

    public  void addChat(String idTeam, Map<String,Object> map,CallBack<String,String > callBack) {
        teamDataSource.addChat(idTeam,map,callBack);
    }

    public  void getListEvaluate(String idTeam , final CallBack<List<Evaluate>,String> loadListTeamCallBack){
        teamDataSource.getListEvaluate(idTeam,loadListTeamCallBack);
    }

    public void addEvaluate(String idTeam,Map<String,Object> map, final CallBack<String ,String>  callBack){
        teamDataSource.addEvaluate(idTeam,map,callBack);
    }


}
