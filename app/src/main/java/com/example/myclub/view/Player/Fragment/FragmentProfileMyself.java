package com.example.myclub.view.Player.Fragment;

import android.content.Context;
import android.content.Intent;
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

import com.example.myclub.auth.ActivityLogin;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentEditMainPlayerBinding;
import com.example.myclub.viewModel.SessionViewModel;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.databinding.FragmentProfileMyselfBinding;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;

public class FragmentProfileMyself extends Fragment {

    private FragmentProfileMyselfBinding binding;
    private SessionViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileMyselfBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(SessionViewModel.class);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentMainEditPlayer());
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getContext(), ActivityLogin.class));
                getActivity().finish();
            }
        });


        SessionViewModel.getInstance().getAvatarLiveData().observe(getViewLifecycleOwner(), new Observer<File>() {
            @Override
            public void onChanged(File file) {
                Picasso.get().load(file).into(binding.avatar);
            }
        });

        SessionViewModel.getInstance().getCoverLiveData().observe(getViewLifecycleOwner(), new Observer<File>() {
            @Override
            public void onChanged(File file) {
                Picasso.get().load(file).into(binding.cover);
            }
        });

        observeLiveData(view.getContext());


    }

    private void observeLiveData(final Context context) {
        Toast.makeText(context,"update UI image",Toast.LENGTH_SHORT).show();
        viewModel.getResultPhotoLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    Picasso.get().load(viewModel.getAvatarLiveData().getValue()).into(binding.avatar);
                    Picasso.get().load(viewModel.getCoverLiveData().getValue()).into(binding.cover);
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, viewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
     }

    }
