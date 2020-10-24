package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.model.Team;

public class ShareExecuteJoinMatchViewModel extends ViewModel {
    private MutableLiveData<String> idTeam = new MutableLiveData<>(null);

    public MutableLiveData<String> getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam.setValue(idTeam);
    }
}
