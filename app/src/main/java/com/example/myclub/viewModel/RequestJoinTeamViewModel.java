package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.DataState;
import com.example.myclub.data.repository.RequestJoinTeamRepository;
import com.example.myclub.data.session.SessionStateData;
import com.example.myclub.model.RequestJoinTeam;

import java.util.Map;

public class RequestJoinTeamViewModel {
    private RequestJoinTeamRepository requestJoinTeamRepository = RequestJoinTeamRepository.getInstance();
    private static RequestJoinTeamViewModel instance;
    private MutableLiveData<Boolean> stateRequestJoinTeam = new MutableLiveData<>(false);
    private  String key ;

    private String resultMessage = null;

    public String getKey(){
        return key;
    }

    public static RequestJoinTeamViewModel getInstance() {
        if (instance == null) {
            instance = new RequestJoinTeamViewModel();
        }
        return instance;
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

    public void acceptJoinTeam(Map<String, Object> decideJoin){
        requestJoinTeamRepository.acceptJoinTeam(decideJoin, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                stateRequestJoinTeam.setValue(Boolean.FALSE);
                SessionStateData.getInstance().setDatalistPlayerByTeam(DataState.NEW);
                SessionStateData.getInstance().setDatalistRequestByTeam(DataState.NEW);
            }

            @Override
            public void onFailure(String s) {
                stateRequestJoinTeam.setValue(Boolean.TRUE);
            }
        });
    }


    public void declineJoinTeam(Map<String, Object> decideJoin){
        requestJoinTeamRepository.declineJoinTeam(decideJoin, new CallBack<String, String>() {
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
