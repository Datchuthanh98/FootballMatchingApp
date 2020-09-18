package com.example.myclub.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myclub.fragment.FragmentProfileClub;
import com.example.myclub.fragment.FragmentProfileField;
import com.example.myclub.fragment.FragmentProfilePlayer;

public class AdapterFragmentProfile extends FragmentPagerAdapter {
    int numTab = 1;
    public AdapterFragmentProfile(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
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
        }
        return  null;
    }

    @Override
    public int getCount() {
        return numTab;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position){
//            case 0:
//                return "Profile";
////            case 1:
////                if(isOnline){
////                    if(isManager){
////                    return "Quản lý ";
////                    }else{
////                    return "Cá nhân";
////                    }}else{
////                    return  "Offline";
////                }
//            case 1:
//                return "Club";
//            case 2:
//                return "Field";
//
//        }
//        return null;
//    }
}
