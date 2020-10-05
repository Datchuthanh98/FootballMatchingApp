package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.Interface.LoadListOtherTeamCallBack;
import com.example.myclub.Interface.LoadListTeamCallBack;
import com.example.myclub.Interface.RegisterTeamCallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.data.repository.TestRepository;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;
import com.example.myclub.model.Todo;
import com.example.myclub.view.Field.Adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.view.Player.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamHorizontal;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;

public class ListMyTeamViewModel extends ViewModel {

    private TeamRepository teamRepository = TeamRepository.getInstance();
    private PlayerViewModel playerViewModel = PlayerViewModel.getInstance();
    private static ListMyTeamViewModel instance;
    private RecycleViewAdapterListTeamVertical adapterListTeam = new RecycleViewAdapterListTeamVertical();
    private RecycleViewAdapterListTeamVertical adapterListOtherTeam = new RecycleViewAdapterListTeamVertical();

    private MutableLiveData<List<Team>> listTeamLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;


    public static ListMyTeamViewModel getInstance() {
        if (instance == null) {
            instance = new ListMyTeamViewModel();
        }
        return instance;
    }

    public ListMyTeamViewModel() {
        getListTeam();
        getListOtherTeam();
    }

    public void getListTeam(){
        String idPlayer = playerViewModel.getPlayerLiveData().getValue().getId();
        teamRepository.getListTeam(idPlayer,new LoadListTeamCallBack() {
            @Override
            public void onSuccess(List<Team> listTeams) {

                if(listTeams == null){
                    adapterListTeam.setListTeam(new ArrayList<Team>());
                }else {
                    listTeamLiveData.setValue(listTeams);
                    adapterListTeam.setListTeam(listTeams);
                    adapterListTeam.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }


        });
    }

    public void getListOtherTeam(){
        teamRepository.getListOtherTeam(new LoadListOtherTeamCallBack() {
            @Override
            public void onSuccess(List<Team> listTeams) {
                if(listTeams == null){
                    adapterListOtherTeam.setListTeam(new ArrayList<Team>());
                }else {
                    adapterListOtherTeam.setListTeam(listTeams);
                    adapterListOtherTeam.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public RecycleViewAdapterListTeamVertical getAdapterListTeam() {
        return adapterListTeam;
    }

    public RecycleViewAdapterListTeamVertical getAdapterListOtherTeam() {
        return adapterListOtherTeam;
    }

    public void createTeam(String name , String phone , String email){
        teamRepository.creatTeam(name, phone, email, new RegisterTeamCallBack() {
            @Override
            public void onSuccess() {
                resultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }
    public String getResultMessage() {
        return resultMessage;
    }



}
