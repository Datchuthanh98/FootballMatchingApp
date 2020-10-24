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

import com.example.myclub.data.enumeration.DataState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.session.SessionUser;
import com.example.myclub.databinding.FragmentListMatchBinding;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.viewModel.ListMatchViewModel;
import com.example.myclub.data.session.SessionStateData;

public class FragmentListMyMatch extends Fragment {
    private ListMatchViewModel viewModel ;
    private FragmentListMatchBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListMatchBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListMatchViewModel.class);
        RecycleViewAdapterListMatchVertical adapter = viewModel.getAdapterMyListMatch();
        adapter.setFm(getParentFragmentManager());

        binding.recycleViewListMatchVertical.setAdapter(viewModel.getAdapterMyListMatch());
        binding.recycleViewListMatchVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        observerLiveDate();
    }



    private void observerLiveDate() {
        SessionStateData.getInstance().getDatalistMatch().observe(getViewLifecycleOwner(), new Observer<DataState>() {
            @Override
            public void onChanged(DataState dataState) {
                viewModel.getListMyMatch(SessionUser.getInstance().getPlayerLiveData().getValue().getId());
            }
        });
    }



}
