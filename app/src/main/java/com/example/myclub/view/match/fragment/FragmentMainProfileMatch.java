package com.example.myclub.view.match.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentMainInformationMatchBinding;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.adapter.AdapterFragmentInformationMatch;
import com.example.myclub.view.player.Adapter.AdapterFragmentProfile;
import com.example.myclub.viewModel.ListMatchViewModel;
import com.example.myclub.viewModel.ProfileMatchViewModel;
import com.example.myclub.viewModel.ShareExecuteJoinMatchViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class FragmentMainProfileMatch extends Fragment {
    private FragmentMainInformationMatchBinding binding;
    private ProfileMatchViewModel viewModel;
    private ShareExecuteJoinMatchViewModel executeViewModel;
    private String idMatch;

    public FragmentMainProfileMatch(String idMatch)  {
        this.idMatch = idMatch;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_information_match, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileMatchViewModel.class);
        executeViewModel = new ViewModelProvider(getActivity()).get(ShareExecuteJoinMatchViewModel.class);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        FragmentManager manager = getParentFragmentManager();
        AdapterFragmentInformationMatch adapter = new AdapterFragmentInformationMatch(getChildFragmentManager(), AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,idMatch);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        initComponent();
        observerLiveData();

    }

    private void initComponent(){
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        viewModel.getInformationMatch(idMatch);
    }

    private void observerLiveData(){
        viewModel.getMatchMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                binding.setMatch(match);
            }
        });

        viewModel.getTeamLoadState().observe(getViewLifecycleOwner(), new Observer<LoadingState>() {
            @Override
            public void onChanged(LoadingState loadingState) {
                if (loadingState == null) return;
                if (loadingState == LoadingState.INIT) {
                    binding.layoutLoading.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADING) {
                    binding.layoutLoading.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADED) {
                    binding.layoutLoading.setVisibility(View.GONE);
                }
            }
        });

        executeViewModel.getIdTeam().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String idTeam) {
                if(idTeam != null){


                    Map<String,Object> map = new HashMap<>();
                    map.put("idTeamAway",idTeam);
                    String idBooking = viewModel.getMatchMutableLiveData().getValue().getIdBooking().getId();
                    viewModel.acceptTeam(idBooking,map);
                    executeViewModel.setIdTeam(null);
                }
            }
        });

        viewModel.getResultExecuteTeam().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result == Result.SUCCESS){
                    viewModel.getInformationMatch(idMatch);
                    Toast.makeText(getContext(),"update choose team",Toast.LENGTH_SHORT).show();
                }else{
                    //Todo
                }
            }
        });

    }


    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }
}
