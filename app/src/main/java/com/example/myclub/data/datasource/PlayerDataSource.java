package com.example.myclub.data.datasource;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.CallBack;;
import com.example.myclub.data.session.SessionUser;
import com.example.myclub.model.Player;

import com.example.myclub.model.Team;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Calendar;
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
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();
    private Gson convert = new Gson();

    public static PlayerDataSource getInstance() {
        if (instance == null) {
            instance = new PlayerDataSource();
        }
        return instance;
    }

    public void register(final String email, final String password, final CallBack<Player, String> callBack) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", "ronaldoo");
        functions.getHttpsCallable("createPlayer").call(map).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                String json = convert.toJson(result.get("data"));
                Player player = convert.fromJson(json, Player.class);
                callBack.onSuccess(player);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });

    }

    public void login(String email, final String password, final CallBack<Player, String> callBack) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = authResult.getUser();
                        functions.getHttpsCallable("getPlayerDetail").call(user.getUid()).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                            @Override
                            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                                Player player = convert.fromJson(convert.toJson(httpsCallableResult.getData()), Player.class);
                                callBack.onSuccess(player);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callBack.onFailure(e.getMessage());
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

    public void updateProfile(Map<String, Object> updateData, final CallBack<String, String> callBack) {
        if (SessionUser.getInstance().getPlayerLiveData().getValue() != null) {
            String uid = SessionUser.getInstance().getPlayerLiveData().getValue().getId();
            db.collection("Player").document(uid).update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void updateImage(Uri uri, String path, boolean isAvatar, final CallBack callBack) {
        final String uid = SessionUser.getInstance().getPlayerLiveData().getValue().getId();
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
                db.collection("Player").document(uid).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callBack.onSuccess(finalUrlFile);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onFailure(e.getMessage());
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

    public void loadListPlayer(String idTeam, final CallBack<List<Player>, String> loadListPlayerCallBack) {
        functions.getHttpsCallable("getListPlayer").call(idTeam).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson = new Gson();
                List<Map> listPlayerMaps = (List<Map>) httpsCallableResult.getData();
                List<Player> listPlayer = new ArrayList<>();
                if (listPlayerMaps == null) {
                    loadListPlayerCallBack.onSuccess(null);
                } else {
                    for (Map playerMap : listPlayerMaps) {
                        Player player = gson.fromJson(gson.toJson(playerMap), Player.class);
                        listPlayer.add(player);
                    }
                    loadListPlayerCallBack.onSuccess(listPlayer);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListPlayerCallBack.onFailure(e.getMessage());
            }
        });
    }

    public void loadListPlayerRequest(String idTeam, final CallBack<List<Player>, String> loadListPlayerRequestCallBack) {
        functions.getHttpsCallable("getListPlayerRequest").call(idTeam).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson = new Gson();
                List<Map> listPlayerMaps = (List<Map>) httpsCallableResult.getData();
                List<Player> listPlayer = new ArrayList<>();
                if (listPlayerMaps == null) {
                    loadListPlayerRequestCallBack.onSuccess(null);
                } else {
                    for (Map playerMap : listPlayerMaps) {
                        Player player = gson.fromJson(gson.toJson(playerMap), Player.class);
                        listPlayer.add(player);
                    }
                    loadListPlayerRequestCallBack.onSuccess(listPlayer);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListPlayerRequestCallBack.onFailure(e.getMessage());
            }
        });
    }
}
