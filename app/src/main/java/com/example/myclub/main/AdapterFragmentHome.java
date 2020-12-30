package com.example.myclub.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myclub.view.field.fragment.FragmentMainField;
import com.example.myclub.view.match.fragment.FragmentListMatch;
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
                return new FragmentListMatch();
            case 1:
            return new FragmentMainField();
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
                return "Trận đấu";
            case 1:
                return "Sân bóng";
            case 2:
                return "Đội bóng";

            case 3:
                return "Cá nhân";

        }
        return null;
    }
}
