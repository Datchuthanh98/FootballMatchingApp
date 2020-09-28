package com.example.myclub.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myclub.R;
import com.hbb20.CountryCodePicker;

public class PhoneLoginActivity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText number;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        countryCodePicker = findViewById(R.id.ccp);
        number = findViewById(R.id.editText_carrierNumber);
        next = findViewById(R.id.next);
        countryCodePicker.registerCarrierNumberEditText(number);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(TextUtils.isEmpty(number.getText().toString())){
                   Toast.makeText(PhoneLoginActivity.this, "Enter No ....", Toast.LENGTH_SHORT).show();
               }
               else if(number.getText().toString().replace(" ","").length()!=10){
                   Toast.makeText(PhoneLoginActivity.this, "Enter Correct No ...", Toast.LENGTH_SHORT).show();
               }
               else {
                  Intent intent = new Intent(PhoneLoginActivity.this,VerificationActivity.class);
                  intent.putExtra("number",countryCodePicker.getFullNumberWithPlus().replace(" ",""));
                  startActivity(intent);
                  finish();
               }
            }
        });


    }




}
