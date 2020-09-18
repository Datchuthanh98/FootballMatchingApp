package com.example.myclub.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.Activity.Player.ActivityListPlayer;
import com.example.myclub.R;
import com.example.myclub.adapter.RecycleViewAdapterListPlayerHorizontal;

public class FragmentProfileClub extends Fragment {
    RecyclerView recyclerView;
    private  Button btnListPlayer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_club,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnListPlayer = view.findViewById(R.id.btnListPlayer);
        btnListPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityListPlayer.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recycleViewListPlayerHorizontal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListPlayerHorizontal adapter = new RecycleViewAdapterListPlayerHorizontal();
        recyclerView.setAdapter(adapter);
    }
}
