package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;

import java.util.ArrayList;
import java.util.List;

public class ListMyMatchViewModel extends ViewModel {
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListMatchVertical adapterListMyMatchVertical = new RecycleViewAdapterListMatchVertical();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);


    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public void getListMyMatch(String idPlayer) {
        teamLoadState.setValue(LoadingState.LOADING);
        matchRepository.getListMyMatch(idPlayer, new CallBack<List<Match>, String>() {
            @Override
            public void onSuccess(List<Match> matchList) {
                teamLoadState.setValue(LoadingState.LOADED);
                if (matchList == null) {
                    adapterListMyMatchVertical.setListMatch(new ArrayList<Match>());
                    adapterListMyMatchVertical.notifyDataSetChanged();
                } else {
                    adapterListMyMatchVertical.setListMatch(matchList);
                    adapterListMyMatchVertical.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


    public RecycleViewAdapterListMatchVertical getAdapterMyListMatch() {
        return adapterListMyMatchVertical;
    }


}
