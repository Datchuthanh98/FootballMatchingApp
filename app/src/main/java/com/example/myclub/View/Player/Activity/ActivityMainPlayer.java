package com.example.myclub.View.Player.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.View.Player.Adapter.AdapterFragmentProfile;
import com.example.myclub.Animation.HorizontalFlipTransformation;
import com.google.android.material.tabs.TabLayout;

public class ActivityMainPlayer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_profile);
        super.onCreate(savedInstanceState);



        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new HorizontalFlipTransformation());
        FragmentManager manager = getSupportFragmentManager();
        AdapterFragmentProfile adapter = new AdapterFragmentProfile(manager, AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_black_24dp);

    }



}