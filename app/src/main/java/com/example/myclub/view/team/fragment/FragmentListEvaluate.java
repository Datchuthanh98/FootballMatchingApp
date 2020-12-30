package com.example.myclub.view.team.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentListCommentBinding;
import com.example.myclub.databinding.FragmentListEvaluateBinding;
import com.example.myclub.session.SessionUser;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListCommentVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterLisEvaluateVertical;
import com.example.myclub.viewModel.ListEvaluateViewModel;
import com.example.myclub.viewModel.ProfileMatchViewModel;
import com.example.myclub.viewModel.ProfileOtherTeamViewModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentListEvaluate extends Fragment {
    private ListEvaluateViewModel viewModel;
    private FragmentListEvaluateBinding binding;
    private String idTeam;

    public FragmentListEvaluate(String idTeam) {
        this.idTeam = idTeam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListEvaluateBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ListEvaluateViewModel.class);
        initComponent();
    }


    private  void initComponent(){
        viewModel.idTeam = idTeam;
        viewModel.getListEvaluate(idTeam);
        binding.recycleViewListCommentVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterLisEvaluateVertical adapter = viewModel.getAdapterListEvaluate();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListCommentVertical.setAdapter(adapter);


     binding.btnComment.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             DialogFragment dialogFragment = new FragmentAddEvaluateDialog();
             dialogFragment.show(getParentFragmentManager(),"Add Evaluate Diaglog");
         }
     });
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }






}
