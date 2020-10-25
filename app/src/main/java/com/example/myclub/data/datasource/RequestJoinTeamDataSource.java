package com.example.myclub.data.datasource;

import androidx.annotation.NonNull;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.model.RequestJoinTeam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class RequestJoinTeamDataSource {
    static RequestJoinTeamDataSource instance;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public static RequestJoinTeamDataSource getInstance() {
        if (instance == null) {
            instance = new RequestJoinTeamDataSource();
        }
        return instance;
    }

    public void addRequest(Map<String, Object> map, final CallBack<String, String> addRequestJoinTeam) {
        final DocumentReference ref = db.collection("RequestJoinTeam").document();
        map.put("id", ref.getId());
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


    public void cancelRequest(String key, final CallBack<String, String> cancelRequestJoinTeam) {
        db.collection("RequestJoinTeam").document(key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void getStateJoinTeamByTeam(Map<String, Object> requestJoin, final CallBack<RequestJoinTeam, String> getStateJoinTeam) {
        db.collection("RequestJoinTeam").whereEqualTo("idPlayer", requestJoin.get("idPlayer")).whereEqualTo("idTeam", requestJoin.get("idTeam")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    getStateJoinTeam.onSuccess(null);
                } else {
                    RequestJoinTeam requestJoinTeam = queryDocumentSnapshots.getDocuments().get(0).toObject(RequestJoinTeam.class);
                    getStateJoinTeam.onSuccess(requestJoinTeam);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getStateJoinTeam.onFailure(e.getMessage());
            }
        });
    }

    public void acceptJoinTeam(final Map<String, Object> acceptJoin, final CallBack<String, String> acceptJoinTeam) {
        db.collection("TeamMember").add(acceptJoin).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("RequestJoinTeam").document((String) acceptJoin.get("key")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        acceptJoinTeam.onSuccess("Success");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        acceptJoinTeam.onFailure(e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                acceptJoinTeam.onFailure(e.getMessage());
            }
        });
    }

    public void declineJoinTeam(final Map<String, Object> acceptJoin, final CallBack<String, String> declineJoinTeam) {
        db.collection("RequestJoinTeam").document((String) acceptJoin.get("key")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
