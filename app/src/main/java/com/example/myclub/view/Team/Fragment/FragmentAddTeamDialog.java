package com.example.myclub.view.Team.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myclub.R;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentAddTeamBinding;
import com.example.myclub.databinding.FragmentEditPlayerBasicBinding;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.viewModel.TeamViewModel;

public class FragmentAddTeamDialog extends DialogFragment {


    FragmentAddTeamBinding binding;
    private TeamViewModel viewModel;
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
        viewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        binding.setViewModel(viewModel);
        initComponent(getContext());
        observeLiveData(getContext());
        View view = binding.getRoot();
        return  view;
    }



    private void detach(){
        dismiss();
    }

    private  void initComponent(final Context context){
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoadingDialog(context);
                loadingDialog.show();
                viewModel.createTeam(binding.txtName.getText().toString(),binding.txtPhone.getText().toString(),binding.txtEmail.getText().toString());
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

    private void observeLiveData(final Context context) {
        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                Log.d("checkteam", "onSuccess: view.");
                if (result == Result.SUCCESS) {

                    loadingDialog.dismiss();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    detach();
                } else if (result == Result.FAILURE) {
                    loadingDialog.dismiss();
                    Toast.makeText(context, viewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                    detach();
                }
            }
        });
    }
}
