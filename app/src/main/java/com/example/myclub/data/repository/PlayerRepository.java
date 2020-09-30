package com.example.myclub.data.repository;

import android.net.Uri;

import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.data.firestore.PlayerDataSource;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;


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

    public void updateProfile(Map<String, Object> updateBasicInformation, UpdateProfileCallBack callBack) {
        userDataSource.updateProfile(updateBasicInformation, callBack);
    }

    public void updateImage(Uri uri, String path , final UpdateProfileCallBack callBack){
        userDataSource.updateImage(uri,path,callBack);
    }









//    public void login(String email, String password, int loginRequestCode, LoginCallBack callBack) {
//        userDataSource.login(email, password, loginRequestCode, callBack);
//    }
//
//    public void register(String email, String password, String fullName, RegisterCallBack callBack) {
//        userDataSource.register(email, password, fullName, callBack);
//    }
//
//    public void logout() {
//        userDataSource.logout();
//    }
//
//    public boolean isLoggedIn() {
//        return userDataSource.isLoggedIn();
//    }


//    public void loginWithCurrentAuthAccount(int loginRequestCode, LoginCallBack loginCallBack) {
//        userDataSource.loginWithCurrentAuthAccount(loginRequestCode, loginCallBack);
//    }
//
//    public void likeTeam(String playerId, String destinationTeamId, LikeTeamCallBack callBack) {
//        userDataSource.likeTeam(playerId, destinationTeamId, callBack);
//    }
//
//    public void queryAllPlayers(String myId, String myTeamId, QueryPlayersCallBack callBack) {
//        userDataSource.queryAllPlayers(myId, myTeamId, callBack);
//    }
//
//    public void getUser(String uid, GetUserCallBack callBack){
//        userDataSource.getUser(uid, callBack);
//    }
//
//    public void updateLastUpdateNotification(String uid) {
//        userDataSource.updateLastUpdateNotification(uid);
//    }
//
//    public ListenerRegistration listenToPlayerLikedByTeams(String uid, EventListener<QuerySnapshot> eventListener) {
//        return userDataSource.listenToPlayerLikedByTeams(uid, eventListener);
//    }
}
