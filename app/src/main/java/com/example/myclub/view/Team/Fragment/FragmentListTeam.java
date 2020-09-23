package com.example.myclub.view.Team.Fragment;

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
import com.example.myclub.databinding.FragmentListPlayerBinding;
import com.example.myclub.databinding.FragmentListTeamBinding;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentListTeam extends Fragment {
    private ViewModelTodo viewModel;
    private FragmentListTeamBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_team,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

       binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTeamVertical adapter = viewModel.getAdapterListTeam();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTeamVertical.setAdapter(viewModel.getAdapterListTeam());


    }
}
