package com.example.myclub.view.Team.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myclub.R;
import com.example.myclub.view.Player.Fragment.FragmentProfilePlayer;
import com.example.myclub.databinding.FragmentEditTeamIntroduceBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentEditTeamIntroduce extends BottomSheetDialogFragment {

     private FragmentEditTeamIntroduceBinding binding;
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

    private void detach(){
        getParentFragmentManager().popBackStack();
        getParentFragmentManager().beginTransaction().detach(this).commit();
    }
}
