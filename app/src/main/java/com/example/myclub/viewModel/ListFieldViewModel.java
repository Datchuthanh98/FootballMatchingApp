package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.model.Field;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListFieldVertical;


import java.util.List;

public class ListFieldViewModel extends ViewModel {

    private FieldRepository fieldRepository = FieldRepository.getInstance();

    private RecycleViewAdapterListFieldVertical adapterListField = new RecycleViewAdapterListFieldVertical();
    private MutableLiveData<List<Field>> listFieldLiveData = new MutableLiveData<>();


    public ListFieldViewModel() {
        fieldRepository.getListField(new CallBack<List<Field>, String>() {
            @Override
            public void onSuccess(List<Field> fields) {
                listFieldLiveData.setValue(fields);
                adapterListField.setListField(fields);
                adapterListField.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String s) {

            }

        });
    }



    public RecycleViewAdapterListFieldVertical getAdapterListField() {
        return adapterListField;
    }

}
