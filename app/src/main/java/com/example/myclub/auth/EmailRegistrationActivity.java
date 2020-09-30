package com.example.myclub.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myclub.Interface.RegisterCallBack;
import com.example.myclub.R;
import com.example.myclub.data.firestore.PlayerDataSource;
import com.example.myclub.databinding.ActivityEmailRegistrationBinding;
import com.example.myclub.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EmailRegistrationActivity extends AppCompatActivity {

    private ActivityEmailRegistrationBinding binding;
    private PlayerDataSource playerDataSource = PlayerDataSource.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailRegistrationActivity.this,ActivityLogin.class));
                finish();
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playerDataSource.register(binding.txtEmail.getText().toString(), binding.txtPassword.getText().toString(), new RegisterCallBack() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(EmailRegistrationActivity.this,ActivityLogin.class));
                        finish();
                    }

                    @Override
                    public void onFailure(String message) {
                         Toast.makeText(getApplicationContext(),"Error: "+message,Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
    }



}
