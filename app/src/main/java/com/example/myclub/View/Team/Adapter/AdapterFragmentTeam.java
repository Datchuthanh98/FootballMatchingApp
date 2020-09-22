package com.example.myclub.View.Team.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.View.Team.Fragment.FragmentListPlayer;
import com.example.myclub.View.Team.Fragment.FragmentMyTeam;
import com.example.myclub.View.Team.Fragment.FragmentProfileTeam;

public class AdapterFragmentTeam extends FragmentPagerAdapter {
    int numTab = 2;

    public AdapterFragmentTeam(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentMyTeam();
            case 1:
                return new FragmentListPlayer();
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
                return "Team";
            case 1:
                return "List Player";
        }
        return null;
    }
}
