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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentListBinding;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListTeamViewModel;
import com.example.myclub.session.SessionUser;
import com.example.myclub.viewModel.ShareSelectTeamViewModel;

public class FragmentListMyTeam extends Fragment {
    private ListTeamViewModel listTeamViewModel;
    private ShareSelectTeamViewModel selectTeamViewModel ;
    private SessionUser sessionUser = SessionUser.getInstance();
    private FragmentListBinding binding;
    public boolean isShow = true ;


    public FragmentListMyTeam( boolean isShow) {
        this.isShow = isShow;
    }

    public FragmentListMyTeam( ) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listTeamViewModel = new ViewModelProvider(getActivity()).get(ListTeamViewModel.class);
        binding = FragmentListBinding.inflate(inflater);
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

        listTeamViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result == Result.SUCCESS){
                    listTeamViewModel.getListTeam(sessionUser.getPlayerLiveData().getValue().getId());
                }

            }
        });
}

    private  void initComponent(){
        listTeamViewModel.getListTeam(sessionUser.getPlayerLiveData().getValue().getId());
        binding.recycleViewListVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListTeamVertical adapter = listTeamViewModel.getAdapterListTeam();


        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        adapter.setShareSelectTeamViewModel(selectTeamViewModel);
        adapter.isMy = true ;
        adapter.isShow = this.isShow;
        binding.recycleViewListVertical.setAdapter(adapter);
        binding.btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new FragmentAddTeamDialog();
                dialogFragment.show(getParentFragmentManager(),"Add Team Diaglog");
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTeamViewModel.searchField(binding.txtSearch.getText().toString());

            }
        });
    }




}
