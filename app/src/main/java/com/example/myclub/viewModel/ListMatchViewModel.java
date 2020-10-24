package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;

import java.util.ArrayList;
import java.util.List;

public class ListMatchViewModel extends ViewModel{
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListMatchVertical adapterListMatchVertical = new RecycleViewAdapterListMatchVertical();
    private RecycleViewAdapterListMatchVertical adapterListMyMatchVertical = new RecycleViewAdapterListMatchVertical();




    public ListMatchViewModel(){
        getListMatch();
        adapterListMatchVertical.setListBookingViewModel(this);
    }

    public void  getListMatch(){
        matchRepository.getListMatch(new CallBack<List<Match>, String>() {
            @Override
            public void onSuccess(List<Match> matchList) {
                if(matchList == null){
                    adapterListMatchVertical.setListMatch(new ArrayList<Match>());
                    adapterListMatchVertical.notifyDataSetChanged();
                }else{
                    adapterListMatchVertical.setListMatch(matchList);
                    adapterListMatchVertical.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void  getListMyMatch(String idPlayer){
        matchRepository.getListMyMatch(idPlayer,new CallBack<List<Match>, String>() {
            @Override
            public void onSuccess(List<Match> matchList) {
                if(matchList == null){
                    adapterListMyMatchVertical.setListMatch(new ArrayList<Match>());
                    adapterListMyMatchVertical.notifyDataSetChanged();
                }else{
                    adapterListMyMatchVertical.setListMatch(matchList);
                    adapterListMyMatchVertical.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(String message) {

            }
        });
    }

    public RecycleViewAdapterListMatchVertical getAdapterListMatch() {
        return adapterListMatchVertical;
    }

    public RecycleViewAdapterListMatchVertical getAdapterMyListMatch() {
        return adapterListMyMatchVertical;
    }


}
