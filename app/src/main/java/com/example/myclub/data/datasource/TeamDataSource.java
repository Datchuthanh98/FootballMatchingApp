package com.example.myclub.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.LoadTeamCallBack;
import com.example.myclub.Interface.RegisterTeamCallBack;
import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.model.Team;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TeamDataSource {
    private final String TAG = "TeamDataSource";
    static TeamDataSource instance;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public static TeamDataSource getInstance() {
        if (instance == null) {
            instance = new TeamDataSource();
        }
        return instance;
    }


    public void createTeam(final String name, final String phone, final String email, final RegisterTeamCallBack callBack) {
                Map<String,Object> map = new HashMap<>();
                map.put("email", name);
                map.put("phone",phone);
                map.put("email",email);
                db.collection("Team").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                          callBack.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                          callBack.onFailure(e.getMessage());
                    }
                });

    }



    public void loadTeam(String idTeam, final String password, final LoadTeamCallBack callBack) {

                       db.collection("Team").document(idTeam).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                           @Override
                           public void onSuccess(DocumentSnapshot documentSnapshot) {
                             Team team = documentSnapshot.toObject(Team.class);
                               callBack.onSuccess(team);
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {

                           }
                       });


    }

    public void updateProfile(Map<String, Object> updateData, final UpdateProfileCallBack callBack) {
//        String uid = Session.getInstance().getPlayerLiveData().getValue().getId() ;
        String uid ="nb6va8HOIJSMFYcWdCO9m25ruZ33";
        db.collection("Player").document(uid).update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callBack.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }


    public FileDownloadTask getTeamPhoto(String url, File downloadLocation) {
        Log.d("check image ","vao day r");
        StorageReference fileRef = storage.getReference().child(url);
        return fileRef.getFile(downloadLocation);
    }



}
