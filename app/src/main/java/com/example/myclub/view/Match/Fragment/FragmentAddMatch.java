package com.example.myclub.view.Match.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.animation.HorizontalFlipTransformation;
import com.example.myclub.databinding.FragmentAddMatchBinding;
import com.example.myclub.databinding.FragmentMainMatchBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.view.Field.Fragment.FragmentListField;
import com.example.myclub.view.Match.Adapter.AdapterFragmentMatch;
import com.example.myclub.view.Player.Adapter.AdapterFragmentProfile;
import com.example.myclub.view.Team.Fragment.FragmentListMyTeam;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class FragmentAddMatch extends Fragment {

    private int pYear, pMonth, pDay, pHour, pMinute;
        FragmentAddMatchBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_match, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });

        binding.btnSelectClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                Fragment selectTeam = new FragmentListMyTeam(false);

                activityHome.addFragment(selectTeam);
            }
        });

        binding.btnSelectField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                Fragment selectField = new FragmentListField(false);
                activityHome.addFragment(selectField);
            }
        });

        binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Dialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateString = day + "/" + month + "/" + year;
                        pDay = day;
                        pMonth = month;
                        pYear = year;
                        binding.lblDate.setText(dateString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        binding.btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                Fragment selectTime = new FragmentListTime();
                activityHome.addFragment(selectTime);
            }
        });

//        binding.selectClub.setVisibility(View.INVISIBLE);



    }




    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }
}
