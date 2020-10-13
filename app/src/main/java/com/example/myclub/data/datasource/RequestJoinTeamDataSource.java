package com.example.myclub.data.datasource;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.AcceptJoinTeam;
import com.example.myclub.Interface.AddRequestJoinTeam;
import com.example.myclub.Interface.CancelRequestJoinTeam;
import com.example.myclub.Interface.DeclineJoinTeam;
import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.Interface.GetStateJoinTeam;
import com.example.myclub.model.RequestJoinTeam;
import com.example.myclub.model.Team;
import com.example.myclub.model.Todo;
import com.example.myclub.viewModel.PlayerViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestJoinTeamDataSource {

    private final String TAG = "UserDataSource";
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

    public void addRequest(Map<String, Object> requestJoin, final AddRequestJoinTeam addRequestJoinTeam) {
        db.collection("RequestJoinTeam").add(requestJoin).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(final DocumentReference documentReference) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", documentReference.getId());
                db.collection("RequestJoinTeam").document(documentReference.getId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addRequestJoinTeam.onSuccess(documentReference.getId());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addRequestJoinTeam.onFailure();
            }
        });

    }


    public void cancelRequest(String key, final CancelRequestJoinTeam cancelRequestJoinTeam) {
        db.collection("RequestJoinTeam").document(key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cancelRequestJoinTeam.onSuccess();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                cancelRequestJoinTeam.onFailure();
            }
        });
    }


    public void getStateJoinTeamByTeam(Map<String, Object> requestJoin, final GetStateJoinTeam getStateJoinTeam) {
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

    public void acceptJoinTeam(final Map<String, Object> acceptJoin, final AcceptJoinTeam acceptJoinTeam) {
        db.collection("TeamMember").add(acceptJoin).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("RequestJoinTeam").document((String) acceptJoin.get("key")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        acceptJoinTeam.onSuccess();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        acceptJoinTeam.onFailure();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                acceptJoinTeam.onFailure();
            }
        });
    }

    public void declineJoinTeam(final Map<String, Object> acceptJoin, final DeclineJoinTeam declineJoinTeam) {
        db.collection("RequestJoinTeam").document((String) acceptJoin.get("key")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                declineJoinTeam.onSuccess();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                declineJoinTeam.onFailure();
            }
        });

    }


}
