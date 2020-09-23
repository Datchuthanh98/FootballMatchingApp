package com.example.myclub.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myclub.view.Field.Fragment.FragmentMainField;
import com.example.myclub.view.Field.Fragment.FragmentListMatch;
import com.example.myclub.view.Player.Fragment.FragmentProfileMyself;
import com.example.myclub.view.Team.Fragment.FragmentMainTeam;

public class AdapterFragmentHome extends FragmentStatePagerAdapter {
    int numTab = 4;
    public AdapterFragmentHome(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentMainField();

            case 1:
                return new FragmentListMatch();
            case 2:
                return new FragmentMainTeam();

            case 3:
                return new FragmentProfileMyself();

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
                return "Field";
            case 1:
                return "Match";
            case 2:
                return "Club";

            case 3:
                return "Profile";

        }
        return null;
    }
}
