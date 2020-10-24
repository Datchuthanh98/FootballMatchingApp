package com.example.myclub.view.match.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.databinding.ItemTeamQueueVerticalBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Team;
import com.example.myclub.view.team.fragment.FragmentProfileMainTeam;
import com.example.myclub.view.team.fragment.FragmentProfileOtherTeam;
import com.example.myclub.viewModel.ShareExecuteJoinMatchViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListQueueTeamVertical extends RecyclerView.Adapter<RecycleViewAdapterListQueueTeamVertical.MyViewHolder> {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FragmentManager fm;
    private List<Team> listTeam = new ArrayList<>();
    public Boolean isMy = true;
    public Boolean isShow = true;
    public Fragment fragment;;

    public ShareExecuteJoinMatchViewModel executeTeamViewModel;





    public void setExecuteViewModel(ShareExecuteJoinMatchViewModel executeTeamViewModel) {
        this.executeTeamViewModel=executeTeamViewModel;
    }


    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListTeam(List<Team> listTeam){
        this.listTeam = listTeam;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTeamQueueVerticalBinding binding = ItemTeamQueueVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemTeamQueueVerticalBinding binding;
        public MyViewHolder(ItemTeamQueueVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) holder.itemView.getContext();
                if(isMy){
                    if(isShow){
                        activityHome.addFragment(new FragmentProfileMainTeam(listTeam.get(position).getId()));
                    }else {

                    }
                }else{
                    activityHome.addFragment(new FragmentProfileOtherTeam(listTeam.get(position)));}
            }
        });


        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeTeamViewModel.setIdTeam(listTeam.get(position).getId());
               }
        });




        holder.binding.setTeam(listTeam.get(position));

         //Set image
//        storageRef.child(listTeam.get(position).getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(holder.binding.avatarTeam);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listTeam.size();
    }

    private void detach() {
        fm.popBackStack();
    }
}

