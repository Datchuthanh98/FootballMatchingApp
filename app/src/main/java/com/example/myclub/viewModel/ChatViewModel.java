package com.example.myclub.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.model.Chat;
import com.example.myclub.model.Comment;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListChatVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatViewModel  extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MutableLiveData<List<Chat>> listChat = new MutableLiveData<>();
    private RecycleViewAdapterListChatVertical adapter = new RecycleViewAdapterListChatVertical();
    private String idTeam;

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public MutableLiveData<List<Chat>> getListChat() {
        return listChat;
    }

    public void setListChat(List<Chat> listChat) {
        this.listChat.setValue(listChat);
    }

    public RecycleViewAdapterListChatVertical getAdapter() {
        return adapter;
    }

    public void setAdapter(RecycleViewAdapterListChatVertical adapter) {
        this.adapter = adapter;
    }


    public  void loadChat(){

        teamRepository.loadChat(idTeam, new CallBack<List<Chat>, String>() {
            @Override
            public void onSuccess(List<Chat> chats) {
                if(chats == null){
                    adapter.setListChat(new ArrayList<Chat>());
                    listChat.setValue(new ArrayList<Chat>());
                    adapter.notifyDataSetChanged();
                }else {
                    listChat.setValue(chats);
                    adapter.setListChat(chats);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String s) {

            }
        });


    }



    public  void addComment(Map<String,Object> map){
        teamRepository.addChat(idTeam, map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("add chat", s);
            }

            @Override
            public void onFailure(String s) {
                Log.d("add chat", s);
            }
        });
    }
}
