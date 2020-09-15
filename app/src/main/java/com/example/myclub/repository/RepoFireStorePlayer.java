package com.example.myclub.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.OnFirebaseDelete;
import com.example.myclub.Interface.OnFirebaseUpdate;
import com.example.myclub.Interface.OnGetLasttimeUpdateCallback;
import com.example.myclub.Interface.PlayerConnectFirebase;
import com.example.myclub.Interface.OnFirebaseInsert;
import com.example.myclub.model.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RepoFireStorePlayer {
    static RepoFireStorePlayer instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static RepoFireStorePlayer getInstance() {
        if (instance == null) {
            instance = new RepoFireStorePlayer();
        }
        return instance;
    }


    //Firestore realtime
    public void loadPlayer(final PlayerConnectFirebase playerConnectFirebase) {
        db.collection("Player").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Player> listPlayer = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Player p = new Player();
                        p.setName((String) document.get("name"));
                        p.setId((String) document.getId());
                        p.setHeight(document.getDouble("height"));
                        p.setWeight(document.getDouble("weight"));
                        p.setPosition((String) document.get("position"));
                        p.setPhone((String) document.get("phone"));
                        p.setEmail((String) document.get("email"));
                        p.setUrlAvatar((String) document.get("urlAvatar"));
                        long age = document.getLong("age");
                        p.setAge((int) age);
                        listPlayer.add(p);
                    }
                    playerConnectFirebase.onPlayerLoadedFromFireBase(listPlayer);
                } else {
                    Log.d("meomeo", "failed");
                }
            }
        });
    }


        public void updateTimestamp() {
            HashMap<String,Object> updateMap= new HashMap<>();
            updateMap.put("last_update_timestamp", Calendar.getInstance().getTimeInMillis());
            db.collection("lastUpdated").document("Player").set(updateMap);
        }

    public void getLastUpdateTimestamp(final OnGetLasttimeUpdateCallback onGetLasttimeUpdateCallback) {
        db.collection("lastUpdated").document("Player").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               long lastUpdateTimestamp=documentSnapshot.getLong("last_update_timestamp");
               onGetLasttimeUpdateCallback.onCallBack(lastUpdateTimestamp);
            }

        });
    }


    public void updatePlayer(final Player player,final OnFirebaseUpdate onFirebaseUpdate){
        DocumentReference newPlayer= db.collection("Player").document(player.getId());
        newPlayer.set(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    updateTimestamp();
                    onFirebaseUpdate.updateSqlite(player);
                }
            }
        });
    }

    public void insertPlayer(final Player player , final OnFirebaseInsert onFirebaseInsert){
        db.collection("Player").add(player).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                updateTimestamp();
                player.setId(documentReference.getId());
                onFirebaseInsert.insertSqlite(player);
            }
        });
    }

    public void deletePlayer(final String id , final OnFirebaseDelete onFirebaseDelete){
        db.collection("Player").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                updateTimestamp();
                onFirebaseDelete.deleteSqlite(id);
            }
        });
    }


    //Lang nghe change data in firestore
//        db.collection("Player").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                Log.d("checked", "vao day 1  r"+listPlayer.size());
//                if (!queryDocumentSnapshots.isEmpty()) {
//                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
//                        Player p = new Player();
//                        p.setName((String) document.get("name"));
//                        p.setId((String)document.get("id"));
////                        p.setHeight((Float.parseFloat((String)document.get("height"))));
////                        p.setWeight((Float.parseFloat((String)document.get("weight"))));
//                        p.setHeight((float) 100);
//                        p.setHeight((float) 100);
//                        p.setPhone((String)document.get("phone"));
//                        p.setPosition((String)document.get("position"));
//                        p.setEmail((String)document.get("email"));
//                        p.setUrlAvatar((String)document.get("urlAvatar"));
//                        Log.d("hichic", "onBindViewHolder: "+(String)document.get("urlAvatar"));
////                        p.setAge((Integer) document.get("age"));
//                        p.setAge(1);
//                        listPlayer.add(p);
//                    }
//                    playerConnectFirebase.onPlayerLoadedFromFireBase(listPlayer);
//                } else {
//                    Log.d("meomeo", "failed");
//                }
//            }
//        });
}
