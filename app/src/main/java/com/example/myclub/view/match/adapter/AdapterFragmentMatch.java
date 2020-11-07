package com.example.myclub.view.match.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myclub.view.match.fragment.FragmentListMatch;
import com.example.myclub.view.match.fragment.FragmentListMyMatch;

public class AdapterFragmentMatch extends FragmentStatePagerAdapter {
    int numTab = 2;
    public AdapterFragmentMatch(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentListMatch();
            case 1 :
                return  new FragmentListMyMatch();
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
                return "Macthes";
            case 1:
                return "My Match";
        }
        return null;
    }

}
