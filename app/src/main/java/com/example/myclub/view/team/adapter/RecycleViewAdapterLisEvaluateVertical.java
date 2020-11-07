package com.example.myclub.view.team.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.databinding.ItemCommentVerticalBinding;
import com.example.myclub.databinding.ItemEvaluateVerticalBinding;
import com.example.myclub.model.Comment;
import com.example.myclub.model.Evaluate;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterLisEvaluateVertical extends RecyclerView.Adapter<RecycleViewAdapterLisEvaluateVertical.MyViewHolder> {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FragmentManager fm;
    private List<Evaluate> evaluates = new ArrayList<>();
    public RecycleViewAdapterLisEvaluateVertical() {

    }

    public RecycleViewAdapterLisEvaluateVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListEvaluate(List<Evaluate> listEvaluate){
        this.evaluates = listEvaluate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemEvaluateVerticalBinding binding = ItemEvaluateVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemEvaluateVerticalBinding binding;
        public MyViewHolder(ItemEvaluateVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.setEvaluate(evaluates.get(position));


        //Set image
//        storageRef.child(players.get(position).getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(holder.binding.avatarPlayer);
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
        return evaluates.size();
    }
}

