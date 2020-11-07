package com.example.myclub.view.match.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentEditPlayerInforBinding;
import com.example.myclub.databinding.FragmentMatchInformationBinding;
import com.example.myclub.model.Match;
import com.example.myclub.viewModel.ProfileMatchViewModel;
import com.example.myclub.viewModel.ShareSelectTeamViewModel;


public class FragmentInformationMatch extends Fragment {


    FragmentMatchInformationBinding binding;
    ProfileMatchViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMatchInformationBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ProfileMatchViewModel.class);
        super.onViewCreated(view, savedInstanceState);
        viewModel.getMatchMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                binding.setMatch(viewModel.getMatchMutableLiveData().getValue());
            }
        });






    }
}
