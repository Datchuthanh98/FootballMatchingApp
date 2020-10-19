package com.example.myclub.view.match.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.databinding.FragmentListBookingBinding;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListBookingVertical;
import com.example.myclub.viewModel.ListBookingViewModel;

public class FragmentListBooking extends Fragment {
    private ListBookingViewModel viewModel;
    private FragmentListBookingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ListBookingViewModel.class);
        binding = FragmentListBookingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       binding.recycleViewListBookingVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListBookingVertical adapter = viewModel.getAdapterListBooking();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListBookingVertical.setAdapter(viewModel.getAdapterListBooking());


    }
}
