package com.example.myclub.view.Player.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.databinding.FragmentBookingFieldBinding;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamHorizontal;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListTimeHorizontal;
import com.example.myclub.viewModel.ViewModelTodo;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentTest extends BottomSheetDialogFragment {
    private ViewModelTodo viewModel;
    FragmentBookingFieldBinding binding;

    public FragmentTest() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingFieldBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);
        binding.recycleViewListTeamHorizontal.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        RecycleViewAdapterListTeamHorizontal adapterListTeamHorizontal = viewModel.getAdapterListTeamHorizontal();
        adapterListTeamHorizontal.setFm(getChildFragmentManager());
        binding.recycleViewListTeamHorizontal.setAdapter(viewModel.getAdapterListTeamHorizontal());

        binding.recycleViewListTime.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        RecycleViewAdapterListTimeHorizontal adapterListTimeHorizontal = viewModel.getAdapterListTimeHorizontal();
        adapterListTimeHorizontal.setFm(getChildFragmentManager());
        binding.recycleViewListTime.setAdapter(viewModel.getAdapterListTimeHorizontal());

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Show r ",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return binding.getRoot();

    }


}
