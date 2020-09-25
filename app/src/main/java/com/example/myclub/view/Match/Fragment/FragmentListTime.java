package com.example.myclub.view.Match.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentListMatchBinding;
import com.example.myclub.databinding.FragmentListTimeBinding;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentListTime extends Fragment {
    private ViewModelTodo viewModel;
    private FragmentListTimeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_time,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       binding.recycleViewListTimeVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTimeVertical adapter = viewModel.getAdapterListTimeVertical();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTimeVertical.setAdapter(viewModel.getAdapterListTimeVertical());


    }
}
