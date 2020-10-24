package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListCommentVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListCommentViewModel extends ViewModel {
    private MutableLiveData<List<Comment>> listCommentLiveData = new MutableLiveData<>(null);
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListCommentVertical adapterListComment = new RecycleViewAdapterListCommentVertical();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    public String teamLoadMessage;
    private MutableLiveData<Result> addCommentResult = new MutableLiveData<>(null);


    public ListCommentViewModel(List<Comment> listComment) {
        listCommentLiveData.setValue(listComment);
    }

    public ListCommentViewModel() {
    }

    public MutableLiveData<List<Comment>> getListQueueTeam() {
        return listCommentLiveData;
    }


    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public RecycleViewAdapterListCommentVertical getAdapterListComment() {
        return adapterListComment;
    }

    public MutableLiveData<Result> getAddCommentResult() {
        return addCommentResult;
    }

    public  void getListQueueTeam(String idMatch){
        matchRepository.getlistComment(idMatch, new CallBack<List<Comment>, String>() {
            @Override
            public void onSuccess(List<Comment> listComments) {
                if(listComments == null){
                    adapterListComment.setListComment(new ArrayList<Comment>());
                }else {
                    listCommentLiveData.setValue(listComments);
                    adapterListComment.setListComment(listComments);
                    adapterListComment.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String s) {
                teamLoadMessage = s ;
            }
        });

    }

    public  void addComment(Map<String,Object> map){
        matchRepository.addQueueTeam(map, new CallBack<String, String>() {
            @Override
            public void onSuccess(String s) {
                addCommentResult.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String s) {
                addCommentResult.setValue(Result.FAILURE);
            }
        });
    }


}
