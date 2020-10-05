package com.example.myclub.view.Team.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Team;
import com.example.myclub.view.Player.Fragment.FragmentEditPlayerPlayer;
import com.example.myclub.view.Team.Fragment.FragmentMainProfileTeam;
import com.example.myclub.databinding.ItemTeamVerticalBinding;
import com.example.myclub.model.Todo;
import com.example.myclub.viewModel.ListMyTeamViewModel;
import com.example.myclub.viewModel.TeamViewModel;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListTeamVertical extends RecyclerView.Adapter<RecycleViewAdapterListTeamVertical.MyViewHolder> {

    private FragmentManager fm;
    private List<Team> listTeam = new ArrayList<>();
    public Boolean isMy = false;
    public Boolean isShow = true;
    public Fragment fragment;
    public RecycleViewAdapterListTeamVertical() {

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
        ItemTeamVerticalBinding binding = ItemTeamVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemTeamVerticalBinding binding;
        public MyViewHolder(ItemTeamVerticalBinding binding) {
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
                        TeamViewModel.getInstance().loadTeam(listTeam.get(position).getId());
                        activityHome.addFragment(new FragmentMainProfileTeam());
                    }else {
                        //Select Team
                      detach();
                    }
                }else{
                    activityHome.addFragment(new FragmentMainProfileTeam());}
            }
        });
        holder.binding.setTeam(listTeam.get(position));
    }

    @Override
    public int getItemCount() {
        return listTeam.size();
    }

    private void detach() {
        fm.popBackStack();
    }
}

