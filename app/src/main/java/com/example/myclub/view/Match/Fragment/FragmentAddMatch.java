package com.example.myclub.view.Match.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentAddMatchBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Field;
import com.example.myclub.model.Team;
import com.example.myclub.model.TimeGame;
import com.example.myclub.view.Field.Fragment.FragmentListField;
import com.example.myclub.view.Team.Fragment.FragmentListMyTeam;
import com.example.myclub.viewModel.BookingFieldSession;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentAddMatch extends Fragment {

    private int pYear, pMonth, pDay, pHour, pMinute;
    private BookingFieldSession matchViewModel = BookingFieldSession.getInstance();
    private  Map<String ,Object> data = new HashMap<>();

    FragmentAddMatchBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMatchBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeLiveData(view.getContext());
        initComponent();


    }


    private  void initComponent(){
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
                        binding.txtDate.setText(dateString);
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

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchViewModel.addBookingField(getInforBooking());
                detach();
            }
        });



//        binding.selectClub.setVisibility(View.INVISIBLE);
    }

    private void observeLiveData(final Context context) {

        matchViewModel.getTeamLiveData().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                binding.team.setText(team.getName());
            }
        });


        matchViewModel.getFieldLiveData().observe(getViewLifecycleOwner(), new Observer<Field>() {
            @Override
            public void onChanged(Field field) {
           binding.field.setText(field.getName());
           matchViewModel.onChangeSelectTeam();
            }
        });


        matchViewModel.getTimeLiveData().observe(getViewLifecycleOwner(), new Observer<TimeGame>() {
            @Override
            public void onChanged(TimeGame timeGame) {
                binding.timeGame.setText(timeGame.getStartTime()+"-"+timeGame.getEndTime());
            }
        });


    }

    private Map<String, Object> getInforBooking() {
        data.put("idTeam", matchViewModel.getTeamLiveData().getValue().getId());
        data.put("idField", matchViewModel.getFieldLiveData().getValue().getId());
        data.put("date", binding.txtDate.getText().toString());
        data.put("time", matchViewModel.getTimeLiveData().getValue().getId());
        data.put("note",binding.txtNote.getText().toString());
        data.put("phone",binding.txtPhone.getText().toString());
        return data;
    }


    private void detach() {
        getParentFragmentManager().popBackStack();
    }
}
