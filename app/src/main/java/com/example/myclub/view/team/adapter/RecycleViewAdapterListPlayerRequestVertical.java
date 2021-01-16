package com.example.myclub.view.team.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.databinding.ItemPlayerRequestVerticalBinding;
import com.example.myclub.databinding.ItemPlayerVerticalBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Player;
import com.example.myclub.view.player.Fragment.FragmentProfilePlayer;
import com.example.myclub.viewModel.ListPlayerRequestViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListPlayerRequestVertical extends RecyclerView.Adapter<RecycleViewAdapterListPlayerRequestVertical.MyViewHolder> {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FragmentManager fm;
    private List<Player> players = new ArrayList<>();
    private ListPlayerRequestViewModel listPlayerRequestViewModel ;
    public RecycleViewAdapterListPlayerRequestVertical() {

    }

    public void setViewModel  (ListPlayerRequestViewModel listPlayerRequestViewModel){
        this.listPlayerRequestViewModel = listPlayerRequestViewModel;
    }

    public RecycleViewAdapterListPlayerRequestVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListPlayer(List<Player> players){
        this.players = players;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPlayerRequestVerticalBinding binding = ItemPlayerRequestVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemPlayerRequestVerticalBinding binding;
        public MyViewHolder(ItemPlayerRequestVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.setPlayer(players.get(position));
        //Set image
        storageRef.child(players.get(position).getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(holder.binding.avatarPlayer);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        holder.binding.btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) holder.itemView.getContext();
                activityHome.addFragment(new FragmentProfilePlayer(players.get(position)));
            }
        });

        holder.binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPlayerRequestViewModel.acceptJointTeam(players.get(position).getId());
            }
        });

        holder.binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPlayerRequestViewModel.declineTeam(players.get(position).getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}

