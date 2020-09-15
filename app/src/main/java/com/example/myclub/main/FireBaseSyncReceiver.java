package com.example.myclub.main;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myclub.R;

public class FireBaseSyncReceiver extends BroadcastReceiver {
    SyncFireBase  syncFireBase =  SyncFireBase.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentCallback = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentCallback, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, MyApplication.alarm_channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("FireBase thông báo")
                .setContentText("Cập nhập dữ liệu cầu thủ")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(2, notificationBuilder.build());

        syncFireBase.checkSync(context);
    }


}
