package com.example.myclub.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.AddBookingField;
import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.Interface.LoadListField;
import com.example.myclub.Interface.LoadListTimeCallBack;
import com.example.myclub.model.Field;
import com.example.myclub.model.TimeGame;
import com.example.myclub.model.Todo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldDataSource {
    static FieldDataSource instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static FieldDataSource getInstance() {
        if (instance == null) {
            instance = new FieldDataSource();
        }
        return instance;
    }

    //Firestore realtime
    public void loadListField(final LoadListField loadListField) {
            db.collection("Field").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<Field> fieldList = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Field field =  document.toObject(Field.class);

                            fieldList.add(field);
                        }
                        loadListField.FirebaseLoadListTodo(fieldList);
                    } else {
                        Log.d("meomeo", "failed");
                        
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

    }

    public void loadListTime(String idTeam, final LoadListTimeCallBack loadListTimeCallBack) {

        db.collection("TimeGame").whereEqualTo("idField", idTeam).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final List<TimeGame> listTimeGame = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        TimeGame timeGame = document.toObject(TimeGame.class);
                        listTimeGame.add(timeGame);
                    }
                     loadListTimeCallBack.onSuccess(listTimeGame);
                }else {
                    loadListTimeCallBack.onSuccess(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListTimeCallBack.onFailure(e.getMessage());
            }
        });
    }


}
