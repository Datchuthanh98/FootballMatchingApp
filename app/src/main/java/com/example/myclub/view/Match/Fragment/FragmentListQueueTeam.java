package com.example.myclub.view.Match.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myclub.databinding.FragmentListQueueTeamBinding;
;

public class FragmentListQueueTeam extends Fragment {


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
//        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);
//        RecycleViewAdapterListFieldVertical adapter = viewModel.get();
//        adapter.setFm(getParentFragmentManager());
//        binding.recycleViewListTeamVertical.setAdapter(viewModel.getAdapterListField());
//        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
