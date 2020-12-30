package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.model.Field;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListFieldVertical;


import java.util.ArrayList;
import java.util.List;

public class ListFieldViewModel extends ViewModel {

    private FieldRepository fieldRepository = FieldRepository.getInstance();
    private RecycleViewAdapterListFieldVertical adapterListField = new RecycleViewAdapterListFieldVertical();
    private List<Field> listField = new ArrayList<>();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    private MutableLiveData<Status> statusData = new MutableLiveData<>();
    private MutableLiveData<Result> result = new MutableLiveData<>();

    public void  searchField(String nameField){
        List<Field> listSearch = new ArrayList<>();
        for(int i = 0 ;i < listField.size(); i++) {
            if(listField.get(i).getName().contains(nameField)){
                listSearch.add(listField.get(i));
            }
        }
        adapterListField.setListField(listSearch);
        adapterListField.notifyDataSetChanged();

    }

    public ListFieldViewModel() {
        teamLoadState.setValue(LoadingState.INIT);
        fieldRepository.getListField(new CallBack<List<Field>, String>() {
            @Override
            public void onSuccess(List<Field> fields) {
                teamLoadState.setValue(LoadingState.LOADED);
                if(fields == null){
                    statusData.setValue(Status.NO_DATA);
                    listField  = new ArrayList<Field>();
                    adapterListField.setListField(new ArrayList<Field>());
                    adapterListField.notifyDataSetChanged();
                }else{
                    listField =fields;
                    adapterListField.setListField(fields);
                    adapterListField.notifyDataSetChanged();
                    statusData.setValue(Status.EXIST_DATA);
                }

            }

            @Override
            public void onFailure(String s) {

            }

        });
    }




    public RecycleViewAdapterListFieldVertical getAdapterListField() {
        return adapterListField;
    }



    public MutableLiveData<Result> getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result.setValue(result);
    }

    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public void setTeamLoadState(MutableLiveData<LoadingState> teamLoadState) {
        this.teamLoadState = teamLoadState;
    }

    public MutableLiveData<Status> getStatusData() {
        return statusData;
    }

    public void setStatusData(MutableLiveData<Status> statusData) {
        this.statusData = statusData;
    }

    public void setResult(MutableLiveData<Result> result) {
        this.result = result;
    }
}
