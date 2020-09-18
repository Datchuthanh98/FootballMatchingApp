package com.example.myclub.Activity.Player;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;

public class ActivityInformationPlayer extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_profile_player);
        super.onCreate(savedInstanceState);
    }
}
