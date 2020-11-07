package com.example.myclub.view.team.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.view.team.fragment.FragmentProfileListPlayer;
import com.example.myclub.view.team.fragment.FragmentProfileInforTeam;
import com.example.myclub.view.team.fragment.FragmentProfileListPlayerRequest;

public class AdapterFragmentProfileMyTeam extends FragmentPagerAdapter {
    int numTab = 2;

    public AdapterFragmentProfileMyTeam(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentProfileInforTeam();
            case 1:
                return new FragmentProfileListPlayer();
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
                return "Information";
            case 1:
                return "List Player";
        }
        return null;
    }


}
