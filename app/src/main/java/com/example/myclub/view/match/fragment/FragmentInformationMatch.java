package com.example.myclub.view.match.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentEditPlayerInforBinding;
import com.example.myclub.databinding.FragmentMatchInformationBinding;
import com.example.myclub.model.Match;
import com.example.myclub.viewModel.ProfileMatchViewModel;
import com.example.myclub.viewModel.ShareSelectTeamViewModel;

import java.util.Calendar;


public class FragmentInformationMatch extends Fragment {


    FragmentMatchInformationBinding binding;
    ProfileMatchViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMatchInformationBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ProfileMatchViewModel.class);
        super.onViewCreated(view, savedInstanceState);
        viewModel.getMatchMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                if(match != null) {
                    binding.setMatch(viewModel.getMatchMutableLiveData().getValue());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(viewModel.getMatchMutableLiveData().getValue().getIdBooking().getDate());
                    int pYear = calendar.get(Calendar.YEAR);
                    int pMonth = calendar.get(Calendar.MONTH);
                    int pDay = calendar.get(Calendar.DAY_OF_MONTH);
                    String startTime = viewModel.getMatchMutableLiveData().getValue().getIdBooking().getStartTime() + "h";
                    String endTime = viewModel.getMatchMutableLiveData().getValue().getIdBooking().getEndTime() + "h";
                    String timeDate = pDay + "/" + (pMonth + 1) + "/" + pYear + "," + startTime + "-" + endTime;
                    binding.txtTime.setText(timeDate);

                    String cutEndTime[] = viewModel.getMatchMutableLiveData().getValue().getIdBooking().getEndTime().split(":",2);
                    int pHourEnd=Integer.parseInt(cutEndTime[0]);
                    int mMinuteEnd= Integer.parseInt(cutEndTime[1]);

                    String cutStartTime[] = viewModel.getMatchMutableLiveData().getValue().getIdBooking().getStartTime().split(":",2);
                    int pHourStart=Integer.parseInt(cutStartTime[0]);
                    int mMinuteStart= Integer.parseInt(cutStartTime[1]);

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(pYear,pMonth,pDay,pHourEnd,mMinuteEnd);
                    long timeGameEnd = calendar2.getTimeInMillis();

                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.set(pYear,pMonth,pDay,pHourStart,mMinuteStart);
                    long timeGameStart = calendar3.getTimeInMillis();

                    Calendar calendar4 = Calendar.getInstance();
                    long timeNow = calendar4.getTimeInMillis();


                    if(timeNow < timeGameStart){
                        binding.status.setText("Sắp diễn ra");
                    }else if(timeGameStart <= timeNow && timeNow  <= timeGameEnd){
                        binding.status.setText("Đang diễn ra ");
                    }else {
                        binding.status.setText("Đã kết thúc ");
                    }
                }
            }
        });









    }
}
