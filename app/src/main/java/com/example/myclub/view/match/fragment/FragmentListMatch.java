package com.example.myclub.view.match.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.databinding.FragmentListBinding;
import com.example.myclub.databinding.FragmentListCallendarBinding;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.viewModel.ListMatchViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class FragmentListMatch extends Fragment {
    private ListMatchViewModel viewModel ;
    private FragmentListCallendarBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListCallendarBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListMatchViewModel.class);
        initComponent();
        observerLiveDate();


    }

    private  void initComponent(){
        RecycleViewAdapterListMatchVertical adapter = viewModel.getAdapterListMatch();
        adapter.setFm(getParentFragmentManager());
        binding.txtCallendar.setText(viewModel.getDateNow());
        binding.recycleViewListVertical.setAdapter(adapter);
        binding.recycleViewListVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.btnCallendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Dialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String sday, smonth;
                        if(day<10){
                            sday ="0"+day;
                        }else{
                            sday=""+day;
                        }

                        if(month<9){
                            smonth ="0"+(month+1);
                        }else{
                            smonth=""+(month+1);
                        }

                        String dateString = sday + "/" + smonth + "/" + year;

                        binding.txtCallendar.setText(dateString);
                        viewModel.getListMatchByDate(dateString);

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private void observerLiveDate() {
      viewModel.getTeamLoadState().observe(getViewLifecycleOwner(), new Observer<LoadingState>() {
          @Override
          public void onChanged(LoadingState loadingState) {
              if (loadingState == null) return;
              if (loadingState == LoadingState.INIT) {
                  binding.loadingLayout.setVisibility(View.VISIBLE);
              } else if (loadingState == LoadingState.LOADING) {
                  binding.loadingLayout.setVisibility(View.VISIBLE);
              } else if (loadingState == LoadingState.LOADED) {
                  binding.loadingLayout.setVisibility(View.GONE);
              }
          }
      });

      viewModel.getStatusData().observe(getViewLifecycleOwner(), new Observer<Status>() {
          @Override
          public void onChanged(Status status) {
               if(status == Status.NO_DATA){
                   binding.viewNoData.setVisibility(View.VISIBLE);
               }else if(status == status.EXIST_DATA){
                   binding.viewNoData.setVisibility(View.GONE);
               }
          }
      });


    }




}
