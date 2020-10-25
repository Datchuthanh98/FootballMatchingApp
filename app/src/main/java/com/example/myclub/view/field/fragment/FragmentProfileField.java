package com.example.myclub.view.field.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentProfileFieldBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Field;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.view.match.fragment.FragmentAddMatch;
import com.example.myclub.viewModel.ProfileFieldViewModel;

public class FragmentProfileField extends Fragment {
    private ProfileFieldViewModel viewModel;
    FragmentProfileFieldBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileFieldBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ProfileFieldViewModel.class);
        Field field = (Field) getArguments().getSerializable("field");
        viewModel.setField(field);

        binding.setField(field);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel.getlistTime();
        super.onViewCreated(view, savedInstanceState);
        binding.recycleViewListTimeVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTimeVertical adapter = viewModel.getAdapterListTimeVertical();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTimeVertical.setAdapter(viewModel.getAdapterListTimeVertical());

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentAddMatch());

            }
        });
    }
    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }
}
