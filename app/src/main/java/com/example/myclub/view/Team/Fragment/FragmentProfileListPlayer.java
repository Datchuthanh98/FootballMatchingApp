package com.example.myclub.view.Team.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentListMyTeamBinding;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.databinding.FragmentListPlayerBinding;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListMyTeamViewModel;
import com.example.myclub.viewModel.ListPlayerViewModel;
import com.example.myclub.viewModel.TeamViewModel;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentProfileListPlayer extends Fragment {
    private ListPlayerViewModel listPlayerViewModel = ListPlayerViewModel.getInstance();
    private TeamViewModel teamViewModel = TeamViewModel.getInstance();
    private FragmentListPlayerBinding binding;
    private String idTeam;

    public FragmentProfileListPlayer(String idTeam) {
        this.idTeam = idTeam;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listPlayerViewModel = new ViewModelProvider(this).get(ListPlayerViewModel.class);
        binding = FragmentListPlayerBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData(view.getContext());
    }
    private  void initComponent(){
        listPlayerViewModel.getListPlayer(idTeam);
        binding.recycleViewListPlayerVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        RecycleViewAdapterListPlayerVertical adapter = listPlayerViewModel.getAdapterListPlayer();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListPlayerVertical.setAdapter(listPlayerViewModel.getAdapterListPlayer());
    }

    private void observeLiveData(final Context context) {
        listPlayerViewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    listPlayerViewModel.getAdapterListPlayer();

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, listPlayerViewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
