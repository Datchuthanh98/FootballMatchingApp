package com.example.myclub.view.Player.Fragment;

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
import com.example.myclub.databinding.FragmentEditPlayerPlayerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentEditPlayerPlayer extends BottomSheetDialogFragment {

     private ImageButton btnSave;
     private FragmentEditPlayerPlayerBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_edit_player_basic_,container,false);
          binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_player_player_,container,false);
        View view = binding.getRoot();
        return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();

            }
        });

        btnSave = view.findViewById(R.id.image_btn_2);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });

    }

    private void detach(){
        getParentFragmentManager().popBackStack();
//        getParentFragmentManager().beginTransaction().detach(this).commit();
    }
}
