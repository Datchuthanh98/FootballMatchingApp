package com.example.myclub.view.team.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.databinding.FragmentProfileOtherTeamBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Team;
import com.example.myclub.session.SessionUser;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterLisEvaluateVertical;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListPlayerHorizontal;
import com.example.myclub.viewModel.ProfileOtherTeamViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class FragmentProfileMainTeamOther extends Fragment {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private  Team team ;
    private  Map<String, Object> data = new HashMap<>();
    private SessionUser sessionUser = SessionUser.getInstance();
    private ProfileOtherTeamViewModel viewModel;
    public FragmentProfileMainTeamOther(Team team) {
        this.team = team;
    }
    private FragmentProfileOtherTeamBinding binding;
    public final static int REQUEST_CALL_PHONE = 10004;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileOtherTeamBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        binding.setTeam(team);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileOtherTeamViewModel.class);
        viewModel.getInformationTeam(team.getId());

        initComponent();
        observeLiveData();


    }


    private void initComponent(){

        viewModel.getMatchLoadState().observe(getViewLifecycleOwner(), new Observer<LoadingState>() {
            @Override
            public void onChanged(LoadingState loadingState) {
                if (loadingState == null) return;
                if (loadingState == LoadingState.INIT) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADING) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADED) {
                    binding.loadingLayout.setVisibility(View.GONE);
                    viewModel.getStateJoinTeam();
                }
            }
        });

        storageRef.child(team.getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        storageRef.child(team.getUrlCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addRequestJoinTeam(requestJoinTeam());
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.cancelRequestJoinTeam();
            }
        });

        binding.listEvaluateMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentListEvaluate(team.getId()));
            }
        });

        binding.matchMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentListMatchByTeam(team.getId()));
            }
        });


        binding.listPlayerMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentProfileListPlayer(team.getId()));
            }
        });

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                } else {
                    doCalling();
                }
            }
        });


        binding.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SENDTO);
                intent2.putExtra("sms_body", "Chào team ");
                intent2.setData(Uri.parse("sms:0984060798"));
                startActivity(intent2);
            }
        });

        RecycleViewAdapterListPlayerHorizontal adapterListPlayer = viewModel.getAdapterListPlayer();
        adapterListPlayer.setFm(getParentFragmentManager());
        binding.recycleViewListPlayerVertical.setAdapter(adapterListPlayer);
        binding.recycleViewListPlayerVertical.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        RecycleViewAdapterListMatchVertical adapterListMatch = viewModel.getAdapterListMatch();
        adapterListMatch.setFm(getParentFragmentManager());
        binding.recycleViewListMatchVertical.setAdapter(adapterListMatch);
        binding.recycleViewListMatchVertical.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        RecycleViewAdapterLisEvaluateVertical adapterListEvaluate = viewModel.getAdapterListEvaluate();
        adapterListEvaluate.setFm(getParentFragmentManager());
        binding.recycleViewListEvaluateVertical.setAdapter(adapterListEvaluate);
        binding.recycleViewListEvaluateVertical.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

    }


    private Map<String, Object> requestJoinTeam() {
        data.put("player", sessionUser.getPlayerLiveData().getValue().getId());
        data.put("time_create",Calendar.getInstance().getTimeInMillis());
        return data;
    }

    private void observeLiveData() {
        viewModel.getStateRequestJoinTeam().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Pemission granted", Toast.LENGTH_LONG);
                doCalling();
            } else {
                Toast.makeText(getContext(), "Pemission denied", Toast.LENGTH_LONG);
            }
        }
    }

    public void doCalling(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:0943761160"));
        startActivity(intent);
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
    }
}
