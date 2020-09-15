package com.example.myclub.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.frangment.FragmentProfileClub;
import com.example.myclub.frangment.FragmentProfileField;
import com.example.myclub.frangment.FragmentProfilePlayer;

public class AdapterFragment extends FragmentPagerAdapter {
    int numTab = 3;
    boolean isManager,isOnline;
    public AdapterFragment(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.isManager=isManager;
        this.isOnline=isOnline;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentProfilePlayer();
//            case 1:
//                if(isOnline){
//                if(isManager){
//                    return new FragmentManagePlayer();
//                }else{
//                    return  new FragmentProfilePlayer();
//                }}else{
//                    return  new FragmentOffline();
//                }
            case 1:
                return new FragmentProfileClub();
            case 2:
                return new FragmentProfileField();
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
                return "Profile";
//            case 1:
//                if(isOnline){
//                    if(isManager){
//                    return "Quản lý ";
//                    }else{
//                    return "Cá nhân";
//                    }}else{
//                    return  "Offline";
//                }
            case 1:
                return "Club";
            case 2:
                return "Field";

        }
        return null;
    }
}
