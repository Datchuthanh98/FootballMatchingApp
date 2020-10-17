package com.example.myclub.view.field.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentMapBinding;
//import com.example.myclub.databinding.FragmentMapBinding;


public class FragmentMap extends Fragment {

    public TextView scrollingText;
    FragmentMapBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        binding.lblGoogleMap.setSelected(true);
        View view = binding.getRoot();
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       binding.lblGoogleMap.setSelected(true);


    }
}
