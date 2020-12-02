package com.example.myclub.session;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.Team;
import com.example.myclub.model.TimeGame;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListTimeVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionBookingField {
    private static SessionBookingField instance;
    private MatchRepository  matchRepository = MatchRepository.getInstance();
    private MutableLiveData<Team> teamLiveData = new MutableLiveData<>(null);
    private MutableLiveData<TimeGame> timeLiveData = new MutableLiveData<>(null);
    private MutableLiveData<Result> bookingResult = new MutableLiveData<>();

    private SessionBookingField(){}

    public static SessionBookingField getInstance() {
        if (instance == null) {
            instance = new SessionBookingField();
        }
        return instance;
    }


    public MutableLiveData<Team> getTeamLiveData() {
        return teamLiveData;
    }

    public MutableLiveData<TimeGame> getTimeLiveData() {
        return timeLiveData;
    }

    public void setTeamLiveData(Team teamLiveData) {
        this.teamLiveData.setValue(teamLiveData);
    }

    public void setTimeLiveData(TimeGame timeLiveData) {
        this.timeLiveData.setValue(timeLiveData);
    }



    public  void addBookingField( Map<String,Object> map){
        matchRepository.addbookingField(map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                bookingResult.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String s) {
                bookingResult.setValue(Result.FAILURE);
            }
    });
    }

    public MutableLiveData<Result> getBookingResult() {
        return bookingResult;
    }

    public void setBookingResult(Result result) {
        this.bookingResult.setValue(result);
    }
}
