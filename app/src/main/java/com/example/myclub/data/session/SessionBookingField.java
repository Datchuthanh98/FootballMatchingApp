package com.example.myclub.data.session;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.Team;
import com.example.myclub.model.TimeGame;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListTimeVertical;

import java.util.List;
import java.util.Map;

public class SessionBookingField {
    private static SessionBookingField instance;
    private MatchRepository  matchRepository = MatchRepository.getInstance();
    private FieldRepository  fieldRepository = FieldRepository.getInstance();
    private MutableLiveData<Team> teamLiveData = new MutableLiveData<>();
    private MutableLiveData<Field> fieldLiveData = new MutableLiveData<>();
    private MutableLiveData<TimeGame> timeLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TimeGame>> listTimeLiveData = new MutableLiveData<>(null);
    private RecycleViewAdapterListTimeVertical adapterListTimeVertical = new RecycleViewAdapterListTimeVertical();

    private SessionBookingField(){}

    public static SessionBookingField getInstance() {
        if (instance == null) {
            instance = new SessionBookingField();
        }
        return instance;
    }


    public void onChangeSelectTeam(){
        fieldRepository.getListTimeByField(fieldLiveData.getValue().getId(), new CallBack<List<TimeGame>, String>() {
            @Override
            public void onSuccess(List<TimeGame> listTimeGames) {
                Log.d("match", "onSuccess:"+listTimeGames.size());
                listTimeLiveData.setValue(listTimeGames);
                adapterListTimeVertical.setListTime(listTimeGames);
                adapterListTimeVertical.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }


    public RecycleViewAdapterListTimeVertical getAdapterListTimeVertical(){
        return  adapterListTimeVertical;
    }

    public MutableLiveData<Team> getTeamLiveData() {
        return teamLiveData;
    }

    public MutableLiveData<Field> getFieldLiveData() {
        return fieldLiveData;
    }

    public MutableLiveData<TimeGame> getTimeLiveData() {
        return timeLiveData;
    }


    public void setTeamLiveData(Team teamLiveData) {
        this.teamLiveData.setValue(teamLiveData);
    }

    public void setFieldLiveData(Field fieldLiveData) {
        this.fieldLiveData.setValue(fieldLiveData);
    }

    public void setTimeLiveData(TimeGame timeLiveData) {
        this.timeLiveData.setValue(timeLiveData);
    }



    public MutableLiveData<List<TimeGame>> getListTimeLiveData() {
        return listTimeLiveData;
    }

    public void setListTimeLiveData(List<TimeGame> listTimeLiveData) {
        this.listTimeLiveData.setValue(listTimeLiveData);
    }

    public  void addBookingField( Map<String,Object> map){
        matchRepository.addbookingField(map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(String s) {

            }
    });

    }

}
