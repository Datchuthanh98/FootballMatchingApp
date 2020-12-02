package com.example.myclub.view.field.adapter;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Field;
import com.example.myclub.view.field.fragment.FragmentProfileField;
import com.example.myclub.databinding.ItemFieldVerticalBinding;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListFieldVertical extends RecyclerView.Adapter<RecycleViewAdapterListFieldVertical.MyViewHolder> {
    public Boolean isShow = false;
    private FragmentManager fm;
    private List<Field> fieldList = new ArrayList<>();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    public RecycleViewAdapterListFieldVertical() {

    }

    public RecycleViewAdapterListFieldVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListField(List<Field> fieldList){
        this.fieldList = fieldList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFieldVerticalBinding binding = ItemFieldVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemFieldVerticalBinding binding;
        public MyViewHolder(ItemFieldVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow) {
                    Bundle args = new Bundle();
                    FragmentProfileField fragmentProfileField = new FragmentProfileField(fieldList.get(position));
                    ActivityHome activityHome = (ActivityHome) holder.itemView.getContext();
                    activityHome.addFragment(fragmentProfileField);
                }else{

                    detach();
                }
            }
        });
        holder.binding.setField(fieldList.get(position));

        if(fieldList.get(position).getUrlAvatar() !=null) {
            storageRef.child(fieldList.get(position).getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if (uri != null) {
                        Picasso.get().load(uri).fit().centerCrop().into(holder.binding.avatarField);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }

    public void detach(){
        fm.popBackStack();
    }
}

