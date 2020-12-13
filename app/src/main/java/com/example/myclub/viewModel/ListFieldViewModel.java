package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.model.Field;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListFieldVertical;


import java.util.ArrayList;
import java.util.List;

public class ListFieldViewModel extends ViewModel {

    private FieldRepository fieldRepository = FieldRepository.getInstance();

    private RecycleViewAdapterListFieldVertical adapterListField = new RecycleViewAdapterListFieldVertical();
    private List<Field> listField = new ArrayList<>();

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
        fieldRepository.getListField(new CallBack<List<Field>, String>() {
            @Override
            public void onSuccess(List<Field> fields) {
                if(fields == null){
                    listField  = new ArrayList<Field>();
                    adapterListField.setListField(new ArrayList<Field>());
                    adapterListField.notifyDataSetChanged();
                }else{
                    listField =fields;
                    adapterListField.setListField(fields);
                    adapterListField.notifyDataSetChanged();
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

}
