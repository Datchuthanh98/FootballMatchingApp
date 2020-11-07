package com.example.myclub.data.datasource;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.model.Chat;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Evaluate;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;
import com.example.myclub.session.SessionUser;
import com.example.myclub.session.SessionTeam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

import static org.testng.reporters.jq.BasePanel.C;

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


    public void createTeam(final Map<String,Object> map, final CallBack<Team,String> callBack) {
        final String idPlayer = SessionUser.getInstance().getPlayerLiveData().getValue().getId();
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
    }


    public void loadTeam(String idTeam, final CallBack<Team,String> callBack) {
        functions.getHttpsCallable("getTeamDetail").call(idTeam).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Team team = convert.fromJson(convert.toJson(httpsCallableResult.getData()), Team.class);
                callBack.onSuccess(team);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure("");
            }
        });
    }


    public void updateProfile(Map<String, Object> updateData, final CallBack<String,String> callBack) {
        String uid = SessionTeam.getInstance().getTeamLiveData().getValue().getId();
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
        final String uid = SessionTeam.getInstance().getTeamLiveData().getValue().getId();
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

    public void loadChatTeam(String idTeam,final CallBack<List<Chat>,String> callBack) {
        db.collection("Team").document(idTeam).collection("chatTeam").orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<Chat>  chats = queryDocumentSnapshots.toObjects(Chat.class);
                callBack.onSuccess(chats);
            }
        });

    }


    public void addChat(String idTeam, Map<String, Object> map, final CallBack<String,String > callBack) {
        db.collection("Team").document(idTeam).collection("chatTeam").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                callBack.onSuccess("add chat sucess");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onSuccess("add chat failure");
            }
        });
    }


    public  void getListEvaluate(String idTeam , final CallBack<List<Evaluate>,String> loadListTeamCallBack){
        functions.getHttpsCallable("getListEvaluate").call(idTeam).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson= new Gson();
                List<Map> listCommentMaps = (List<Map>) httpsCallableResult.getData();
                List<Evaluate> listComments = new ArrayList<>();
                if(listCommentMaps == null){
                    loadListTeamCallBack.onSuccess(new ArrayList<Evaluate>());
                }else{
                    for (Map commentMap : listCommentMaps){
                        Evaluate comment = gson.fromJson(gson.toJson(commentMap), Evaluate.class);
                        listComments.add(comment);
                    }
                    loadListTeamCallBack.onSuccess(listComments);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListTeamCallBack.onFailure(e.getMessage());
            }
        });
    }

    public void addEvaluate(Map<String,Object> map, final CallBack<String ,String>  callBack){
        DocumentReference ref = db.collection("Evaluate").document();
        map.put("id", ref.getId());
        ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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



}
