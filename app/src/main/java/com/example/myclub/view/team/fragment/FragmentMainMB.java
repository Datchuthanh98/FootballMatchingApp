package com.example.myclub.view.team.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentMainListBinding;
import com.example.myclub.view.field.adapter.AdapterFragmentField;
import com.example.myclub.view.player.Adapter.AdapterFragmentProfile;
import com.example.myclub.view.team.adapter.AdapterFragmentListMB;
import com.google.android.material.tabs.TabLayout;

public class FragmentMainMB extends Fragment {

    private  String idTeam;
    public FragmentMainMB(String idTeam) {
        this.idTeam = idTeam;
    }

    FragmentMainListBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_list, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        FragmentManager manager = getParentFragmentManager();
        AdapterFragmentListMB adapter = new AdapterFragmentListMB(getChildFragmentManager(), AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,idTeam);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }
}
