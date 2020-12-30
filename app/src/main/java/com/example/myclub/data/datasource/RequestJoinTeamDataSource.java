package com.example.myclub.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;


import com.example.myclub.Interface.CallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.storage.FirebaseStorage;
import com.myhexaville.smartimagepicker.ImagePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestJoinTeamDataSource {
    static RequestJoinTeamDataSource instance;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();

    public static RequestJoinTeamDataSource getInstance() {
        if (instance == null) {
            instance = new RequestJoinTeamDataSource();
        }
        return instance;
    }

    public void addRequest(String idTeam,Map<String, Object> map, final CallBack<String, String> addRequestJoinTeam) {
        final DocumentReference ref = db.collection("Team").document(idTeam).collection("listRequest").document((String) map.get("player"));
        ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                addRequestJoinTeam.onSuccess(ref.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addRequestJoinTeam.onFailure(e.getMessage());
            }
        });
    }


    public void cancelRequest(String idTeam,String idPlayer, final CallBack<String, String> cancelRequestJoinTeam) {
        db.collection("Team").document(idTeam).collection("listRequest").document(idPlayer).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cancelRequestJoinTeam.onSuccess("Success");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                cancelRequestJoinTeam.onFailure(e.getMessage());
            }
        });
    }


    public void getStateJoinTeamByTeam( String idTeam,String idPlayer, final CallBack<Boolean, String> getStateJoinTeam) {
        db.collection("Team").document(idTeam).collection("listRequest").whereEqualTo("player", idPlayer).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    getStateJoinTeam.onSuccess(Boolean.FALSE);
                } else {
                    getStateJoinTeam.onSuccess(Boolean.TRUE);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getStateJoinTeam.onFailure(e.getMessage());
            }
        });
    }

    public void acceptJoinTeam(final String idTeam, final String idPlayer, final CallBack<String, String> acceptJoinTeam) {
        Map<String, Object> map = new HashMap<>();
        map.put("idPlayer", idPlayer);
        map.put("idTeam",idTeam);
        map.put("time_create",Calendar.getInstance().getTimeInMillis());
        functions.getHttpsCallable("acceptJoinTeam").call(map).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                acceptJoinTeam.onSuccess("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                acceptJoinTeam.onFailure(e.getMessage());
            }
        });
    }

    public void declineJoinTeam(String idTeam,String idPlayer, final CallBack<String, String> declineJoinTeam) {
        db.collection("Team").document(idTeam).collection("listRequest").document(idPlayer).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                declineJoinTeam.onSuccess("sucess");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                declineJoinTeam.onFailure(e.getMessage());
            }
        });
    }


}
