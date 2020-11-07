package com.example.myclub.view.match.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentMainInformationMatchBinding;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.adapter.AdapterFragmentInformationMatch;
import com.example.myclub.view.player.Adapter.AdapterFragmentProfile;
import com.example.myclub.viewModel.ListMatchViewModel;
import com.example.myclub.viewModel.ProfileMatchViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.example.myclub.R.drawable.avatar_team_default;

public class FragmentMainProfileMatch extends Fragment {
    private FragmentMainInformationMatchBinding binding;
    private ProfileMatchViewModel viewModel;
    private String idMatch;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public FragmentMainProfileMatch(String idMatch)  {
        this.idMatch = idMatch;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_information_match, container, false);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileMatchViewModel.class);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        FragmentManager manager = getParentFragmentManager();
        AdapterFragmentInformationMatch adapter = new AdapterFragmentInformationMatch(getChildFragmentManager(), AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,idMatch);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        initComponent();
        observerLiveData();

    }

    private void initComponent(){
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        viewModel.getInformationMatch(idMatch);


    }

    private void observerLiveData(){
        viewModel.getMatchMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                if(match != null) {
                    binding.setMatch(match);
                if(match.getIdBooking().getIdTeamHome().getUrlAvatar() !=null)
                storageRef.child(match.getIdBooking().getIdTeamHome().getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            Picasso.get().load(uri).into(binding.avatarHome);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });

                    //set image away
//                if(match.getIdBooking().getIdTeamAway().getUrlAvatar() !=null) {
//                    storageRef.child(match.getIdBooking().getIdTeamAway().getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            if (uri != null) {
//                                Picasso.get().load(uri).into(binding.avatarAway);
//                            }
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            Picasso.get().load(avatar_team_default).into(binding.avatarAway);
//                        }
//                    });
//                }else{
//                    Picasso.get().load(avatar_team_default).into(binding.avatarAway);
//                }


                }
            }
        });


        viewModel.getMatchLoadState().observe(getViewLifecycleOwner(), new Observer<LoadingState>() {
            @Override
            public void onChanged(LoadingState loadingState) {
                if (loadingState == null) return;
                if (loadingState == LoadingState.INIT) {
                    binding.layoutLoading.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADING) {
                    binding.layoutLoading.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADED) {
                    binding.layoutLoading.setVisibility(View.GONE);
                }
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
