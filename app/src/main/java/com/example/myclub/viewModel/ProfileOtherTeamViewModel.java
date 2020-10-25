package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.DataState;
import com.example.myclub.data.repository.RequestJoinTeamRepository;
import com.example.myclub.model.RequestJoinTeam;
import com.example.myclub.session.SessionStateData;

import java.util.Map;

public class ProfileOtherTeamViewModel extends ViewModel {
    private RequestJoinTeamRepository requestJoinTeamRepository = RequestJoinTeamRepository.getInstance();
    private static ProfileOtherTeamViewModel instance;
    private MutableLiveData<Boolean> stateRequestJoinTeam = new MutableLiveData<>(false);
    private  String key ;

    private String resultMessage = null;

    public String getKey(){
        return key;
    }


    public  MutableLiveData<Boolean> getStateRequestJoinTeam(){
       return stateRequestJoinTeam;
    }

    public void addRequestJoinTeam(Map<String, Object> requestJoin){
        requestJoinTeamRepository.addRequestJoinTeam(requestJoin, new CallBack<String, String>() {
            @Override
            public void onSuccess(String getKey) {
                key = getKey;
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }
            @Override
            public void onFailure(String e) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
            }
        });
    }

    public void cancelRequestJoinTeam(String key){
        requestJoinTeamRepository.cancelRequestJoinTeam(key, new CallBack<String, String>() {
            @Override
            public void onSuccess(String sucess) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
            }

            @Override
            public void onFailure(String e) {
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }
        });
    }

    public void getStateJoinTeam(final Map<String, Object> requestJoin){
        requestJoinTeamRepository.getStateJoinTeam(requestJoin, new CallBack<RequestJoinTeam, String>() {
            @Override
            public void onSuccess(RequestJoinTeam requestJoinTeam) {
                if(requestJoinTeam == null){
                    stateRequestJoinTeam.setValue(false);
                }else{
                    stateRequestJoinTeam.setValue(true);
                    key = requestJoinTeam.getId();
                }
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }




}
