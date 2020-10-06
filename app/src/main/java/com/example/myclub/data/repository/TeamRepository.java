package com.example.myclub.data.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.GetTeamPhotoCallBack;
import com.example.myclub.Interface.GetUserCoverCallBack;
import com.example.myclub.Interface.GetUserPhotoCallBack;
import com.example.myclub.Interface.LoadListOtherTeamCallBack;
import com.example.myclub.Interface.LoadListTeamCallBack;
import com.example.myclub.Interface.LoadTeamCallBack;
import com.example.myclub.Interface.RegisterTeamCallBack;
import com.example.myclub.Interface.UpdateImageCallBack;
import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.data.datasource.PlayerDataSource;
import com.example.myclub.data.datasource.TeamDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
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


    public void loadTeam(String id , LoadTeamCallBack loadTeamCallBack){
        teamDataSource.loadTeam(id,loadTeamCallBack);
    }

    public void updateProfile(Map<String, Object> updateInformation, UpdateProfileCallBack callBack) {
        teamDataSource.updateProfile(updateInformation, callBack);
    }

    public void updateImage(Uri uri, String path ,boolean isAvatar, final UpdateImageCallBack callBack){
        teamDataSource.updateImage(uri,path,isAvatar,callBack);
    }


    public void getAvatarPhoto(final GetTeamPhotoCallBack callBack, String url, Context context) {
        if (url.isEmpty()) {
            callBack.onGetTeamPhotoCallBack(null);
            return;
        }
        String[] files = url.split("/");
        String fileName = files[files.length-1];
        final File cachePhoto = new File(context.getCacheDir(), fileName);
        FileDownloadTask downloadTask = teamDataSource.getUserPhoto(url, cachePhoto);
        downloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                callBack.onGetTeamPhotoCallBack(cachePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onGetTeamPhotoCallBack(null);
            }
        });
    }


    public void getCoverPhoto(final GetTeamPhotoCallBack callBack, String url, Context context) {
        if (url.isEmpty()) {
            callBack.onGetTeamPhotoCallBack(null);
            return;
        }
        String[] files = url.split("/");
        String fileName = files[files.length-1];
        final File cachePhoto = new File(context.getCacheDir(), fileName);
        FileDownloadTask downloadTask = teamDataSource.getUserPhoto(url, cachePhoto);
        downloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                callBack.onGetTeamPhotoCallBack(cachePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onGetTeamPhotoCallBack(null);
            }
        });
    }


    public void creatTeam(String name, String phone, String email, RegisterTeamCallBack checkteam) {
        teamDataSource.createTeam(name,phone,email,checkteam);
    }

    public void getListTeam(String id,LoadListTeamCallBack listTeamCallBack){
        teamDataSource.loadListTeam(id,listTeamCallBack);
    }

    public void getListOtherTeam(String id,LoadListOtherTeamCallBack listTeamCallBack){
        teamDataSource.loadListOtherTeam(id,listTeamCallBack);
    }
}
