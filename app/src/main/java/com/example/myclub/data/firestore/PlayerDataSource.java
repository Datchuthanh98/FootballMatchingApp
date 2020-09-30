package com.example.myclub.data.firestore;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.Interface.LoginCallBack;
import com.example.myclub.Interface.RegisterCallBack;
import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.data.session.Session;
import com.example.myclub.model.Player;
import com.example.myclub.model.Todo;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
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


    public void register(final String email, final String password, final RegisterCallBack callBack) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String,Object> map = new HashMap<>();
                map.put("id", authResult.getUser().getUid());
                map.put("email", email);
                map.put("password",password);
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

    public void updateImage(Uri uri, String path , final UpdateProfileCallBack callBack){
          storage.getReference().child(path).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  callBack.onSuccess();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                  callBack.onFailure(e.getMessage());
             }
         });
    }
}
