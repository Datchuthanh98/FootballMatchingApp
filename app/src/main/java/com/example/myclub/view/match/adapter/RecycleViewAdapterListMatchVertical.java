package com.example.myclub.view.match.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.databinding.ItemMatchVerticalBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.fragment.FragmentMainProfileMatch;
import com.example.myclub.viewModel.ListMatchViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import com.example.managefield.view.Fragment.FragmentMainProfileMatch;


public class RecycleViewAdapterListMatchVertical extends RecyclerView.Adapter<RecycleViewAdapterListMatchVertical.MyViewHolder> {
    private FragmentManager fm;
    private List<Match> matches = new ArrayList<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public RecycleViewAdapterListMatchVertical() {
    }

    public RecycleViewAdapterListMatchVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListMatch(List<Match> listMatch){
        this.matches = listMatch;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMatchVerticalBinding binding = ItemMatchVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemMatchVerticalBinding binding;
        public MyViewHolder(ItemMatchVerticalBinding binding) {
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
                activityHome.addFragment(new FragmentMainProfileMatch(matches.get(position).getId()));
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(matches.get(position).getIdBooking().getDate());
        int pYear=calendar.get(Calendar.YEAR);
        int pMonth=calendar.get(Calendar.MONTH);
        int pDay=calendar.get(Calendar.DAY_OF_MONTH);
        String startTime = matches.get(position).getIdBooking().getIdTimeGame().getStartTime()+"h";
        String endTime = matches.get(position).getIdBooking().getIdTimeGame().getEndTime()+"h";
        String timeDate = pDay+"/"+(pMonth+1)+"/"+pYear+","+startTime+"-"+endTime;

       String nameField = matches.get(position).getIdBooking().getIdField().getName();
       String addressField= matches.get(position).getIdBooking().getIdField().getAddress();
       String positionField = matches.get(position).getIdBooking().getIdTimeGame().getPosition();



        holder.binding.txtField.setText("Sân "+nameField+ " , ô số "+positionField+ ",địa chỉ : "+addressField );
        holder.binding.txtTime.setText(timeDate);


        if(matches.get(position).getIdBooking().getIdTeamHome() !=null) {
            storageRef.child(matches.get(position).getIdBooking().getIdTeamHome().getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if (uri != null) {
                        Picasso.get().load(uri).fit().centerCrop().into(holder.binding.avatarHome);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }


        if(matches.get(position).getIdBooking().getIdTeamAway() !=null) {
            holder.binding.avatarAway.setImageResource(R.drawable.avatar_team_default);
            storageRef.child(matches.get(position).getIdBooking().getIdTeamAway().getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if (uri != null) {
                        Picasso.get().load(uri).fit().centerCrop().into(holder.binding.avatarAway);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }
        holder.binding.setMatch(matches.get(position));
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}

