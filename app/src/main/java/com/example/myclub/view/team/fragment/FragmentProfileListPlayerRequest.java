package com.example.myclub.view.team.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentListBinding;
import com.example.myclub.databinding.FragmentListRequestBinding;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerRequestVertical;
import com.example.myclub.viewModel.ListPlayerRequestViewModel;
import com.example.myclub.viewModel.ListPlayerViewModel;


public class FragmentProfileListPlayerRequest extends Fragment {
    private ListPlayerRequestViewModel listPlayerViewModel = ListPlayerRequestViewModel.getInstance();
    private FragmentListRequestBinding binding;
    private String idTeam;

    public FragmentProfileListPlayerRequest(String idTeam) {
        this.idTeam = idTeam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listPlayerViewModel = new ViewModelProvider(this).get(ListPlayerRequestViewModel.class);
        binding = FragmentListRequestBinding.inflate(inflater);
        return  binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData(view.getContext());
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }
    private  void initComponent(){
        listPlayerViewModel.getListPlayer(idTeam);
        binding.recycleViewListRequestVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListPlayerRequestVertical adapter = listPlayerViewModel.getAdapterListPlayer();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListRequestVertical.setAdapter(adapter);
    }

    private void observeLiveData(final Context context) {
    }
}
