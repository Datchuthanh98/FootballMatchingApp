package com.example.myclub.view.Field.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentBookingFieldBinding;
import com.example.myclub.databinding.FragmentListFieldBinding;
import com.example.myclub.view.Field.Adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamHorizontal;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTimeHorizontal;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentBookingFiledDiaLog extends DialogFragment {
    private ViewModelTodo viewModel;

    FragmentBookingFieldBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = FragmentBookingFieldBinding.inflate(inflater);
        builder.setView(binding.getRoot());
        return builder.create();
    }
}
