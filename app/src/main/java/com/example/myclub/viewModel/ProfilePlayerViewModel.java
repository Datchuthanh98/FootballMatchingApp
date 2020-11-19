package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.RequestJoinTeamRepository;


import java.util.Map;

public class ProfilePlayerViewModel extends ViewModel {
    private RequestJoinTeamRepository requestJoinTeamRepository = RequestJoinTeamRepository.getInstance();
    private MutableLiveData<Boolean> stateRequestJoinTeam = new MutableLiveData<>(false);
    public  MutableLiveData<Boolean> getStateRequestJoinTeam(){
       return stateRequestJoinTeam;
    }


    public void getStateJoinTeam(final Map<String, Object> map){
        requestJoinTeamRepository.getStateJoinTeam(map.get("team").toString(),map.get("player").toString(), new CallBack<Boolean, String>() {
            @Override
            public void onSuccess(Boolean requestJoinTeam) {
                if(requestJoinTeam == false){
                    stateRequestJoinTeam.setValue(false);
                }else{
                    stateRequestJoinTeam.setValue(true);

                }
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }

    public void acceptJoinTeam(Map<String, Object> map){
        requestJoinTeamRepository.acceptJoinTeam(map.get("team").toString(),map.get("player").toString(), new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
            }

            @Override
            public void onFailure(String s) {
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }
        });
    }


    public void declineJoinTeam(Map<String, Object> map){
        requestJoinTeamRepository.declineJoinTeam(map.get("team").toString(),map.get("player").toString(), new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
            }

            @Override
            public void onFailure(String e) {
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }
        });
    }


}
