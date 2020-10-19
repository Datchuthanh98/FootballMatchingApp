package com.example.myclub.data.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.PlayerDataSource;
import com.example.myclub.model.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerRepository {
    private static PlayerRepository instance;
    private PlayerDataSource userDataSource = PlayerDataSource.getInstance();

    private PlayerRepository() {
    }

    public static PlayerRepository getInstance() {
        if (instance == null) {
            instance = new PlayerRepository();
        }
        return instance;
    }

    public void updateProfile(Map<String, Object> updateInformation, CallBack<String,String> callBack) {
        userDataSource.updateProfile(updateInformation, callBack);
    }

    public void updateImage(Uri uri, String path ,boolean isAvatar, final CallBack<String,String> callBack){
        userDataSource.updateImage(uri,path,isAvatar,callBack);
    }


    public void getUserPhoto(final CallBack<File,String> callBack, String url, Context context) {
        if (url.isEmpty()) {
            callBack.onSuccess(null);
            return;
        }
        String[] files = url.split("/");
        String fileName = files[files.length-1];
        final File cachePhoto = new File(context.getCacheDir(), fileName);
        FileDownloadTask downloadTask = userDataSource.getUserPhoto(url, cachePhoto);
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
        FileDownloadTask downloadTask = userDataSource.getUserPhoto(url, cachePhoto);
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

    public void getListPlayer(String id, CallBack<List<Player>,String> loadListPlayerCallBack){
        userDataSource.loadListPlayer(id,loadListPlayerCallBack);
    }

    public void getListPlayerRequest(String id, CallBack<List<Player>,String> loadListPlayerCallBack){
        userDataSource.loadListPlayerRequest(id,loadListPlayerCallBack);
    }

//    public void getListOtherPlayer(String id ,CallBack<List<Player>,String> loadListOtherPlayerCallBack){
//        userDataSource.loadListOtherPlayer(id,loadListOtherPlayerCallBack);
//    }

}
