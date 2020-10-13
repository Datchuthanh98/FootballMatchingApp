package com.example.myclub.view.Team.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.view.Match.Fragment.FragmentComment;
import com.example.myclub.view.Team.Fragment.FragmentProfileListPlayer;
import com.example.myclub.view.Team.Fragment.FragmentProfileBasicTeam;
import com.example.myclub.view.Team.Fragment.FragmentProfileListPlayerRequest;

public class AdapterFragmentProfileTeam extends FragmentPagerAdapter {
    int numTab = 3;
    String  idTeam;

    public AdapterFragmentProfileTeam(@NonNull FragmentManager fm, int behavior,String idTeam) {
        super(fm, behavior);
        this.idTeam = idTeam;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentProfileBasicTeam();
            case 1:
                return new FragmentProfileListPlayer(idTeam);
            case 2:
                return new FragmentProfileListPlayerRequest(idTeam);
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
                return "List Request";
        }
        return null;
    }


}
