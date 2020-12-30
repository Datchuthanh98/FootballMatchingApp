package com.example.myclub.view.match.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.session.SessionUser;
import com.example.myclub.databinding.FragmentListCommentBinding;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListCommentVertical;
import com.example.myclub.viewModel.ProfileMatchViewModel;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentListComment extends Fragment {
    private  ProfileMatchViewModel viewModel;
    private FragmentListCommentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListCommentBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileMatchViewModel.class);
        initComponent();
    }


    private  void initComponent(){
        viewModel.getListQueueTeam();
        binding.recycleViewListCommentVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListCommentVertical adapter = viewModel.getAdapterListComment();
//        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListCommentVertical.setAdapter(viewModel.getAdapterListComment());


     binding.btnComment.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
              Map<String, Object> map = new HashMap<>();
              map.put("player", SessionUser.getInstance().getPlayerLiveData().getValue().getId());
              map.put("comment",binding.txtCommemt.getText().toString());
              map.put("time_create",Calendar.getInstance().getTimeInMillis());
              viewModel.addComment(map);
         }
     });
    }






}
