package com.example.myclub.data.datasource;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.LoadListOtherTeamCallBack;
import com.example.myclub.Interface.LoadListTeamCallBack;
import com.example.myclub.Interface.LoadTeamCallBack;
import com.example.myclub.Interface.RegisterTeamCallBack;
import com.example.myclub.Interface.UpdateImageCallBack;
import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.model.Team;
import com.example.myclub.viewModel.PlayerViewModel;
import com.example.myclub.viewModel.TeamViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> map = new HashMap<>();
        final String idPlayer = PlayerViewModel.getInstance().getPlayerLiveData().getValue().getId();

        map.put("name", name);
        map.put("phone", phone);
        map.put("email", email);
        map.put("level", "basic");
        map.put("idCaption", idPlayer);
        map.put("urlAvatar", "/Avatar/avatar_team_default.jpg");
        map.put("urlCover", "/Cover/cover_default.jpg");

        db.collection("Team").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(final DocumentReference documentReference) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", documentReference.getId());
                db.collection("Team").document(documentReference.getId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        final Map<String, Object> mapTeamMember = new HashMap<>();
                        mapTeamMember.put("idPlayer", idPlayer);
                        mapTeamMember.put("idTeam", documentReference.getId());
                        db.collection("TeamMember").add(mapTeamMember).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                callBack.onSuccess();
                            }
                        });
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


    public void loadListTeam(String id, final LoadListTeamCallBack loadListTeamCallBack) {

        db.collection("TeamMember").whereEqualTo("idPlayer", id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> listIdTeam = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String idteam = (String) document.get("idTeam");
                        listIdTeam.add(idteam);
                    }
                    db.collection("Team").whereIn("id", listIdTeam).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<Team> listTeam = new ArrayList<>();
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                    Team team = document.toObject(Team.class);
                                    listTeam.add(team);
                                }
                                loadListTeamCallBack.onSuccess(listTeam);
                            } else {
                                loadListTeamCallBack.onFailure("Null");
                            }
                        }
                    });
                }
            }
        });
    }


    public void loadListOtherTeam(String id, final LoadListOtherTeamCallBack loadListOtherTeamCallBack) {
        db.collection("Team").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Team> listTeam = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Team team = document.toObject(Team.class);
                        listTeam.add(team);
                    }
                    loadListOtherTeamCallBack.onSuccess(listTeam);
                } else {
                    loadListOtherTeamCallBack.onFailure("Null");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListOtherTeamCallBack.onFailure(e.getMessage());
            }
        });
    }


    public void loadTeam(String idTeam, final LoadTeamCallBack callBack) {
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


    public void updateProfile(Map<String, Object> updateData, final UpdateProfileCallBack callBack) {

        String uid = TeamViewModel.getInstance().getTeamLiveData().getValue().getId();
        Log.d("meomeo", "updateProfile: " + uid);
        db.collection("Team").document(uid).update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void updateImage(Uri uri, String path, boolean isAvatar, final UpdateImageCallBack callBack) {
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
