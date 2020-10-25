package com.example.myclub.view.match.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.session.SessionUser;
import com.example.myclub.databinding.FragmentListCommentBinding;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListCommentVertical;
import com.example.myclub.viewModel.ListCommentViewModel;

import java.util.HashMap;
import java.util.Map;

public class FragmentListComment extends Fragment {
    private ListCommentViewModel viewModel;
    private FragmentListCommentBinding binding;
    private  String idMatch;

    public FragmentListComment(String idMatch) {
        this.idMatch = idMatch;
    }

    public FragmentListComment( ) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListCommentBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListCommentViewModel.class);
        initComponent();
        observerLiveDate();
    }

    private void observerLiveDate() {
       viewModel.getAddCommentResult().observe(getViewLifecycleOwner(), new Observer<Result>() {
           @Override
           public void onChanged(Result result) {
               if(result == Result.SUCCESS){
                   viewModel.getListQueueTeam(idMatch);
               }
           }
       });
    }

    private  void initComponent(){
        viewModel.getListQueueTeam(idMatch);
        binding.recycleViewListCommentVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListCommentVertical adapter = viewModel.getAdapterListComment();
//        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListCommentVertical.setAdapter(viewModel.getAdapterListComment());


     binding.btnComment.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
              Map<String, Object> map = new HashMap<>();
              map.put("idPlayer", SessionUser.getInstance().getPlayerLiveData().getValue().getId());
              map.put("idMatch",idMatch);
              map.put("comment","123213");
              viewModel.addComment(map);
         }
     });
    }






}
