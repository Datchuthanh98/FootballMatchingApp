package com.example.myclub.data.datasource;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;
import com.example.myclub.data.session.SessionUser;
import com.example.myclub.viewModel.TeamViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamDataSource {
    static TeamDataSource instance;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();
    private Gson convert = new Gson();

    public static TeamDataSource getInstance() {
        if (instance == null) {
            instance = new TeamDataSource();
        }
        return instance;
    }


    public void createTeam(final String name, final String phone, final String email, final CallBack<Team,String> callBack) {
        Map<String, Object> map = new HashMap<>();
        final String idPlayer = SessionUser.getInstance().getPlayerLiveData().getValue().getId();
        map.put("name", name);
        map.put("phone", phone);
        map.put("email", email);
        map.put("level", "basic");
        map.put("idPlayer", idPlayer);
        functions.getHttpsCallable("createTeam").call(map).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                callBack.onSuccess(null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }


    public void loadListTeam(String idPlayer, final CallBack<List<Team>,String> loadListTeamCallBack) {
         functions.getHttpsCallable("getListTeam").call(idPlayer).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
             @Override
             public void onSuccess(HttpsCallableResult httpsCallableResult) {
                 Gson gson= new Gson();
                 List<Map> listTeamMaps = (List<Map>) httpsCallableResult.getData();
                 List<Team> listTeam = new ArrayList<>();
                 if(listTeamMaps == null){
                     loadListTeamCallBack.onSuccess(new ArrayList<Team>());
                 }else{
                     for (Map teamMap : listTeamMaps){
                         Team team = gson.fromJson(gson.toJson(teamMap), Team.class);
                         listTeam.add(team);
                     }
                     loadListTeamCallBack.onSuccess(listTeam);
                 }
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 loadListTeamCallBack.onFailure(e.getMessage());
             }
         });
    }


    public void loadListOtherTeam(String idPlayer, final CallBack<List<Team>,String> loadListOtherTeamCallBack) {
       functions.getHttpsCallable("getListTeamOther").call(idPlayer).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson= new Gson();
                List<Map> listTeamMaps = (List<Map>) httpsCallableResult.getData();
                List<Team> listTeam = new ArrayList<>();
                if(listTeamMaps == null){
                    loadListOtherTeamCallBack.onSuccess(new ArrayList<Team>());
                }else{
                    Log.d("uchiha", "vao day la null r "+listTeamMaps.size());
                    for (Map teamMap : listTeamMaps){
                        Team team = gson.fromJson(gson.toJson(teamMap), Team.class);
                        listTeam.add(team);
                    }
                    loadListOtherTeamCallBack.onSuccess(listTeam);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListOtherTeamCallBack.onFailure(e.getMessage());
            }
        });

//        db.collection("TeamMember").whereEqualTo("idPlayer", idPlayer).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                final List<String> listIdTeam = new ArrayList<>();
//                if (!queryDocumentSnapshots.isEmpty()) {
//                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
//                        String idteam = (String) document.get("idTeam");
//                        listIdTeam.add(idteam);
//                    }
//                }
//
//                db.collection("Team").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<Team> listTeam = new ArrayList<>();
//                        if (!queryDocumentSnapshots.isEmpty()) {
//                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
//                                Team team = document.toObject(Team.class);
//                                for(int i = 0 ; i < listIdTeam.size();i++){
//                                    if(listIdTeam.get(i).equals(team.getId())){
//                                        break;
//                                    }else if(i == (listIdTeam.size()-1)){
//                                       listTeam.add(team);
//                                    }
//
//                                }
//                            }
//                            loadListOtherTeamCallBack.onSuccess(listTeam);
//                        } else {
//                            loadListOtherTeamCallBack.onFailure("Null");
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        loadListOtherTeamCallBack.onFailure(e.getMessage());
//                    }
//                });
//
//            }
//        });
    }


    public void loadTeam(String idTeam, final CallBack<Team,String> callBack) {
        db.collection("Team").document(idTeam).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Team team = documentSnapshot.toObject(Team.class);
                callBack.onSuccess(team);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }


    public void updateProfile(Map<String, Object> updateData, final CallBack<String,String> callBack) {
        String uid = TeamViewModel.getInstance().getTeamLiveData().getValue().getId();
        db.collection("Team").document(uid).update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callBack.onSuccess("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                callBack.onFailure(e.getMessage());
            }
        });
    }


    public void updateImage(Uri uri, String path, boolean isAvatar, final CallBack<String ,String> callBack) {
        final String uid = TeamViewModel.getInstance().getTeamLiveData().getValue().getId();
        Date date = new Date();
        String urlFile = "", key = "";
        String[] parts = path.split("\\.");
        if (isAvatar) {
            key = "urlAvatar";
            urlFile = "/Avatar/" + uid + "_" + date.getTime() + "." + parts[1];

        } else {
            key = "urlCover";
            urlFile = "/Cover/" + uid + "_" + date.getTime() + "." + parts[1];
        }

        final String finalUrlFile = urlFile;
        final String finalKey = key;
        storage.getReference().child(urlFile).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Map<String, Object> map = new HashMap<>();

                map.put(finalKey, finalUrlFile);// "avatar/dsa.jpg"
                db.collection("Team").document(uid).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callBack.onSuccess(finalUrlFile);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public FileDownloadTask getUserPhoto(String url, File downloadLocation) {
        StorageReference fileRef = storage.getReference().child(url);
        return fileRef.getFile(downloadLocation);
    }


}
