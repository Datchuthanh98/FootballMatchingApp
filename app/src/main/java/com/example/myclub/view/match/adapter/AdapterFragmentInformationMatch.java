package com.example.myclub.view.match.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.myclub.view.match.fragment.FragmentInformationMatch;
import com.example.myclub.view.match.fragment.FragmentListComment;
import com.example.myclub.view.match.fragment.FragmentListQueueTeam;

public class AdapterFragmentInformationMatch extends FragmentStatePagerAdapter {
    int numTab = 3;
    String idMatch ;
    public AdapterFragmentInformationMatch(@NonNull FragmentManager fm, int behavior ,String idMatch) {
        super(fm, behavior);
        this.idMatch = idMatch ;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentInformationMatch();
            case 1 :
                return  new FragmentListQueueTeam();
            case 2 :
                return  new FragmentListComment();
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
                return "Queue Club";
            case 2:
                return "Comment";
        }
        return null;
    }

}
