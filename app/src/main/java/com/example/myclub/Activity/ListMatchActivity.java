package com.example.myclub.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.adapter.RecycleViewAdapterListPlayerVertical;

public class ListMatchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.list_match_activity);
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.recycleViewListMatchVertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListMatchVertical adapter = new RecycleViewAdapterListMatchVertical();
        recyclerView.setAdapter(adapter);

    }
}
