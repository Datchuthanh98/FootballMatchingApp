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

import com.example.myclub.databinding.FragmentListBinding;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListTeamOtherViewModel;
import com.example.myclub.viewModel.ListTeamViewModel;
import com.example.myclub.session.SessionUser;

public class FragmentListOtherTeam extends Fragment {
    private ListTeamOtherViewModel listTeamViewModel;
    private SessionUser sessionUser = SessionUser.getInstance();
    private FragmentListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listTeamViewModel = new ViewModelProvider(this).get(ListTeamOtherViewModel.class);
        binding = FragmentListBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
    }

    private  void initComponent(){
        binding.btnCreateTeam.setVisibility(View.GONE);
        listTeamViewModel.getListOtherTeam(sessionUser.getPlayerLiveData().getValue().getId());
        binding.recycleViewListVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTeamVertical adapter = listTeamViewModel.getAdapterListOtherTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListVertical.setAdapter(adapter);

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTeamViewModel.searchField(binding.txtSearch.getText().toString());

            }
        });
    }
}
