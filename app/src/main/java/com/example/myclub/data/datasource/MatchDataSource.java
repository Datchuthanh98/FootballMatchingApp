package com.example.myclub.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.AddBookingField;
import com.example.myclub.Interface.LoadCallBack;
import com.example.myclub.Interface.LoadListBookingCallBack;
import com.example.myclub.Interface.LoadListField;
import com.example.myclub.Interface.LoadListMatchCallBack;
import com.example.myclub.Interface.LoadListTimeCallBack;
import com.example.myclub.model.Booking;
import com.example.myclub.model.Field;
import com.example.myclub.model.Match;
import com.example.myclub.model.TimeGame;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchDataSource {
    private static final String TAG ="matchdata" ;
    static MatchDataSource instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static MatchDataSource getInstance() {
        if (instance == null) {
            instance = new MatchDataSource();
        }
        return instance;
    }

    //Firestore realtime
    public void loadListBooking(final LoadCallBack<List<Booking>, String> callBack) {
            db.collection("Booking").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<Booking> bookingList = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Booking booking =  document.toObject(Booking.class);

                            bookingList.add(booking);
                        }
                        callBack.onSuccess(bookingList);
                    } else {

                        
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

    }


    public void addBooking(Map<String, Object> map, final AddBookingField addBookingField) {
        DocumentReference ref = db.collection("Booking").document();
        map.put("id", ref.getId());
        map.put("approve",null);
        ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                addBookingField.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addBookingField.onFailure();
            }
        });
    }

    public void loadListMatch(final LoadListMatchCallBack loadListMatchCallBack) {
        db.collection("Match").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Match> matchList = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Match match = document.toObject(Match.class);
                        matchList.add(match);
                    }

                    loadListMatchCallBack.onSuccess(matchList);
                } else {
                    loadListMatchCallBack.onSuccess(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
