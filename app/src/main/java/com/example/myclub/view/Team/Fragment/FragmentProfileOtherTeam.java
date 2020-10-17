package com.example.myclub.view.Team.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentProfileOtherTeamBinding;
import com.example.myclub.model.Team;
import com.example.myclub.viewModel.SessionUser;
import com.example.myclub.viewModel.RequestJoinTeamViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class FragmentProfileOtherTeam extends Fragment {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private  Team team ;
    private  Map<String, Object> data = new HashMap<>();
    private SessionUser sessionUser = SessionUser.getInstance();
    private RequestJoinTeamViewModel requestJoinTeamViewModel = RequestJoinTeamViewModel.getInstance();

    public FragmentProfileOtherTeam(Team team) {
        this.team = team;
    }


    private FragmentProfileOtherTeamBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile_other_team,container,false);
        binding.setTeam(team);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData();
        requestJoinTeamViewModel.getStateJoinTeam(requestJoinTeam());

    }


    private void initComponent(){
        //Set image
        storageRef.child(team.getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(binding.avatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        //Set image
        storageRef.child(team.getUrlCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(binding.cover);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });

        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestJoinTeamViewModel.addRequestJoinTeam(requestJoinTeam());
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestJoinTeamViewModel.cancelRequestJoinTeam(requestJoinTeamViewModel.getKey());
            }
        });

    }


    private Map<String, Object> requestJoinTeam() {
        data.put("idPlayer", sessionUser.getPlayerLiveData().getValue().getId());
        data.put("idTeam",team.getId());
        data.put("isPlayerRequest",true);
        return data;
    }

    private void observeLiveData() {
        requestJoinTeamViewModel.getStateRequestJoinTeam().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean requestJoined) {
              if(requestJoined == true){
                  binding.btnJoin.setVisibility(View.GONE);
                  binding.btnCancel.setVisibility(View.VISIBLE);
              }else{
                  binding.btnJoin.setVisibility(View.VISIBLE);
                  binding.btnCancel.setVisibility(View.GONE);
              }
            }
        });
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
    }
}
