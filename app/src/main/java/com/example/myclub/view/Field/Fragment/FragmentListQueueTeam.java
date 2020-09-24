package com.example.myclub.view.Field.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.databinding.FragmentListFieldBinding;
import com.example.myclub.databinding.FragmentListQueueTeamBinding;
import com.example.myclub.view.Field.Adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentListQueueTeam extends Fragment {
    private ViewModelTodo viewModel;

        FragmentListQueueTeamBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListQueueTeamBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);
        RecycleViewAdapterListFieldVertical adapter = viewModel.getAdapterListField();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTeamVertical.setAdapter(viewModel.getAdapterListField());
        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
