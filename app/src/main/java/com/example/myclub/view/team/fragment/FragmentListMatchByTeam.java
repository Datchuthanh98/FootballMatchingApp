package com.example.myclub.view.team.adapter;

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

import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.databinding.FragmentListCallendarBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.view.field.fragment.FragmentAddBooking;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.viewModel.ListMatchByTeamViewModel;


public class FragmentListMatchByTeam extends Fragment {
    private ListMatchByTeamViewModel viewModel ;
    private FragmentListCallendarBinding binding;
    private String idTeam;

    public FragmentListMatchByTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListCallendarBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListMatchByTeamViewModel.class);
        initComponent();
        observerLiveDate();

    }

    private  void initComponent(){
        viewModel.getListMatchByTeam(idTeam);
        RecycleViewAdapterListMatchVertical adapter = viewModel.getAdapterListMatch();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListVertical.setAdapter(adapter);
        binding.recycleViewListVertical.setLayoutManager(new LinearLayoutManager(getContext()));





    }

    private void observerLiveDate() {
      viewModel.getTeamLoadState().observe(getViewLifecycleOwner(), new Observer<LoadingState>() {
          @Override
          public void onChanged(LoadingState loadingState) {
              if (loadingState == null) return;
              if (loadingState == LoadingState.INIT) {
                  binding.loadingLayout.setVisibility(View.VISIBLE);
              } else if (loadingState == LoadingState.LOADING) {
                  binding.loadingLayout.setVisibility(View.VISIBLE);
              } else if (loadingState == LoadingState.LOADED) {
                  binding.loadingLayout.setVisibility(View.GONE);
              }
          }
      });

      viewModel.getStatusData().observe(getViewLifecycleOwner(), new Observer<Status>() {
          @Override
          public void onChanged(Status status) {
               if(status == Status.NO_DATA){
                   binding.viewNoData.setVisibility(View.VISIBLE);
               }else if(status == status.EXIST_DATA){
                   binding.viewNoData.setVisibility(View.GONE);
               }
          }
      });


    }




}
