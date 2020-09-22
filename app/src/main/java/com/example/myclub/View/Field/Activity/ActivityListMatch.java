package com.example.myclub.View.Field.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.View.Field.Adapter.RecycleViewAdapterListMatchVertical;

public class ActivityListMatch extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list_match);
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.recycleViewListMatchVertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListMatchVertical adapter = new RecycleViewAdapterListMatchVertical(getSupportFragmentManager());
        recyclerView.setAdapter(adapter);

    }
}
