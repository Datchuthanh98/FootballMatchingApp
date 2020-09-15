package com.example.myclub.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myclub.R;
import com.example.myclub.model.Player;
import com.example.myclub.repository.SQLiteHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ActivityPlayerDetail extends AppCompatActivity {
    private EditText id, txtAge, txtName, txtHeight, txtWeight, txtPhone, txtEmail;
    private EditText txtPosition;
    private ImageView avatar;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private SQLiteHelper sqLiteHelper;
    private Button btnCall,btnSMS;
    public final static int REQUEST_CALL_PHONE = 10004;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_player_detail);
        Intent intent = getIntent();
        String idPlayer = intent.getStringExtra("ID");

        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        id = findViewById(R.id.txtIDPlayer);
        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtPosition = findViewById(R.id.txtPosition);
        avatar = findViewById(R.id.imgAvatar);
        btnCall = findViewById(R.id.btnCall);
        btnSMS = findViewById(R.id.btnSMS);

        Player playerExisted = sqLiteHelper.getPlayerbyID(idPlayer);
        id.setText(playerExisted.getId());
        txtName.setText(playerExisted.getName());
        txtAge.setText("" + playerExisted.getAge());
        txtHeight.setText("" + playerExisted.getHeight());
        txtWeight.setText("" + playerExisted.getWeight());
        txtEmail.setText(playerExisted.getEmail());
        txtPhone.setText(playerExisted.getPhone());
        txtPosition.setText(playerExisted.getPosition());
        String url = playerExisted.getUrlAvatar(); // /Quanao/1234.jpg
        String[] files = url.split("/");
        String fileName = files[2];
        File folder = new File(getApplicationContext().getCacheDir(), files[1]);
        if (!folder.exists()) folder.mkdir();
        final File file = new File(folder, fileName);
        if (file.exists()) {
            Picasso.get().load(file).into(avatar);
        } else {
            storage.getReference().child(url).getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Picasso.get().load(file).into(avatar);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivityPlayerDetail.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                } else {
                    doCalling();
                }
            }
        });

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SENDTO);
                intent2.putExtra("sms_body", "Hi "+txtName.getText());

                intent2.setData(Uri.parse("sms:"+txtPhone.getText()));
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Pemission granted", Toast.LENGTH_LONG);
                doCalling();
            } else {
                Toast.makeText(getApplicationContext(), "Pemission denied", Toast.LENGTH_LONG);
            }
        }
    }

    public void doCalling(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+txtPhone.getText()));
        startActivity(intent);
    }
}
