package com.example.myclub.view.player.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentProfilePlayerBinding;

import com.example.myclub.model.Player;
import com.example.myclub.viewModel.ProfilePlayerViewModel;
import com.example.myclub.session.SessionTeam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class FragmentProfilePlayer extends Fragment {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private Player player;
    private  Map<String, Object> data = new HashMap<>();
    private ProfilePlayerViewModel profilePlayerViewModel;
    private SessionTeam sessionTeam = SessionTeam.getInstance();
    public FragmentProfilePlayer(Player player) {
        this.player =player;
    }

    private FragmentProfilePlayerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profilePlayerViewModel = new ViewModelProvider(this).get(ProfilePlayerViewModel.class);
        binding = FragmentProfilePlayerBinding.inflate(inflater);
        binding.setPlayer(player);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData();
        profilePlayerViewModel.getStateJoinTeam(approveJoinTeam());
    }

    private void initComponent(){
        //Set image
        storageRef.child(player.getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(binding.avatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        //Set image
        storageRef.child(player.getUrlCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(binding.cover);
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


        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 profilePlayerViewModel.acceptJoinTeam(approveJoinTeam());
            }
        });

        binding.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePlayerViewModel.declineJoinTeam(approveJoinTeam());
            }
        });
    }

    private Map<String, Object> approveJoinTeam() {
        data.put("team", sessionTeam.getTeamLiveData().getValue().getId());
        data.put("player",player.getId());
        return data;
    }

    private void observeLiveData() {
        profilePlayerViewModel.getStateRequestJoinTeam().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean stateApprovalJoinTeam) {
                if(stateApprovalJoinTeam == true){
                    binding.btnDecline.setVisibility(View.VISIBLE);
                    binding.btnAccept.setVisibility(View.VISIBLE);
                    binding.btnInvite.setVisibility(View.GONE);
                }else{
                    binding.btnInvite.setVisibility(View.GONE);
                    binding.btnDecline.setVisibility(View.GONE);
                    binding.btnAccept.setVisibility(View.GONE);
                }
            }
        });
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
    }


}
