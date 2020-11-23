package com.example.myclub.view.team.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Player;
import com.example.myclub.view.player.Fragment.FragmentProfilePlayer;
import com.example.myclub.databinding.ItemPlayerVerticalBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListPlayerVertical extends RecyclerView.Adapter<RecycleViewAdapterListPlayerVertical.MyViewHolder> {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FragmentManager fm;
    private List<Player> players = new ArrayList<>();
    public RecycleViewAdapterListPlayerVertical() {

    }

    public RecycleViewAdapterListPlayerVertical(FragmentManager fm) {
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
        ItemPlayerVerticalBinding binding = ItemPlayerVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemPlayerVerticalBinding binding;
        public MyViewHolder(ItemPlayerVerticalBinding binding) {
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
                activityHome.addFragment(new FragmentProfilePlayer(players.get(position)));
            }
        });
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

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}

