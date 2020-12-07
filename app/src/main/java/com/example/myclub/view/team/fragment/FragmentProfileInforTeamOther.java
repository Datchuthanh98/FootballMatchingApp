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
import com.example.myclub.databinding.FragmentProfileTeamBasicBinding;
import com.example.myclub.model.Team;
import com.example.myclub.viewModel.ProfileMatchViewModel;
import com.example.myclub.viewModel.ProfileOtherTeamViewModel;

public class FragmentProfileInforTeamOther extends Fragment {
    private FragmentProfileTeamBasicBinding binding;
    private ProfileOtherTeamViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileTeamBasicBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ProfileOtherTeamViewModel.class);
        super.onViewCreated(view, savedInstanceState);

    }
}
