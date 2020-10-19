package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.model.Field;

public class ProfileFieldViewModel extends ViewModel {
    private MutableLiveData<Field> fieldLiveData = new MutableLiveData<>();

    public void setField(Field field) {
        fieldLiveData.setValue(field);
    }

    public LiveData<Field> getFieldLiveData(){
        return fieldLiveData;
    }
}
