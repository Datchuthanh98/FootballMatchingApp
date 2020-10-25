package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.TimeGame;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListTimeVertical;

import java.util.ArrayList;
import java.util.List;

public class ProfileFieldViewModel extends ViewModel {
    private MutableLiveData<Field> fieldLiveData = new MutableLiveData<>();
    private RecycleViewAdapterListTimeVertical adapterListTimeVertical = new RecycleViewAdapterListTimeVertical();
    private FieldRepository fieldRepository = FieldRepository.getInstance();
    public void setField(Field field) {
        fieldLiveData.setValue(field);
    }

    public LiveData<Field> getFieldLiveData(){
        return fieldLiveData;
    }

    public void getlistTime(){
        fieldRepository.getListTimeByField(fieldLiveData.getValue().getId(), new CallBack<List<TimeGame>, String>() {
            @Override
            public void onSuccess(List<TimeGame> listTimeGames) {
                if(listTimeGames == null){
                    adapterListTimeVertical.setListTime(new ArrayList<TimeGame>());
                    adapterListTimeVertical.notifyDataSetChanged();
                }else {
                    adapterListTimeVertical.setListTime(listTimeGames);
                    adapterListTimeVertical.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public RecycleViewAdapterListTimeVertical getAdapterListTimeVertical() {
        return adapterListTimeVertical;
    }


}
