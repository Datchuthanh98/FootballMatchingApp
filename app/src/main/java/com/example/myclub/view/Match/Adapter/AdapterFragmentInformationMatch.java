package com.example.myclub.view.Match.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myclub.view.Match.Fragment.FragmentComment;
import com.example.myclub.view.Match.Fragment.FragmentInformationMatch;
import com.example.myclub.view.Match.Fragment.FragmentListQueueTeam;

public class AdapterFragmentInformationMatch extends FragmentStatePagerAdapter {
    int numTab = 3;
    public AdapterFragmentInformationMatch(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
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
                return  new FragmentComment();
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
