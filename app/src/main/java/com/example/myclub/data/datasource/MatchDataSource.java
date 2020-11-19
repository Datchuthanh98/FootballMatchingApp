package com.example.myclub.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.model.Booking;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Match;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchDataSource {
    static MatchDataSource instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();
    private Gson convert = new Gson();
    public static MatchDataSource getInstance() {
        if (instance == null) {
            instance = new MatchDataSource();
        }
        return instance;
    }


    public void loadListBooking(final CallBack<List<Booking>, String> callBack) {
//            db.collection("Booking").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                @Override
//                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                    List<Booking> bookingList = new ArrayList<>();
//                    if (!queryDocumentSnapshots.isEmpty()) {
//                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
//                            Booking booking =  document.toObject(Booking.class);
//
//                            bookingList.add(booking);
//                        }
//                        callBack.onSuccess(bookingList);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                    callBack.onSuccess(null);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                        callBack.onFailure(e.getMessage());
//                }
//            });
    }


    public void addBooking(Map<String, Object> map, final CallBack<String,String> addBookingField) {
        functions.getHttpsCallable("createBooking").call(map).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                addBookingField.onSuccess("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("addbooking", "onFailure: "+e.getMessage());
                addBookingField.onFailure(e.getMessage());
            }
        });
    }



    public void loadListMyMatch(String idTeam, final CallBack<List<Match>,String> loadListMyMatchCallBack) {
        functions.getHttpsCallable("getListMatchByTeam").call(idTeam).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson= new Gson();
                List<Map> listTeamMaps = (List<Map>) httpsCallableResult.getData();
                List<Match> listMatch = new ArrayList<>();
                if(listTeamMaps == null){
                    loadListMyMatchCallBack.onSuccess(null);
                }else{
                    for (Map teamMap : listTeamMaps){
                        Match match = gson.fromJson(gson.toJson(teamMap), Match.class);
                        listMatch.add(match);
                    }
                    loadListMyMatchCallBack.onSuccess(listMatch);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListMyMatchCallBack.onFailure(e.getMessage());
            }
        });
    }

    public void loadListMatch(final CallBack loadListMatchCallBack) {
        functions.getHttpsCallable("getAllListMatch").call().addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson= new Gson();
                List<Map> listTeamMaps = (List<Map>) httpsCallableResult.getData();
                List<Match> listMatch = new ArrayList<>();
                if(listTeamMaps == null){
                    loadListMatchCallBack.onSuccess(null);
                }else{
                    for (Map teamMap : listTeamMaps){
                        Match match = gson.fromJson(gson.toJson(teamMap), Match.class);
                        listMatch.add(match);
                    }
                    loadListMatchCallBack.onSuccess(listMatch);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListMatchCallBack.onFailure(e.getMessage());
            }
        });
    }

    public void loadListMatchByDate(String date,final CallBack loadListMatchCallBack) {
        functions.getHttpsCallable("getListMatchByDate").call(date).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson= new Gson();
                List<Map> listTeamMaps = (List<Map>) httpsCallableResult.getData();
                List<Match> listMatch = new ArrayList<>();
                if(listTeamMaps == null || listTeamMaps.size() == 0){
                    loadListMatchCallBack.onSuccess(null);
                }else{
                    for (Map teamMap : listTeamMaps){
                        Match match = gson.fromJson(gson.toJson(teamMap), Match.class);
                        listMatch.add(match);
                    }
                    loadListMatchCallBack.onSuccess(listMatch);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListMatchCallBack.onFailure(e.getMessage());
            }
        });
    }


    public  void getMatchDetail(String idMatch , final CallBack<Match,String> callBack){
        functions.getHttpsCallable("getMatchDetail").call(idMatch).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Match match = convert.fromJson(convert.toJson(httpsCallableResult.getData()), Match.class);
                            callBack.onSuccess(match);
                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               callBack.onFailure(e.getMessage());
            }
        });
    }

    public  void getListQueueTeam(String idMatch , final CallBack<List<Team>,String> loadListTeamCallBack){
        functions.getHttpsCallable("getListQueueTeam").call(idMatch).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson= new Gson();
                List<Map> listTeamMaps = (List<Map>) httpsCallableResult.getData();
                List<Team> listTeam = new ArrayList<>();
                if(listTeamMaps == null){
                    loadListTeamCallBack.onSuccess(new ArrayList<Team>());
                }else{
                    for (Map teamMap : listTeamMaps){
                        Team team = gson.fromJson(gson.toJson(teamMap), Team.class);
                        listTeam.add(team);
                    }
                    loadListTeamCallBack.onSuccess(listTeam);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListTeamCallBack.onFailure(e.getMessage());
            }
        });
    }

    public  void getListComment(String idMatch , final CallBack<List<Comment>,String> loadListTeamCallBack){
        functions.getHttpsCallable("getCommentMatch").call(idMatch).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson= new Gson();
                List<Map> listCommentMaps = (List<Map>) httpsCallableResult.getData();
                List<Comment> listComments = new ArrayList<>();
                if(listCommentMaps == null){
                    loadListTeamCallBack.onSuccess(new ArrayList<Comment>());
                }else{
                    for (Map commentMap : listCommentMaps){
                        Comment comment = gson.fromJson(gson.toJson(commentMap), Comment.class);
                        listComments.add(comment);
                    }
                    loadListTeamCallBack.onSuccess(listComments);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListTeamCallBack.onFailure(e.getMessage());
            }
        });
    }

    public void addComment(String idMatch,Map<String,Object> map, final CallBack<String ,String>  callBack){
            DocumentReference ref = db.collection("Match").document(idMatch).collection("listComment").document();
        map.put("id", ref.getId());
        ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void addQueueTeam(String idMatch,Map<String,Object> map, final CallBack<String ,String>  callBack){
        DocumentReference ref = db.collection("Match").document(idMatch).collection("listQueueTeam").document();
        map.put("id", ref.getId());
        ref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void acceptTeam(String idBooking,Map<String, Object> map, final CallBack<String, String> acceptCallBack) {
        db.collection("Booking").document(idBooking).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                acceptCallBack.onSuccess("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                acceptCallBack.onFailure("");
            }
        });
    }
}
