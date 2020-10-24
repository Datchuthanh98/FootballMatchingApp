package com.example.myclub.view.match.fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentListQueueTeamBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Team;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListQueueTeamVertical;
import com.example.myclub.view.team.fragment.FragmentListMyTeam;
import com.example.myclub.viewModel.ListQueueTeamViewModel;
import com.example.myclub.viewModel.ShareExecuteJoinMatchViewModel;
import com.example.myclub.viewModel.ShareSelectTeamViewModel;

import java.util.HashMap;
import java.util.Map;

public class FragmentListQueueTeam extends Fragment  {
    private ListQueueTeamViewModel viewModel;
    private ShareSelectTeamViewModel selectTeamViewModel;
    private ShareExecuteJoinMatchViewModel executeTeamViewModel;
    private FragmentListQueueTeamBinding binding;
    private  String idMatch;


    public FragmentListQueueTeam(String idMatch) {
        this.idMatch = idMatch;
    }

    public FragmentListQueueTeam( ) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListQueueTeamBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListQueueTeamViewModel.class);
        executeTeamViewModel = new ViewModelProvider(getActivity()).get(ShareExecuteJoinMatchViewModel.class);
        selectTeamViewModel = new ViewModelProvider(getActivity()).get(ShareSelectTeamViewModel.class);
        selectTeamViewModel.getSelectedTeam().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                if(team != null) {
                    Toast.makeText(getContext(), "comehere " + team.getName(), Toast.LENGTH_SHORT).show();
                    Map<String ,Object> map = new HashMap<>();
                    map.put("idMatch",idMatch);
                    map.put("idTeam",team.getId());
                    viewModel.addQueueTeam(map);
                    selectTeamViewModel.setSelectedTeam(null);
                }
            }
        });
        initComponent();
        observerLiveDate();
    }

    private void observerLiveDate() {
        viewModel.getResultAddQueueTeam().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
               viewModel.getListQueueTeam(idMatch);
            }
        });


    }

    private  void initComponent(){
        viewModel.getListQueueTeam(idMatch);
        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListQueueTeamVertical adapter = viewModel.getAdapterListQueueTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        adapter.setExecuteViewModel(executeTeamViewModel);
        binding.recycleViewListTeamVertical.setAdapter(viewModel.getAdapterListQueueTeam());


        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                Fragment selectTeam = new FragmentListMyTeam(false);
                activityHome.addFragment(selectTeam);
            }
        });
    }



}
