package com.example.myclub.View.Team.Fragment;

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
import com.example.myclub.View.Team.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.databinding.FragmentListPlayerBinding;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentListPlayer extends Fragment {
    private ViewModelTodo viewModel;
    private FragmentListPlayerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_player,container,false);
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

       binding.recycleViewListPlayerVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListPlayerVertical adapter = viewModel.getAdapterListPlayer();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListPlayerVertical.setAdapter(viewModel.getAdapterListPlayer());


    }
}
