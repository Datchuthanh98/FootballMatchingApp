package com.example.myclub.view.team.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentAddTeamBinding;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.viewModel.ListMyTeamViewModel;

public class FragmentAddTeamDialog extends DialogFragment {
    FragmentAddTeamBinding binding;
    private ListMyTeamViewModel listMyTeamViewModel = ListMyTeamViewModel.getInstance();
    private Dialog loadingDialog;
    private LoadingLayoutBinding loadingLayoutBinding;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = FragmentAddTeamBinding.inflate(inflater);
        builder.setView(binding.getRoot());
        return builder.create();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentAddTeamBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        initComponent(getContext());
        View view = binding.getRoot();
        return  view;
    }



    private void detach(){
        dismiss();
    }

    private  void initComponent(final Context context){
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoadingDialog(context);
                listMyTeamViewModel.createTeam(binding.txtName.getText().toString(),binding.txtPhone.getText().toString(),binding.txtEmail.getText().toString());
                detach();
            }
        });

    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingLayoutBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(loadingLayoutBinding.getRoot());
//        loadingLayoutBinding.title.setText(R.string.updating_information);
        loadingDialog.setCancelable(false);
    }




}
