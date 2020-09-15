package com.example.myclub.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.model.Player;
import com.example.myclub.main.ActivityPlayerDetail;
import com.example.myclub.repository.RepoFireStorePlayer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterPlayer extends RecyclerView.Adapter<RecycleViewAdapterPlayer.MyViewHolder> {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public  List<Player> players = new ArrayList<>();

    private RepoFireStorePlayer repoFireStorePlayer = RepoFireStorePlayer.getInstance();
    public RecycleViewAdapterPlayer(List<Player> players) {
        this.players = players;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        String url = players.get(position).getUrlAvatar();
        String[] files = url.split("/");
        String fileName = files[2];
        File folder = new File(holder.imageView.getContext().getCacheDir(), files[1]);
        if (!folder.exists()) folder.mkdir();
        final File file = new File(folder, fileName);
        if (file.exists()) {
            Picasso.get().load(file).into(holder.imageView);
        } else {
            storage.getReference().child(players.get(position).getUrlAvatar()).getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Picasso.get().load(file).into(holder.imageView);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

//        holder.cardView.setBackgroundColor(Color.BLUE);
        holder.itemName.setText(players.get(position).getName());
        holder.itemPosition.setText("Vị trí: "+players.get(position).getPosition());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.btnDetail.getContext(), ActivityPlayerDetail.class);
                 intent.putExtra("ID",players.get(position).getId());
                 holder.btnDetail.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return players.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView itemName;
        public TextView itemPosition;
        public TextView btnDetail;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPosition = itemView.findViewById(R.id.itemPosition);
            btnDetail=itemView.findViewById(R.id.btnDetail);
        }
    }
}

