package com.example.myclub.frangment.origin;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myclub.R;
import com.example.myclub.auth.LoginActivity;
import com.example.myclub.main.FireBaseSyncReceiver;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
public class FragmentSetting extends Fragment {
    private EditText realTimeEt;
    private Button realTimeBt;
    private EditText realTimeRepeatEt;
    private Button realTimeRepeatBt;
    private Button stopRealTimeBt;
    private Button btnSingout;

    private AlarmManager alarmManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_setting_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realTimeEt = view.findViewById(R.id.real_time_edit_text);
        realTimeBt = view.findViewById(R.id.real_time_button);
        realTimeRepeatEt = view.findViewById(R.id.repeat_real_time_edit_text);
        realTimeRepeatBt = view.findViewById(R.id.repeat_real_time_button);
        stopRealTimeBt = view.findViewById(R.id.stop_real_time_button);
        btnSingout= view.findViewById(R.id.btnSignOut);

        alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

        realTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Dialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeString = hourOfDay + ":" + minute;
                        realTimeEt.setText(timeString);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });



        realTimeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                try {
                    String[] timeString = realTimeEt.getText().toString().split(":");
                    int hour = Integer.parseInt(timeString[0]);
                    int minute = Integer.parseInt(timeString[1]);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Can't parse value", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), FireBaseSyncReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(getContext(), "Alarm started", Toast.LENGTH_SHORT).show();
            }
        });



        realTimeRepeatBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double secondsToRepeat;
                Calendar calendar = Calendar.getInstance();
                try {
                    String[] timeString = realTimeEt.getText().toString().split(":");
                    int hour = Integer.parseInt(timeString[0]);
                    int minute = Integer.parseInt(timeString[1]);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    secondsToRepeat = Double.parseDouble(realTimeRepeatEt.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Can't parse value", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), FireBaseSyncReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (long) (secondsToRepeat * 1000), pendingIntent);
                Toast.makeText(getContext(), "Repeating alarm started", Toast.LENGTH_SHORT).show();
            }
        });



        stopRealTimeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FireBaseSyncReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getContext(), "Stopped repeating alarm", Toast.LENGTH_SHORT).show();
            }
        });


        btnSingout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }
}
