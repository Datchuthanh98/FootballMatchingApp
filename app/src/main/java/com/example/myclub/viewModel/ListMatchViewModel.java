package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.LoadListMatchCallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Match;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListMatchVertical;

import java.util.ArrayList;
import java.util.List;

public class ListMatchViewModel extends ViewModel{
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListMatchVertical adapterListMatchVertical = new RecycleViewAdapterListMatchVertical();
    private MutableLiveData<List<Match>> listBookingFieldLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);

    public ListMatchViewModel(){
        getListMatch();
        adapterListMatchVertical.setListBookingViewModel(this);
    }

    public void  getListMatch(){
        matchRepository.getListMatch(new LoadListMatchCallBack() {
            @Override
            public void onSuccess(List<Match> matchList) {
                if(matchList == null){
                    listBookingFieldLiveData.setValue(new ArrayList<Match>());
                    adapterListMatchVertical.setListMatch(new ArrayList<Match>());
                    adapterListMatchVertical.notifyDataSetChanged();
                }else{
                    listBookingFieldLiveData.setValue(matchList);
                    adapterListMatchVertical.setListMatch(matchList);
                    adapterListMatchVertical.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(String message) {

            }
        });
    }



    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }

    public RecycleViewAdapterListMatchVertical getAdapterListMatch() {
        return adapterListMatchVertical;
    }

}
