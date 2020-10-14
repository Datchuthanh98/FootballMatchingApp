package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.Interface.LoadListField;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.data.repository.TestRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.Todo;
import com.example.myclub.view.Field.Adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamHorizontal;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;

import java.util.List;

public class ListFieldViewModel extends ViewModel {

    private FieldRepository fieldRepository = FieldRepository.getInstance();

    private RecycleViewAdapterListFieldVertical adapterListField = new RecycleViewAdapterListFieldVertical();
    private MutableLiveData<List<Field>> listFieldLiveData = new MutableLiveData<>();


    public ListFieldViewModel() {
        fieldRepository.getListField(new LoadListField() {
            @Override
            public void FirebaseLoadListTodo(List<Field> fields) {
                listFieldLiveData.setValue(fields);
                adapterListField.setListField(fields);
                adapterListField.notifyDataSetChanged();
            }
        });
    }



    public RecycleViewAdapterListFieldVertical getAdapterListField() {
        return adapterListField;
    }

}
