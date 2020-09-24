package com.example.myclub.view.Team.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.view.Team.Fragment.FragmentListMyTeam;
import com.example.myclub.view.Team.Fragment.FragmentListOtherTeam;
import com.example.myclub.view.Team.Fragment.FragmentMyTeam;

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
                return new FragmentListOtherTeam();
            case 1:
                return new FragmentListMyTeam();
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
