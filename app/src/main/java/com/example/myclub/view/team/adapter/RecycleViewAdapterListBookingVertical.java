package com.example.myclub.view.team.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.databinding.ItemBookingVerticalBinding;
import com.example.myclub.databinding.ItemMatchVerticalBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Booking;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.fragment.FragmentMainProfileMatch;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import com.example.managefield.view.Fragment.FragmentMainProfileMatch;


public class RecycleViewAdapterListBookingVertical extends RecyclerView.Adapter<RecycleViewAdapterListBookingVertical.MyViewHolder> {
    private FragmentManager fm;
    private List<Booking> bookingList = new ArrayList<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public RecycleViewAdapterListBookingVertical() {
    }

    public RecycleViewAdapterListBookingVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListMatch(List<Booking> bookingList){
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBookingVerticalBinding binding = ItemBookingVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemBookingVerticalBinding binding;
        public MyViewHolder(ItemBookingVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bookingList.get(position).getDate());
        int pYear=calendar.get(Calendar.YEAR);
        int pMonth=calendar.get(Calendar.MONTH);
        int pDay=calendar.get(Calendar.DAY_OF_MONTH);
        String startTime =bookingList.get(position).getStartTime()+"h";
        String endTime = bookingList.get(position).getEndTime()+"h";
        String timeDate = pDay+"/"+(pMonth+1)+"/"+pYear+","+startTime+"-"+endTime;

       String nameField = bookingList.get(position).getIdField().getName();
       String addressField= bookingList.get(position).getIdField().getAddress();
       String positionField = bookingList.get(position).getPosition();



        holder.binding.txtField.setText("Sân "+nameField+ " , ô số "+positionField+ ",địa chỉ : "+addressField );
        holder.binding.txtTime.setText(timeDate);


        if(bookingList.get(position).getIdTeamHome() !=null) {
            storageRef.child(bookingList.get(position).getIdTeamHome().getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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


        if(bookingList.get(position).getIdTeamAway() !=null) {
            holder.binding.avatarAway.setImageResource(R.drawable.avatar_team_default);
            storageRef.child(bookingList.get(position).getIdTeamAway().getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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


        if(bookingList.get(position).getApprove() == null){
            holder.binding.status.setText("Chưa phê duyệt");
        }else if(bookingList.get(position).getApprove() == true){
            holder.binding.status.setText("Đã chấp nhận");
        }else if(bookingList.get(position).getApprove() == false) {
            holder.binding.status.setText("Đã từ chối");
        }
        holder.binding.setBooking(bookingList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}

