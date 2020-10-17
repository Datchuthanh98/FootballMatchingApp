package com.example.myclub.messagingService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.R;
import com.example.myclub.data.repository.PlayerRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingRegistrar;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private PlayerRepository playerRepository = PlayerRepository.getInstance();

    @Override
    public void onCreate() {
        // check if registration is sent to server
    }

    @Override
    public void onNewToken(String token) {
        sendRegistrationTokenToServer(token);
    }

    private void sendRegistrationTokenToServer(String token) {
        Log.d("NewToken", "sendRegistrationTokenToServer: "+token);
        Map<String, Object> updateProfileMap = new HashMap<>();
        updateProfileMap.put("registrationToken", token);
        playerRepository.updateProfile(updateProfileMap, new UpdateProfileCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Updated registration token", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData());
    }

    private void showNotification(Map<String, String> data) {
        String messageType = data.get("messageType");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "example.myapplication.service.test";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("MyClub");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        if (messageType!=null && messageType.equals("NewJoinRequest")){
            String playerId = data.get("playerId");
            String playerName = data.get("playerName");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                notificationBuilder.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_clear_black_24dp)
                        .setContentTitle(messageType)
                        .setContentText(playerName + " with id "+playerId+" just sent a join request")
                        .setContentInfo("Info");

                notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
            }
        }
        else if (messageType!= null && messageType.equals("New Member")){
            String teamName = data.get("teamName");
            String memberName = data.get("memberName");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                notificationBuilder.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_clear_black_24dp)
                        .setContentTitle(messageType)
                        .setContentText("Team "+teamName+" has a new member: "+memberName)
                        .setContentInfo("Info");

                notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
            }
        } else if (messageType != null && messageType.equals("Joined Team")){
            String teamName = data.get("teamName");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                notificationBuilder.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_clear_black_24dp)
                        .setContentTitle(messageType)
                        .setContentText("Welcome to team"+teamName)
                        .setContentInfo("Info");

                notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
            }
        }
    }
}
