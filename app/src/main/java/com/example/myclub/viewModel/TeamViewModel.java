package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.RegisterTeamCallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.TeamRepository;

public class TeamViewModel extends ViewModel {
    private TeamRepository teamRepository= TeamRepository.getInstance();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void createTeam(String name , String phone , String email){
         teamRepository.creatTeam(name, phone, email, new RegisterTeamCallBack() {
             @Override
             public void onSuccess() {
                 Log.d("checkteam", "onSuccess: .");
                 resultLiveData.setValue(Result.SUCCESS);
             }

             @Override
             public void onFailure(String message) {
                 resultMessage = message;
                 resultLiveData.setValue(Result.FAILURE);
             }
         });
    }





}
