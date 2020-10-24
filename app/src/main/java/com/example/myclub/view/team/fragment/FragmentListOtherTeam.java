package com.example.myclub.view.team.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentListOtherTeamBinding;;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListMyTeamViewModel;
import com.example.myclub.data.session.SessionUser;

public class FragmentListOtherTeam extends Fragment {
    private ListMyTeamViewModel listMyTeamViewModel = ListMyTeamViewModel.getInstance();
    private SessionUser sessionUser = SessionUser.getInstance();
    private FragmentListOtherTeamBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listMyTeamViewModel = new ViewModelProvider(this).get(ListMyTeamViewModel.class);
        binding = FragmentListOtherTeamBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
    }

    private  void initComponent(){
        listMyTeamViewModel.getListOtherTeam(sessionUser.getPlayerLiveData().getValue().getId());
        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTeamVertical adapter = listMyTeamViewModel.getAdapterListOtherTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recycleViewListTeamVertical.setLayoutManager(mLayoutManager);
        binding.recycleViewListTeamVertical.setAdapter(listMyTeamViewModel.getAdapterListOtherTeam());
    }
}
