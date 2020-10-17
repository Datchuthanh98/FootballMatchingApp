package com.example.myclub.view.Team.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentListMyTeamBinding;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ListMyTeamViewModel;
import com.example.myclub.viewModel.SessionUser;

public class FragmentListMyTeam extends Fragment {
    private ListMyTeamViewModel listMyTeamViewModel = ListMyTeamViewModel.getInstance();
    private SessionUser sessionUser = SessionUser.getInstance();
    private FragmentListMyTeamBinding binding;
    public boolean isShow = true ;

    public FragmentListMyTeam( boolean isShow) {
        this.isShow = isShow;
    }

    public FragmentListMyTeam( ) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listMyTeamViewModel = new ViewModelProvider(this).get(ListMyTeamViewModel.class);
        binding = FragmentListMyTeamBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observerLiveDate();
    }

    private void observerLiveDate() {
        listMyTeamViewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    listMyTeamViewModel.getListTeam(sessionUser.getPlayerLiveData().getValue().getId());
//                    Toast.makeText(context, "List get new team", Toast.LENGTH_SHORT).show();

                } else if (result == Result.FAILURE) {
                    Toast.makeText(getContext(), listMyTeamViewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void initComponent(){
        listMyTeamViewModel.getListTeam(sessionUser.getPlayerLiveData().getValue().getId());
        binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTeamVertical adapter = listMyTeamViewModel.getAdapterListTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        adapter.isMy = true ;
        adapter.isShow = this.isShow;
        binding.recycleViewListTeamVertical.setAdapter(listMyTeamViewModel.getAdapterListTeam());
        binding.btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new FragmentAddTeamDialog();
                dialogFragment.show(getParentFragmentManager(),"Add Team Diaglog");
            }
        });

    }




}
