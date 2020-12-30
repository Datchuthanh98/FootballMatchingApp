package com.example.myclub.view.team.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.R;
import com.example.myclub.data.datasource.FieldDataSource;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.databinding.FragmentListBookingBinding;
import com.example.myclub.databinding.FragmentListTimeBinding;
import com.example.myclub.model.Booking;
import com.example.myclub.model.TimeGame;
import com.example.myclub.session.SessionBookingField;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListBookingVertical;

import java.util.ArrayList;
import java.util.List;

public class FragmentListBooking extends Fragment {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private RecycleViewAdapterListBookingVertical adapter = new RecycleViewAdapterListBookingVertical(null);
    private FragmentListBookingBinding binding;
    private String  idField;

    public FragmentListBooking(String idField) {
        this.idField = idField;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBookingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycleViewListTimeVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment

        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTimeVertical.setAdapter(adapter);
        teamRepository.getListBooking(idField, new CallBack<List<Booking>, String>() {
            @Override
            public void onSuccess(List<Booking> bookingList) {
                Toast.makeText(getContext(),"Booking Frm",Toast.LENGTH_SHORT).show();
                if(bookingList == null){
                    adapter.setListMatch(new ArrayList<Booking>());
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.setListMatch(bookingList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String s) {

            }
        });


    }
}
