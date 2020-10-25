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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.data.enumeration.DataState;
import com.example.myclub.databinding.FragmentListPlayerBinding;
import com.example.myclub.session.SessionStateData;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerRequestVertical;
import com.example.myclub.viewModel.ListPlayerViewModel;


public class FragmentProfileListPlayerRequest extends Fragment {
    private ListPlayerViewModel listPlayerViewModel = ListPlayerViewModel.getInstance();
    private FragmentListPlayerBinding binding;
    private String idTeam;

    public FragmentProfileListPlayerRequest(String idTeam) {
        this.idTeam = idTeam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listPlayerViewModel = new ViewModelProvider(this).get(ListPlayerViewModel.class);
        binding = FragmentListPlayerBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData(view.getContext());
    }
    private  void initComponent(){
        listPlayerViewModel.getListPlayerRequest(idTeam);
        binding.recycleViewListPlayerVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListPlayerRequestVertical adapter = listPlayerViewModel.getAdapterListPlayerRequest();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListPlayerVertical.setAdapter(listPlayerViewModel.getAdapterListPlayerRequest());
    }

    private void observeLiveData(final Context context) {
        SessionStateData.getInstance().getDatalistRequestByTeam().observe(getViewLifecycleOwner(), new Observer<DataState>() {
            @Override
            public void onChanged(DataState dataState) {
                listPlayerViewModel.getListPlayerRequest(idTeam);
            }
        });
    }
}
