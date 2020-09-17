package com.example.myclub.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.adapter.AdapterFragment;
import com.example.myclub.model.Player;
import com.example.myclub.repository.SQLiteHelper;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    private String emailLogin, phoneLogin;
//    private SQLiteHelper sqLiteHelper;
//    private boolean isManager, isOnline;
//    private SharedPreferences sharedPref;
//    private SyncFireBase syncFireBase = SyncFireBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_profile);
        super.onCreate(savedInstanceState);

//        sharedPref = getApplicationContext().getSharedPreferences("sessionUser", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        sqLiteHelper = new SQLiteHelper(getApplicationContext());
//        Bundle extra = getIntent().getExtras();
//        Player player = null;
//
//        String emailLogin = sharedPref.getString("session", "");
//        if (!(emailLogin.equals(""))) {
//            player = sqLiteHelper.getPlayerbyEmail(emailLogin);
//            isOnline=true;
//        } else {
//           isOnline=false;
//        }
//
//        if (player != null) {
//            isManager = false;
//            editor.putString("idPlayer", player.getId());
//            editor.apply();
//        } else {
//            isManager = true;
//        }

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new HorizontalFlipTransformation());
        FragmentManager manager = getSupportFragmentManager();
        AdapterFragment adapter = new AdapterFragment(manager, AdapterFragment.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_clear_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_backspace_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_clear_black_24dp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mLogout:
//                FirebaseAuth.getInstance().signOut();
                break;
            case R.id.mPhone:
                Toast.makeText(getApplicationContext(),
                        "Phone",Toast.LENGTH_LONG).show();
                break;
            case R.id.mEmail:
                Toast.makeText(getApplicationContext(),
                        "Email",Toast.LENGTH_LONG).show();
                break;
            case R.id.mExit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}