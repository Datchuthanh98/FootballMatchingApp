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

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentProfilePlayerBinding;
import com.example.myclub.model.Player;
import com.example.myclub.viewModel.RequestJoinTeamViewModel;
import com.example.myclub.viewModel.TeamViewModel;
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
    private RequestJoinTeamViewModel requestJoinTeamViewModel = RequestJoinTeamViewModel.getInstance();
    private TeamViewModel teamViewModel = TeamViewModel.getInstance();

    public FragmentProfilePlayer(Player player) {
        this.player =player;
    }

    private FragmentProfilePlayerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_player, container, false);
        binding.setPlayer(player);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        observeLiveData();
        requestJoinTeamViewModel.getStateJoinTeam(approveJoinTeam());
    }

    private void initComponent(){
        //Set image
        storageRef.child(player.getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        storageRef.child(player.getUrlCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        binding.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Invited Player "+requestJoinTeamViewModel.getKey(),Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 requestJoinTeamViewModel.acceptJoinTeam(approveJoinTeam());
            }
        });

        binding.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestJoinTeamViewModel.declineJoinTeam(approveJoinTeam());
            }
        });
    }

    private Map<String, Object> approveJoinTeam() {
        data.put("idTeam",teamViewModel.getTeamLiveData().getValue().getId());
        data.put("idPlayer",player.getId());
        data.put("key",requestJoinTeamViewModel.getKey());
        return data;
    }

    private void observeLiveData() {
        requestJoinTeamViewModel.getStateRequestJoinTeam().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
