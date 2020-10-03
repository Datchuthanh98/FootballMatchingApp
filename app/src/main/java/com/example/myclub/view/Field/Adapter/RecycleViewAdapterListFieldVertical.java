package com.example.myclub.view.Field.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.main.ActivityHome;
import com.example.myclub.view.Field.Fragment.FragmentProfileField;
import com.example.myclub.databinding.ItemFieldVerticalBinding;
import com.example.myclub.model.Todo;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListFieldVertical extends RecyclerView.Adapter<RecycleViewAdapterListFieldVertical.MyViewHolder> {
    public Boolean isShow = false;
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
                if(isShow) {
                    ActivityHome activityHome = (ActivityHome) holder.itemView.getContext();
                    activityHome.addFragment(new FragmentProfileField());
                }else{
                    detach();
                }
            }
        });
        holder.binding.setTodo(todos.get(position));
        holder.binding.numberShirt.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void detach(){
        fm.popBackStack();
    }
}

