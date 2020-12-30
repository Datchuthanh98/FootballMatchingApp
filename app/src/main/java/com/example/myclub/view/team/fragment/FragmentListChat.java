package com.example.myclub.view.team.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentListChatBinding;
import com.example.myclub.session.SessionUser;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListChatVertical;
import com.example.myclub.viewModel.ChatViewModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentListChat extends Fragment {
    private ChatViewModel viewModel;
    private FragmentListChatBinding binding;
    private  String idTeam;

    public FragmentListChat(String idTeam) {
        this.idTeam = idTeam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListChatBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ChatViewModel.class);
        initComponent();
    }


    private  void initComponent(){

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });


        viewModel.setIdTeam(this.idTeam);
        viewModel.loadChat();
        binding.recycleViewListCommentVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListChatVertical adapter = viewModel.getAdapter();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListCommentVertical.setAdapter(adapter);
        binding.recycleViewListCommentVertical.scrollToPosition(viewModel.getListChat().getValue().size()-1);


     binding.btnComment.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
              Map<String, Object> map = new HashMap<>();
              map.put("idPlayer", SessionUser.getInstance().getPlayerLiveData().getValue().getId());
              map.put("message",binding.txtCommemt.getText().toString());
              map.put("time_create",Calendar.getInstance().getTimeInMillis());
              viewModel.addComment(map);
         }
     });
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
    }




}
