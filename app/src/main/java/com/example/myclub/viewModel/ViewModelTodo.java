package com.example.myclub.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.data.repository.TestRepository;
import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.view.Field.Adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamHorizontal;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.model.Todo;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListTimeVertical;

import java.util.ArrayList;
import java.util.List;

public class ViewModelTodo extends ViewModel {

    private TestRepository testRepository = TestRepository.getInstance();
    private RecycleViewAdapterListPlayerVertical adapterListPlayer = new RecycleViewAdapterListPlayerVertical();
    private RecycleViewAdapterListTeamVertical adapterListTeam = new RecycleViewAdapterListTeamVertical();
    private RecycleViewAdapterListTeamHorizontal adapterListTeamHorizontal = new RecycleViewAdapterListTeamHorizontal();
    private RecycleViewAdapterListFieldVertical adapterListField = new RecycleViewAdapterListFieldVertical();
    private RecycleViewAdapterListMatchVertical adapterListMatch = new RecycleViewAdapterListMatchVertical();
    private RecycleViewAdapterListTimeVertical adapterListTimeVertical = new RecycleViewAdapterListTimeVertical();
    private MutableLiveData<List<Todo>> listTodoLiveData = new MutableLiveData<>();


    public ViewModelTodo() {
        testRepository.getAllTodo(new FirebaseLoadListTodo() {
            @Override
            public void FirebaseLoadListTodo(List<Todo> todos) {
                Log.d("getalltodo", "FirebaseLoadListTodo: ");

                    listTodoLiveData.setValue(todos);





                    adapterListField.setListTodo(todos);
                    adapterListField.notifyDataSetChanged();

                    adapterListMatch.setListTodo(todos);
                    adapterListMatch.notifyDataSetChanged();

                    adapterListTeamHorizontal.setListTodo(todos);
                    adapterListTeamHorizontal.notifyDataSetChanged();

                    adapterListTimeVertical.setListTodo(todos);
                    adapterListTimeVertical.notifyDataSetChanged();


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

    public RecycleViewAdapterListMatchVertical getAdapterListMatch(){
        return  adapterListMatch;
    }

    public RecycleViewAdapterListTeamHorizontal getAdapterListTeamHorizontal(){
        return  adapterListTeamHorizontal;
    }

    public RecycleViewAdapterListTimeVertical getAdapterListTimeVertical(){
        return  adapterListTimeVertical;
    }


}
