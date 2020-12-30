package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Evaluate;
import com.example.myclub.model.Player;
import com.example.myclub.view.team.adapter.RecycleViewAdapterLisEvaluateVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListEvaluateViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private RecycleViewAdapterLisEvaluateVertical adapterListComment = new RecycleViewAdapterLisEvaluateVertical();
    public String idTeam;


    public  void getListEvaluate(String idTeam){
        teamRepository.getListEvaluate(idTeam, new CallBack<List<Evaluate>, String>() {
            @Override
            public void onSuccess(List<Evaluate> evaluates) {
                if(evaluates == null){
                    adapterListComment.setListEvaluate(new ArrayList<Evaluate>());
                }else {

                    adapterListComment.setListEvaluate(evaluates);
                    adapterListComment.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }

    public  void addEvaluate(Map<String,Object> map){
        teamRepository.addEvaluate(idTeam,map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                getListEvaluate(idTeam);
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }
    public RecycleViewAdapterLisEvaluateVertical getAdapterListEvaluate() {
        return adapterListComment;
    }


}
