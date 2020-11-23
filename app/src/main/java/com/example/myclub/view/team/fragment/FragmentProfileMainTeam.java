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

import com.example.myclub.R;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentProfileMyTeamBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.view.match.fragment.FragmentListMatchByTeam;
import com.example.myclub.view.player.Adapter.AdapterFragmentProfile;
import com.example.myclub.view.team.adapter.AdapterFragmentProfileMyTeam;
import com.example.myclub.session.SessionTeam;
import com.squareup.picasso.Picasso;

public class FragmentProfileMainTeam extends Fragment {

    private  String idTeam;
    public FragmentProfileMainTeam(String idTeam) {
        this.idTeam = idTeam;
    }
    FragmentProfileMyTeamBinding binding;
    private SessionTeam sessionTeam = SessionTeam.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileMyTeamBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdapterFragmentProfileMyTeam adapter = new AdapterFragmentProfileMyTeam(getChildFragmentManager(), AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpager.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);
        initComponent();
        observeLiveData(view.getContext());

    }

    private  void initComponent(){
//        sessionTeam.loadChat(idTeam);

        SessionTeam.getInstance().loadTeam(idTeam);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentEditMainTeam());
            }
        });

        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentListChat(idTeam));
            }
        });

        binding.btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentListMatchByTeam(idTeam));
            }
        });




    }


    private void observeLiveData(final Context context) {
        sessionTeam.getTeamLoadState().observe(getViewLifecycleOwner(), new Observer<LoadingState>() {
            @Override
            public void onChanged(LoadingState loadingState) {
                if (loadingState == null) return;
                if (loadingState == LoadingState.INIT) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADING) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADED) {
                    binding.loadingLayout.setVisibility(View.GONE);
                }
            }
        });



        sessionTeam.getResultPhotoLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    if (sessionTeam.getAvatarLiveData().getValue() != null)
                    Picasso.get().load(sessionTeam.getAvatarLiveData().getValue()).fit().centerCrop().into(binding.avatar);
                    if (sessionTeam.getCoverLiveData().getValue() != null)
                    Picasso.get().load(sessionTeam.getCoverLiveData().getValue()).fit().centerCrop().into(binding.cover);

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, sessionTeam.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    private void detach() {
        getParentFragmentManager().popBackStack();
    }
}
