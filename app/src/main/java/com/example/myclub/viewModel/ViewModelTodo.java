package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Data.repository.TestRepository;
import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.View.Field.Adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.View.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.model.Todo;
import com.example.myclub.View.Team.Adapter.RecycleViewAdapterListPlayerVertical;

import java.util.ArrayList;
import java.util.List;

public class ViewModelTodo extends ViewModel {

    private TestRepository testRepository = TestRepository.getInstance();
    private RecycleViewAdapterListPlayerVertical adapterListPlayer = new RecycleViewAdapterListPlayerVertical();
    private RecycleViewAdapterListTeamVertical adapterListTeam = new RecycleViewAdapterListTeamVertical();
    private RecycleViewAdapterListFieldVertical adapterListField = new RecycleViewAdapterListFieldVertical();
    private MutableLiveData<List<Todo>> listTodoLiveData = new MutableLiveData<>();


    public ViewModelTodo() {
        testRepository.getAllTodo(new FirebaseLoadListTodo() {
            @Override
            public void FirebaseLoadListTodo(List<Todo> todos) {
                Log.d("getalltodo", "FirebaseLoadListTodo: ");
                if (todos == null) {
                    adapterListPlayer.setListTodo(new ArrayList<Todo>());
                    adapterListTeam.setListTodo(new ArrayList<Todo>());
                } else {
                    listTodoLiveData.setValue(todos);

                    adapterListPlayer.setListTodo(todos);
                    adapterListPlayer.notifyDataSetChanged();

                    adapterListTeam.setListTodo(todos);
                    adapterListTeam.notifyDataSetChanged();

                    adapterListField.setListTodo(todos);
                    adapterListField.notifyDataSetChanged();


                }
            }
        });
    }


    public RecycleViewAdapterListPlayerVertical getAdapterListPlayer() {
        return adapterListPlayer;
    }

    public RecycleViewAdapterListTeamVertical getAdapterListTeam() {
        return adapterListTeam;
    }

    public RecycleViewAdapterListFieldVertical getAdapterListField() {
        return adapterListField;
    }


}
