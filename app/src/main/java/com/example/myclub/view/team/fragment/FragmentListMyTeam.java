package com.example.myclub.view.team.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.data.enumeration.DataState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.session.SessionStateData;
import com.example.myclub.databinding.FragmentListMyTeamBinding;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListMyTeamViewModel;
import com.example.myclub.data.session.SessionUser;
import com.example.myclub.viewModel.ShareSelectTeamViewModel;

public class FragmentListMyTeam extends Fragment {
    private ListMyTeamViewModel listMyTeamViewModel = ListMyTeamViewModel.getInstance();
    private ShareSelectTeamViewModel selectTeamViewModel ;
    private SessionUser sessionUser = SessionUser.getInstance();
    private FragmentListMyTeamBinding binding;
    public boolean isShow = true ;


    public FragmentListMyTeam( boolean isShow) {
        this.isShow = isShow;
    }

    public FragmentListMyTeam( ) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listMyTeamViewModel = new ViewModelProvider(this).get(ListMyTeamViewModel.class);

        binding = FragmentListMyTeamBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectTeamViewModel = new ViewModelProvider(getActivity()).get(ShareSelectTeamViewModel.class);
        initComponent();
        observerLiveDate();
    }

    private void observerLiveDate() {
        SessionStateData.getInstance().getDatalistMyTeam().observe(getViewLifecycleOwner(), new Observer<DataState>() {
            @Override
            public void onChanged(DataState dataState) {
                listMyTeamViewModel.getListTeam(sessionUser.getPlayerLiveData().getValue().getId());
            }
        });
    }

    private  void initComponent(){
        listMyTeamViewModel.getListTeam(sessionUser.getPlayerLiveData().getValue().getId());
        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListTeamVertical adapter = listMyTeamViewModel.getAdapterListTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        adapter.setShareSelectTeamViewModel(selectTeamViewModel);
        adapter.isMy = true ;
        adapter.isShow = this.isShow;
        binding.recycleViewListTeamVertical.setAdapter(listMyTeamViewModel.getAdapterListTeam());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recycleViewListTeamVertical.setLayoutManager(mLayoutManager);
        binding.btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"create new Team",Toast.LENGTH_SHORT).show();
                DialogFragment dialogFragment = new FragmentAddTeamDialog();
                dialogFragment.show(getParentFragmentManager(),"Add Team Diaglog");
            }
        });
    }




}
