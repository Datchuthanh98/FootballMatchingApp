package com.example.myclub.view.team.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.view.team.fragment.FragmentListBooking;
import com.example.myclub.view.team.fragment.FragmentListMatchByTeam;

public class AdapterFragmentListMB extends FragmentPagerAdapter {
    int numTab = 2;
    private  String idTeam;

    public AdapterFragmentListMB(@NonNull FragmentManager fm, int behavior, String idTeam) {
        super(fm, behavior);
        this.idTeam = idTeam;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentListMatchByTeam(idTeam);
            case 1:
                return new FragmentListBooking(idTeam);
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
                return "Đặt sân";
        }
        return null;
    }


}
