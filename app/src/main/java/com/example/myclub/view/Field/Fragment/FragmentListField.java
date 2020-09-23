package com.example.myclub.view.Field.Fragment;

import android.content.Intent;
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
import com.example.myclub.databinding.FragmentListFieldBinding;
import com.example.myclub.view.Field.Adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentListField extends Fragment {
    private ViewModelTodo viewModel;

    FragmentListFieldBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_field, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);
        RecycleViewAdapterListFieldVertical adapter = viewModel.getAdapterListField();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListFieldVertical.setAdapter(viewModel.getAdapterListField());
        binding.recycleViewListFieldVertical.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
