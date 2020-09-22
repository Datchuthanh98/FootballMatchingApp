package com.example.myclub.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.example.myclub.View.Field.Activity.ActivityListField;
import com.example.myclub.View.Field.Activity.ActivityMainField;
import com.example.myclub.View.Player.Activity.ActivityMainPlayer;
import com.example.myclub.View.Team.Activity.ActivityListTeam;
import com.example.myclub.View.Team.Activity.ActivityMainTeam;
import com.example.myclub.R;
import com.example.myclub.databinding.ActivityEditPlayerBinding;
import com.example.myclub.databinding.ActivityHomeBinding;

public class ActivityHome extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        binding.cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMainPlayer.class);
                startActivity(intent);
            }
        });

       binding.cardTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMainTeam.class);
                startActivity(intent);
            }
        });

        binding.cardListTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityListTeam.class);
                startActivity(intent);
            }
        });

        binding.cardListField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityListField.class);
                startActivity(intent);
            }
        });


    }
}
