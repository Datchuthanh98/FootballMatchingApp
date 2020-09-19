package com.example.myclub.View.Team.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.View.Player.Activity.ActivityListPlayer;
import com.example.myclub.R;
import com.example.myclub.View.Team.Activity.ActivityEditProfileTeam;
import com.example.myclub.View.Player.Adapter.RecycleViewAdapterListPlayerHorizontal;
import com.example.myclub.databinding.FragmentProfileTeamBinding;

public class FragmentProfileTeam extends Fragment {
    private FragmentProfileTeamBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile_team,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       binding.btnListPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityListPlayer.class);
                startActivity(intent);
            }
        });


        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityEditProfileTeam.class);
                startActivity(intent);
            }
        });


        binding.recycleViewListPlayerHorizontal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListPlayerHorizontal adapter = new RecycleViewAdapterListPlayerHorizontal();
        binding.recycleViewListPlayerHorizontal.setAdapter(adapter);
    }
}
