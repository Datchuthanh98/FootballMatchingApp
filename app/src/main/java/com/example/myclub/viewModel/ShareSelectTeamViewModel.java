package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.model.Team;

public class ShareSelectTeamViewModel extends ViewModel {
    private MutableLiveData<Team> selectedTeam = new MutableLiveData<>(null);

    public MutableLiveData<Team> getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam.setValue(selectedTeam);
    }
}
