package com.example.myclub.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.animation.HorizontalFlipTransformation;
import com.example.myclub.view.Player.Adapter.AdapterFragmentProfile;
import com.example.myclub.R;
import com.google.android.material.tabs.TabLayout;

public class ActivityHome extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);



        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
//        viewPager.setPageTransformer(true, new HorizontalFlipTransformation());
        FragmentManager manager = getSupportFragmentManager();
        AdapterFragmentHome adapter = new AdapterFragmentHome(manager, AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_account_circle_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_account_circle_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_account_circle_black_24dp);



    }

    public void addFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, null).addToBackStack(null).commit();
    }
}
