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
import com.example.myclub.databinding.FragmentListTeamBinding;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListTeamViewModel;
import com.example.myclub.session.SessionUser;

public class FragmentListOtherTeam extends Fragment {
    private ListTeamViewModel listTeamViewModel;
    private SessionUser sessionUser = SessionUser.getInstance();
    private FragmentListTeamBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listTeamViewModel = new ViewModelProvider(this).get(ListTeamViewModel.class);
        binding = FragmentListTeamBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
    }

    private  void initComponent(){
        listTeamViewModel.getListOtherTeam(sessionUser.getPlayerLiveData().getValue().getId());
        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTeamVertical adapter = listTeamViewModel.getAdapterListOtherTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTeamVertical.setAdapter(listTeamViewModel.getAdapterListOtherTeam());
    }
}
