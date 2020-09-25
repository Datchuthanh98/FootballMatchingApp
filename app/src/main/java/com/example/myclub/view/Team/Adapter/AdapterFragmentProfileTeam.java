package com.example.myclub.view.Team.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.view.Match.Fragment.FragmentComment;
import com.example.myclub.view.Team.Fragment.FragmentInformationTeam;
import com.example.myclub.view.Team.Fragment.FragmentListMyTeam;
import com.example.myclub.view.Team.Fragment.FragmentListOtherTeam;

public class AdapterFragmentProfileTeam extends FragmentPagerAdapter {
    int numTab = 3;

    public AdapterFragmentProfileTeam(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentInformationTeam();
            case 1:
                return new FragmentListOtherTeam();
            case 2:
                return new FragmentComment();
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
            case 2:
                return "Comment";
        }
        return null;
    }


}
