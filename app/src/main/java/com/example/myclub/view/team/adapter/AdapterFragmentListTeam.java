package com.example.myclub.view.team.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.view.team.fragment.FragmentListMyTeam;
import com.example.myclub.view.team.fragment.FragmentListOtherTeam;

public class AdapterFragmentListTeam extends FragmentPagerAdapter {
    int numTab = 2;

    public AdapterFragmentListTeam(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentListMyTeam();
            case 1:
                return new FragmentListOtherTeam();
        }
        return  null;
    }

    @Override
    public int getCount() {
        return numTab;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "My Team";
            case 1:
                return "Other Team";
        }
        return null;
    }


}
