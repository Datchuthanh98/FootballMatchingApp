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

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentListPlayerRequestBinding;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerRequestVertical;
import com.example.myclub.viewModel.ListPlayerViewModel;
import com.example.myclub.viewModel.TeamViewModel;

public class FragmentProfileListPlayerRequest extends Fragment {
    private ListPlayerViewModel listPlayerViewModel = ListPlayerViewModel.getInstance();
    private TeamViewModel teamViewModel = TeamViewModel.getInstance();
    private FragmentListPlayerRequestBinding binding;
    private String idTeam;

    public FragmentProfileListPlayerRequest(String idTeam) {
        this.idTeam = idTeam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listPlayerViewModel = new ViewModelProvider(this).get(ListPlayerViewModel.class);
        binding = FragmentListPlayerRequestBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData(view.getContext());
    }
    private  void initComponent(){
        listPlayerViewModel.getListPlayerRequest(idTeam);
        binding.recycleViewListPlayerVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListPlayerRequestVertical adapter = listPlayerViewModel.getAdapterListPlayerRequest();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListPlayerVertical.setAdapter(listPlayerViewModel.getAdapterListPlayerRequest());
    }

    private void observeLiveData(final Context context) {
        listPlayerViewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    listPlayerViewModel.getAdapterListPlayerRequest();

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, listPlayerViewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
