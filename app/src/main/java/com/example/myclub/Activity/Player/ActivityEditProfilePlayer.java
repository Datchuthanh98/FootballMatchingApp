package com.example.myclub.Activity.Player;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.myclub.R;
import com.example.myclub.Fragment.Player.FragmentEditPlayerBasic;
import com.example.myclub.databinding.ActivityEditPlayerBinding;

public class ActivityEditProfilePlayer extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_player);

        ActivityEditPlayerBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_player);

        binding.btnEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



//        toolbar.inflateMenu(R.menu.mymenu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return false;
//            }
//        });


        binding.btnEditBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new FragmentEditPlayerBasic(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
