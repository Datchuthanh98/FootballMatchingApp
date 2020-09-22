package com.example.myclub.View.Team.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.View.Player.Fragment.FragmentProfilePlayer;
import com.example.myclub.View.Team.Fragment.FragmentProfileTeam;
import com.example.myclub.databinding.ItemPlayerVerticalBinding;
import com.example.myclub.databinding.ItemTeamVerticalBinding;
import com.example.myclub.model.Todo;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListTeamVertical extends RecyclerView.Adapter<RecycleViewAdapterListTeamVertical.MyViewHolder> {

    private FragmentManager fm;
    private List<Todo> todos = new ArrayList<>();
    public RecycleViewAdapterListTeamVertical() {

    }

    public RecycleViewAdapterListTeamVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListTodo(List<Todo> todos){
        this.todos = todos;
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
                FragmentProfileTeam fragmentProfileTeam = new FragmentProfileTeam();
                fm.beginTransaction()
                        .add(android.R.id.content, fragmentProfileTeam, "abc")
                        .addToBackStack(null).commit();
            }
        });
        holder.binding.setTodo(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}

