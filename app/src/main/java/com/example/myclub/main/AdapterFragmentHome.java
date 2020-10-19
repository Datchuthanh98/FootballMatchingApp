package com.example.myclub.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myclub.view.field.fragment.FragmentMainField;
import com.example.myclub.view.match.fragment.FragmentMainMatch;
import com.example.myclub.view.player.Fragment.FragmentProfileMyself;
import com.example.myclub.view.team.fragment.FragmentListMainTeam;

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
                return new FragmentMainMatch();
            case 2:
                return new FragmentListMainTeam();
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
                return "Team";

            case 3:
                return "Profile";

        }
        return null;
    }
}
