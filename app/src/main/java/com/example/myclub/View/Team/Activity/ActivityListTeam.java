package com.example.myclub.View.Team.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.View.Team.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.View.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.databinding.ActivityEditTeamBinding;
import com.example.myclub.databinding.ActivityListTeamBinding;
import com.example.myclub.viewModel.ViewModelTodo;

public class ActivityListTeam extends AppCompatActivity {
    private ViewModelTodo viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListTeamBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_list_team);
       binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);
        RecycleViewAdapterListTeamVertical adapter = viewModel.getAdapterListTeam();
        adapter.setFm(getSupportFragmentManager());
        binding.recycleViewListTeamVertical.setAdapter(viewModel.getAdapterListTeam());

    }
}
