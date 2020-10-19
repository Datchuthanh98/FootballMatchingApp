package com.example.myclub.view.team.fragment;

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
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.myclub.R;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.model.Team;
import com.example.myclub.databinding.FragmentEditTeamIntroduceBinding;
import com.example.myclub.viewModel.TeamViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentEditTeamIntroduce extends BottomSheetDialogFragment {

     private FragmentEditTeamIntroduceBinding binding;
    private TeamViewModel teamViewModel = TeamViewModel.getInstance();
    private Dialog loadingDialog;
    private LoadingLayoutBinding loadingLayoutBinding;
    private  Map<String, Object> data = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_team_introduce_,container,false);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        View view = binding.getRoot();
        return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeLiveData(view.getContext());
        initComponent(view.getContext());
    }

    private void observeLiveData(final Context context) {
        teamViewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    teamViewModel.resetResult();
                    loadingDialog.dismiss();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    updateUITeam();
                    detach();
                } else if (result == Result.FAILURE) {
                    teamViewModel.resetResult();
                    loadingDialog.dismiss();
                    detach();
                    Toast.makeText(context, teamViewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void initComponent(final Context context){
        binding.imageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoadingDialog(context);
                teamViewModel.resetResult();
                loadingDialog.show();
                teamViewModel.updateProfile(getUpdateIntroduction());
            }
        });
    }

    private void detach(){
        dismiss();
    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingLayoutBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(loadingLayoutBinding.getRoot());
//        loadingLayoutBinding.title.setText(R.string.updating_information);
        loadingDialog.setCancelable(false);
    }

    private Map<String, Object> getUpdateIntroduction() {
        data.put("introduce", binding.txtIntroduce.getText().toString());
        return data;
    }

    private  void updateUITeam (){
        Team team = TeamViewModel.getInstance().getTeamLiveData().getValue();
        team.setIntroduce(binding.txtIntroduce.getText().toString());
        TeamViewModel.getInstance().setTeamLiveData(team);
    }
}
