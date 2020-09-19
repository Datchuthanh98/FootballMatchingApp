package com.example.myclub.View.Field.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.View.Player.Adapter.RecycleViewAdapterListPlayerVertical;

public class ActivityListField extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_player);
        recyclerView = findViewById(R.id.recycleViewListPlayerVertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListPlayerVertical adapter = new RecycleViewAdapterListPlayerVertical();
        recyclerView.setAdapter(adapter);

    }
}
