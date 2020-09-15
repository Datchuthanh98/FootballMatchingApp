package com.example.myclub.frangment.origin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.R;
import com.example.myclub.model.Player;
import com.example.myclub.adapter.RecycleViewAdapterPlayer;
import com.example.myclub.repository.SQLiteHelper;
import com.example.myclub.main.ObserverManager;
import com.example.myclub.main.SyncFireBase;

import java.util.List;

public class FragmentListPlayer extends Fragment {
    private RecyclerView recyclerView;
    private EditText txtName;
    private SQLiteHelper sqLiteHelper;
   private Button btnSync;
   private SyncFireBase syncFireBase =  SyncFireBase.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_player_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqLiteHelper = new SQLiteHelper(getContext());
        txtName = view.findViewById(R.id.txtName);
        btnSync =view.findViewById(R.id.btnSync);
        recyclerView = view.findViewById(R.id.recycleViewListCardPlayer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Player> players = sqLiteHelper.getAllPlayer();
        RecycleViewAdapterPlayer adapter = new RecycleViewAdapterPlayer(players);
        recyclerView.setAdapter(adapter);

        ObserverManager.getInstance().data.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                List<Player> players = sqLiteHelper.getAllPlayer();
                RecycleViewAdapterPlayer adapter = new RecycleViewAdapterPlayer(players);
                recyclerView.setAdapter(adapter);
            }
        });

        ObserverManager.getInstance().nameSearh.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
               if(s.equals("")){
                   List<Player> players = sqLiteHelper.getAllPlayer();
                   RecycleViewAdapterPlayer adapter = new RecycleViewAdapterPlayer(players);
                   recyclerView.setAdapter(adapter);
               }else{
                   List<Player> players = sqLiteHelper.getListPlayerbyName(s);
                   RecycleViewAdapterPlayer adapter = new RecycleViewAdapterPlayer(players);
                   recyclerView.setAdapter(adapter);
               }
            }
        });

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               ObserverManager.getInstance().nameSearh.setValue(txtName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncFireBase.synchronizeFireStore(getContext());
            }
        });

    }


}
