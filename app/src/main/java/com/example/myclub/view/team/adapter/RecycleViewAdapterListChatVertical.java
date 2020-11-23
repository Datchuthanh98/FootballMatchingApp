package com.example.myclub.view.team.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.databinding.ItemChatVerticalBinding;
import com.example.myclub.databinding.ItemCommentVerticalBinding;
import com.example.myclub.model.Chat;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Player;
import com.example.myclub.session.SessionTeam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListChatVertical extends RecyclerView.Adapter<RecycleViewAdapterListChatVertical.MyViewHolder> {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FragmentManager fm;
    private List<Chat> listChat = new ArrayList<>();
    private List<Player> players = SessionTeam.getInstance().getTeamLiveData().getValue().getPlayers();
    public RecycleViewAdapterListChatVertical() {

    }

    public RecycleViewAdapterListChatVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListChat(List<Chat> listChat){
        this.listChat = listChat;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemChatVerticalBinding binding = ItemChatVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemChatVerticalBinding binding;
        public MyViewHolder(ItemChatVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.setChat(listChat.get(position));

        for(int i =0 ; i< players.size(); i++){
            if(listChat.get(position).getIdPlayer().equals( players.get(i).getId())){
                //Set image
                holder.binding.namePlayer.setText(players.get(i).getName());
                storageRef.child(players.get(i).getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(holder.binding.avatarPlayer);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
               break;
            }
        }




    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }
}

