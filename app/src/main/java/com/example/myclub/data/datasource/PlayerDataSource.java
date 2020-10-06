package com.example.myclub.data.datasource;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.LoadListOtherPlayerCallBack;
import com.example.myclub.Interface.LoadListOtherTeamCallBack;
import com.example.myclub.Interface.LoadListPlayerCallBack;
import com.example.myclub.Interface.LoadListTeamCallBack;
import com.example.myclub.Interface.LoadPlayerCallBack;
import com.example.myclub.Interface.LoadTeamCallBack;
import com.example.myclub.Interface.LoginCallBack;
import com.example.myclub.Interface.RegisterPlayerCallBack;
import com.example.myclub.Interface.UpdateImageCallBack;
import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.model.Team;
import com.example.myclub.viewModel.PlayerViewModel;
import com.example.myclub.model.Player;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class PlayerDataSource {
    private final String TAG = "UserDataSource";
    static PlayerDataSource instance;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public static PlayerDataSource getInstance() {
        if (instance == null) {
            instance = new PlayerDataSource();
        }
        return instance;
    }


    public void register(final String email, final String password, final RegisterPlayerCallBack callBack) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String,Object> map = new HashMap<>();
                map.put("id", authResult.getUser().getUid());
                map.put("email", email);
                map.put("password",password);
                map.put("urlAvatar","/Avatar/avatar_player_default.jpg");
                map.put("urlCover","/Cover/cover_default.jpg");
                db.collection("Player").document(authResult.getUser().getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                         callBack.onSuccess();
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

    public void login(String email, final String password, final LoginCallBack callBack) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = authResult.getUser();
                       db.collection("Player").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                           @Override
                           public void onSuccess(DocumentSnapshot documentSnapshot) {
                             Player player = documentSnapshot.toObject(Player.class);
                               callBack.onSuccess(player);
                           }
                       });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       callBack.onFailure(e.getMessage());
                    }
                });
    }

    public void updateProfile(Map<String, Object> updateData, final UpdateProfileCallBack callBack) {
        String uid = PlayerViewModel.getInstance().getPlayerLiveData().getValue().getId();
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


    public void updateImage(Uri uri, String path ,boolean isAvatar, final UpdateImageCallBack callBack){
        final String uid = PlayerViewModel.getInstance().getPlayerLiveData().getValue().getId();
        Date date = new Date();
        String urlFile ="", key="";
        String[] parts = path.split("\\.");
        if(isAvatar) {
            key="urlAvatar";
            urlFile = "/Avatar/"+uid+"_"+date.getTime()+"."+parts[1];

        }else{
            key="urlCover";
            urlFile = "/Cover/"+uid+"_"+date.getTime()+"."+parts[1];
        }

        final String finalUrlFile = urlFile;
        final String finalKey = key;
        storage.getReference().child(urlFile).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Map<String,Object> map = new HashMap<>();

                map.put(finalKey, finalUrlFile);// "avatar/dsa.jpg"
                db.collection("Player").document(uid).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        Log.d("image ","vao day r");
        StorageReference fileRef = storage.getReference().child(url);
        return fileRef.getFile(downloadLocation);
    }




    public void loadPlayer(String idPlayer, final LoadPlayerCallBack callBack) {
        db.collection("Player").document(idPlayer).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Player player = documentSnapshot.toObject(Player.class);
                callBack.onSuccess(player);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public void loadListPlayer(String idTeam , final LoadListPlayerCallBack loadListPlayerCallBack) {
        db.collection("TeamMember").whereEqualTo("idTeam", idTeam).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> listIdPlayer = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String idteam = (String) document.get("idPlayer");
                        listIdPlayer.add(idteam);
                    }
                    db.collection("Player").whereIn("id",listIdPlayer).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<Player> listPlayers = new ArrayList<>();
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                    Player player = document.toObject(Player.class);
                                    listPlayers.add(player);
                                }
                                loadListPlayerCallBack.onSuccess(listPlayers);
                            } else {
                                loadListPlayerCallBack.onFailure("Null");
                            }
                        }
                    });
                }
            }
        });
    }


    public void loadListOtherPlayer(String idPlayer ,final LoadListOtherPlayerCallBack loadListOtherPlayerCallBack) {
        db.collection("Player").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Player> listPlayers = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Player player = document.toObject(Player.class);
                        listPlayers.add(player);
                    }
                    loadListOtherPlayerCallBack.onSuccess(listPlayers);
                } else {
                    loadListOtherPlayerCallBack.onFailure("Null");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListOtherPlayerCallBack.onFailure(e.getMessage());
            }
        });
    }





}