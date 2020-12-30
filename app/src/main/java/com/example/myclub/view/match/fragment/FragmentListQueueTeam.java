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

import com.example.myclub.data.enumeration.State;
import com.example.myclub.databinding.FragmentListQueueTeamBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Team;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListQueueTeamVertical;
import com.example.myclub.view.team.fragment.FragmentListMyTeam;
import com.example.myclub.viewModel.ProfileMatchViewModel;
import com.example.myclub.viewModel.ShareSelectTeamViewModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentListQueueTeam extends Fragment  {
    private ProfileMatchViewModel viewModel;
    private ShareSelectTeamViewModel selectTeamViewModel;
    private FragmentListQueueTeamBinding binding;

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
        viewModel = new ViewModelProvider(getActivity()).get(ProfileMatchViewModel.class);
        selectTeamViewModel = new ViewModelProvider(getActivity()).get(ShareSelectTeamViewModel.class);
        initComponent();
        observerLiveDate();
    }

    private void observerLiveDate() {
        viewModel.getTeamAwayStatus().observe(getViewLifecycleOwner(), new Observer<State>() {
            @Override
            public void onChanged(State state) {
                if(state == State.DONE){
                    binding.viewWait.setVisibility(View.GONE);
                    binding.viewDone.setVisibility(View.VISIBLE);
                }else{
                    RecycleViewAdapterListQueueTeamVertical adapter = viewModel.getAdapterListQueueTeam();
                    adapter.fragment = getTargetFragment();
                    adapter.setFm(getParentFragmentManager());
                    adapter.setProfileMatchViewModel(viewModel);
                    binding.recycleViewListTeamVertical.setAdapter(adapter);
                }
            }
        });
    }

    private  void initComponent(){
        selectTeamViewModel.getSelectedTeam().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                if(team != null) {
                    Map<String ,Object> map = new HashMap<>();
                    map.put("team",team.getId());
                    map.put("time_create",Calendar.getInstance().getTimeInMillis());
                    viewModel.addQueueTeam(map);
                    selectTeamViewModel.setSelectedTeam(null);
                }
            }
        });


        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));



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
