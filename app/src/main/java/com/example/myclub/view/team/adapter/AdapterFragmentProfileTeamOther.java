package com.example.myclub.view.team.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.view.team.fragment.FragmentListEvaluate;
import com.example.myclub.view.team.fragment.FragmentProfileInforTeamOther;
import com.example.myclub.view.team.fragment.FragmentProfileListPlayer;

public class AdapterFragmentProfileTeamOther extends FragmentPagerAdapter {
    int numTab = 2;

    public AdapterFragmentProfileTeamOther(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentProfileInforTeamOther();
            case 1:
                return new FragmentListEvaluate();
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
                return "Thông tin ";
            case 1:
                return "Đánh giá";
        }
        return null;
    }


}
