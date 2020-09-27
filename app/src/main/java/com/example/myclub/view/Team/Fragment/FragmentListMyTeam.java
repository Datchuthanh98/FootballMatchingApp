package com.example.myclub.view.Team.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.databinding.FragmentListMyTeamBinding;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentListMyTeam extends Fragment {
    private ViewModelTodo viewModel;
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
        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);

        binding = FragmentListMyTeamBinding.inflate(inflater);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       binding.recycleViewListTeamVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTeamVertical adapter = viewModel.getAdapterListTeam();
        adapter.fragment = getTargetFragment();
        adapter.setFm(getParentFragmentManager());
        adapter.isMy = true ;
        adapter.isShow = this.isShow;
        binding.recycleViewListTeamVertical.setAdapter(viewModel.getAdapterListTeam());

        binding.btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new FragmentAddTeamDialog();
                dialogFragment.show(getParentFragmentManager(),"Add Team Diaglog");
            }
        });



    }
}
