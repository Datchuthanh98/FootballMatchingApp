package com.example.myclub.view.match.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.databinding.ItemBookingVerticalBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Booking;
import com.example.myclub.view.match.fragment.FragmentMainProfileMatch;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListBookingVertical extends RecyclerView.Adapter<RecycleViewAdapterListBookingVertical.MyViewHolder> {

    private FragmentManager fm;
    private List<Booking> bookingList = new ArrayList<>();
    public RecycleViewAdapterListBookingVertical() {

    }

    public RecycleViewAdapterListBookingVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListBooking(List<Booking> listBooking){
        this.bookingList = listBooking;
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
        Log.d("RV", "onBindViewHolder: "+position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) holder.itemView.getContext();
                activityHome.addFragment(new FragmentMainProfileMatch());
            }
        });
        holder.binding.setBooking(bookingList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}

