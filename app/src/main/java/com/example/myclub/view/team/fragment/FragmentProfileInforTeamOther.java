package com.example.myclub.view.team.fragment;

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

import com.example.myclub.databinding.FragmentMatchInformationBinding;
import com.example.myclub.databinding.FragmentProfileTeamBasicBinding;
import com.example.myclub.databinding.FragmentProfileTeamOtherBasicBinding;
import com.example.myclub.model.Match;
import com.example.myclub.model.Team;
import com.example.myclub.viewModel.ProfileMatchViewModel;
import com.example.myclub.viewModel.ProfileOtherTeamViewModel;

public class FragmentProfileInforTeamOther extends Fragment {
    private FragmentProfileTeamOtherBasicBinding binding;
    private ProfileOtherTeamViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileTeamOtherBasicBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ProfileOtherTeamViewModel.class);
        super.onViewCreated(view, savedInstanceState);
        viewModel.getMatchMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                if(team !=null) {
                    Toast.makeText(getContext(),"!23"+team.getName(),Toast.LENGTH_SHORT).show();
                    binding.setTeam(team);
                }
            }
        });






    }
}
