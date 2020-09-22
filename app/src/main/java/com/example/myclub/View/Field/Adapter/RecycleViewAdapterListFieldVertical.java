package com.example.myclub.View.Field.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.View.Field.Fragment.FragmentProfileField;
import com.example.myclub.View.Team.Fragment.FragmentProfileTeam;
import com.example.myclub.databinding.ItemFieldVerticalBinding;
import com.example.myclub.databinding.ItemTeamVerticalBinding;
import com.example.myclub.model.Todo;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListFieldVertical extends RecyclerView.Adapter<RecycleViewAdapterListFieldVertical.MyViewHolder> {

    private FragmentManager fm;
    private List<Todo> todos = new ArrayList<>();
    public RecycleViewAdapterListFieldVertical() {

    }

    public RecycleViewAdapterListFieldVertical(FragmentManager fm) {
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
                FragmentProfileField fragmentProfileField = new FragmentProfileField();
                fm.beginTransaction()
                        .add(android.R.id.content, fragmentProfileField, "abc")
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

