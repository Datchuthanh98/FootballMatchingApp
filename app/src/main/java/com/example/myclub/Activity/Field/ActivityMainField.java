package com.example.myclub.Activity.Field;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.Adapter.Field.AdapterFragmentField;
import com.example.myclub.Adapter.Player.AdapterFragmentProfile;
import com.example.myclub.Animation.HorizontalFlipTransformation;
import com.google.android.material.tabs.TabLayout;

public class ActivityMainField extends AppCompatActivity {
//    private String emailLogin, phoneLogin;
//    private SQLiteHelper sqLiteHelper;
//    private boolean isManager, isOnline;
//    private SharedPreferences sharedPref;
//    private SyncFireBase syncFireBase = SyncFireBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_field);
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
        AdapterFragmentField adapter = new AdapterFragmentField(manager, AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_black_24dp);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mymenu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.mLogout:
////                FirebaseAuth.getInstance().signOut();
//                break;
//            case R.id.mPhone:
//                Toast.makeText(getApplicationContext(),
//                        "Phone",Toast.LENGTH_LONG).show();
//                break;
//            case R.id.mEmail:
//                Toast.makeText(getApplicationContext(),
//                        "Email",Toast.LENGTH_LONG).show();
//                break;
//            case R.id.mExit:
//                System.exit(0);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }



}