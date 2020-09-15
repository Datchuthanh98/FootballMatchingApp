package com.example.myclub.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myclub.Interface.OnGetLasttimeUpdateCallback;
import com.example.myclub.Interface.PlayerConnectFirebase;
import com.example.myclub.model.Player;
import com.example.myclub.repository.RepoFireStorePlayer;
import com.example.myclub.repository.SQLiteHelper;

import java.util.List;

public class SyncFireBase {
    static SyncFireBase instance;
    private RepoFireStorePlayer repoFireStorePlayer = RepoFireStorePlayer.getInstance();
    private SQLiteHelper sqLiteHelper;
    private SharedPreferences sharedPref;


    public static SyncFireBase getInstance() {
        if (instance == null) {
            instance = new SyncFireBase();
        }
        return instance;
    }


    public void checkSync(final Context context) {
        if (sqLiteHelper == null) sqLiteHelper = new SQLiteHelper(context);
        sharedPref = context.getSharedPreferences("last_update", Context.MODE_PRIVATE);
        final long currentTimestamp = sharedPref.getLong("last_updated_timestamp", 0);
        repoFireStorePlayer.getLastUpdateTimestamp(new OnGetLasttimeUpdateCallback() {
            @Override
            public void onCallBack(long timestamp) {
                if (currentTimestamp == timestamp) {
                    // do nothing
                } else {
                    synchronizeFireStore(context);
                    updateCurrentTimestamp(timestamp);
                }
            }
        });
    }

    public void synchronizeFireStore(final Context context) {
        Toast.makeText(context, "Sync", Toast.LENGTH_SHORT).show();
        if (sqLiteHelper == null) sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.resetPlayer();
        repoFireStorePlayer.loadPlayer(new PlayerConnectFirebase() {
            @Override
            public void onPlayerLoadedFromFireBase(List<Player> players) {
                for (int i = 0; i < players.size(); i++) { sqLiteHelper.insertPlayer(players.get(i));
                }
                // set lại timestamp vào shared preference
                ObserverManager.getInstance().data.setValue((int) 1);
            }
        });


    }

    public void updateCurrentTimestamp(long timestamp) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("last_updated_timestamp", timestamp);
        editor.apply();
    }
}
