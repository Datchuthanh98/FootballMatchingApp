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
import com.example.myclub.databinding.FragmentListOtherTeamBinding;;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListMyTeamViewModel;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentListOtherTeam extends Fragment {
    private ListMyTeamViewModel listMyTeamViewModel = ListMyTeamViewModel.getInstance();
    private FragmentListOtherTeamBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listMyTeamViewModel = new ViewModelProvider(this).get(ListMyTeamViewModel.class);
        binding = FragmentListOtherTeamBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData(view.getContext());


    }

    private  void initComponent(){
        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTeamVertical adapter = listMyTeamViewModel.getAdapterListOtherTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTeamVertical.setAdapter(listMyTeamViewModel.getAdapterListOtherTeam());
    }

    private void observeLiveData(final Context context) {
        listMyTeamViewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    Toast.makeText(context, "List get new team", Toast.LENGTH_SHORT).show();
                    listMyTeamViewModel.getAdapterListOtherTeam();

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, listMyTeamViewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
