package com.example.myclub.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.datasource.PlayerDataSource;
import com.example.myclub.databinding.ActivityEmailRegistrationBinding;
import com.example.myclub.model.Player;

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

                playerDataSource.register(binding.txtEmail.getText().toString(), binding.txtPassword.getText().toString(),binding.txtName.getText().toString(), new CallBack<Player, String>() {
                    @Override
                    public void onSuccess(Player player) {
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
