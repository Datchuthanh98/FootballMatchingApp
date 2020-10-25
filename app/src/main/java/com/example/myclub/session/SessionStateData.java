package com.example.myclub.session;

import androidx.lifecycle.MutableLiveData;

import com.example.myclub.data.enumeration.DataState;

public class SessionStateData {
      private MutableLiveData<DataState> datalistMatch = new MutableLiveData<>(DataState.NOW);
      private MutableLiveData<DataState> datalistBooking = new MutableLiveData<>(DataState.NOW);
      private MutableLiveData<DataState> datalistMyTeam = new MutableLiveData<>(DataState.NOW);
      private MutableLiveData<DataState> datalistPlayerByTeam = new MutableLiveData<>(DataState.NOW);
    private MutableLiveData<DataState> datalistRequestByTeam = new MutableLiveData<>(DataState.NOW);
      private static SessionStateData instance;
    public static SessionStateData getInstance() {
        if (instance == null) {
            instance = new SessionStateData();
        }
        return instance;
    }


    public MutableLiveData<DataState> getDatalistMatch() {
        return datalistMatch;
    }

    public void setDatalistMatch(DataState datalistField) {
        this.datalistMatch.setValue(datalistField);
    }

    public MutableLiveData<DataState> getDatalistBooking() {
        return datalistBooking;
    }

    public void setDatalistBooking(DataState datalistBooking) {
        this.datalistBooking.setValue(datalistBooking);
    }

    public MutableLiveData<DataState> getDatalistMyTeam() {
        return datalistMyTeam;
    }

    public void setDatalistMyTeam(DataState datalistMyTeam) {
        this.datalistMyTeam.setValue(datalistMyTeam);
    }

    public MutableLiveData<DataState> getDatalistPlayerByTeam() {
        return datalistPlayerByTeam;
    }

    public void setDatalistPlayerByTeam(DataState datalistPlayerByTeam) {
        this.datalistPlayerByTeam.setValue(datalistPlayerByTeam);
    }

    public MutableLiveData<DataState> getDatalistRequestByTeam() {
        return datalistRequestByTeam;
    }

    public void setDatalistRequestByTeam(DataState datalistRequestByTeam) {
        this.datalistRequestByTeam.setValue(datalistRequestByTeam);
    }
}


