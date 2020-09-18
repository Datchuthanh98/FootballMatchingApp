package com.example.myclub.Adapter.Field;

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
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListMatchVertical extends RecyclerView.Adapter<RecycleViewAdapterListMatchVertical.MyViewHolder> {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public List<Player> listPlayer = new ArrayList<>();

    public RecycleViewAdapterListMatchVertical() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_match_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

//        //Set ảnh lên item
//        String url = todos.get(position).getUrlImagePreview();
//

//        String[] files = url.split("/");
//        String fileName = files[2];
//        File folder = new File(holder.imageView.getContext().getCacheDir(), files[1]);
//        if (!folder.exists()) folder.mkdir();
//        final File file = new File(folder, fileName);
//        //kiểm tra trong cache của app có ảnh chưa , nếu có rồi thì gọi ra và set luôn lên layout
//        if (file.exists()) {
//            Picasso.get().load(file).into(holder.imageView);
//        } else {
//            //nếu chưa có thì gọi storage trong firebase để lấy ảnh lưu vào cache để lần sau load nó vào nhánh bên trên thì tối ưu
//            //sau đó lại set ảnh lên layout luôn
//            storage.getReference().child(todos.get(position).getUrlImagePreview()).getFile(file)
//                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Picasso.get().load(file).into(holder.imageView);
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }

//       holder.numberShirt.setText("10");
//       holder.namePlayer.setText("Eden Hazard");
//       holder.position.setText("Tiền vệ");
//       holder.cardView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent intent = new Intent(holder.cardView.getContext(), InformationPlayerActivity.class);
//               holder.cardView.getContext().startActivity(intent);
//           }
//       });
    }

    @Override
    public int getItemCount() {
//        return listPlayer.size();
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatarPlayer;
        public TextView namePlayer;
        public TextView position;
        public TextView numberShirt;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            cardView=itemView.findViewById(R.id.cardView);
//            avatarPlayer = itemView.findViewById(R.id.avatarPlayer);
//            namePlayer = itemView.findViewById(R.id.namePlayer);
//            position = itemView.findViewById(R.id.position);
//            numberShirt = itemView.findViewById(R.id.numberShirt);
        }
    }
}

