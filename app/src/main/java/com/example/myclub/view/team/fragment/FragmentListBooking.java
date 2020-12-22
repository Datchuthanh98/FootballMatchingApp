package com.example.myclub.view.field.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.FieldDataSource;
import com.example.myclub.databinding.FragmentListTimeBinding;
import com.example.myclub.model.Field;
import com.example.myclub.model.TimeGame;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListTimeVertical;

import com.example.myclub.session.SessionBookingField;

import java.util.ArrayList;
import java.util.List;

public class FragmentListTime extends Fragment {
    private SessionBookingField viewModel = SessionBookingField.getInstance();
    private  FieldDataSource fieldDataSource = FieldDataSource.getInstance();
    private  RecycleViewAdapterListTimeVertical adapter = new RecycleViewAdapterListTimeVertical(null);
    private FragmentListTimeBinding binding;
    private String  idField;

    public FragmentListTime(String idField) {
        this.idField = idField;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListTimeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recycleViewListTimeVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment

        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTimeVertical.setAdapter(adapter);
        fieldDataSource.loadListTime(idField, new CallBack<List<TimeGame>, String>() {
            @Override
            public void onSuccess(List<TimeGame> timeGames) {
                if(timeGames == null){
                    adapter.setListTime(new ArrayList<TimeGame>());
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.setListTime(timeGames);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String s) {

            }
        });

    }
}
