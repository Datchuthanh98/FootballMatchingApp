package com.example.myclub.view.Match.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myclub.view.Match.Fragment.FragmentListBooking;
import com.example.myclub.view.Match.Fragment.FragmentListMatch;
import com.example.myclub.view.Match.Fragment.FragmentListMyMatch;

public class AdapterFragmentMatch extends FragmentStatePagerAdapter {
    int numTab = 3;
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
            case 2 :
                return  new FragmentListBooking();
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
            case 2:
                return "My Booking";
        }
        return null;
    }

}
