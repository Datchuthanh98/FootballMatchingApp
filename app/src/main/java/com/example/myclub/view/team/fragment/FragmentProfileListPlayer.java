package com.example.myclub.view.team.fragment;

import android.content.Context;
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

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentListPlayerBinding;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.databinding.FragmentListBinding;
import com.example.myclub.viewModel.ListPlayerViewModel;
import com.example.myclub.session.SessionTeam;


public class FragmentProfileListPlayer extends Fragment {
    private ListPlayerViewModel listPlayerViewModel = ListPlayerViewModel.getInstance();
    private FragmentListPlayerBinding binding;
    private String idTeam;

    public FragmentProfileListPlayer(String idTeam) {
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
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }
    private  void initComponent(){

        binding.recycleViewListPlayerVertical.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void observeLiveData(final Context context) {
     SessionTeam.getInstance().getTeamLiveData().observe(getViewLifecycleOwner(), new Observer<Team>() {
         @Override
         public void onChanged(Team team) {
             if(team != null) {
                 listPlayerViewModel.getListPlayer(idTeam);
                 RecycleViewAdapterListPlayerVertical adapter = listPlayerViewModel.getAdapterListPlayer();
                 adapter.setFm(getParentFragmentManager());
                 binding.recycleViewListPlayerVertical.setAdapter(adapter);
             }
         }
     });
    }
}
