package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class SnoozeActivity extends AppCompatActivity {
    Button snooze;
    Button dismiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        snooze = (Button)findViewById(R.id.snooze_btn);
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopRingtone = new Intent(view.getContext(), AlarmRingService.class);
                view.getContext().stopService(stopRingtone);
                setSnoozeTimer(view);
            }
        });

        dismiss = (Button)findViewById(R.id.dismiss_btn);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopRingtone = new Intent(view.getContext(), AlarmRingService.class);
                view.getContext().stopService(stopRingtone);
            }
        });
    }

    public void setSnoozeTimer(View v){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);
        int snoozeTime = 1;
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);
        minutes += snoozeTime;
        if (minutes > 59){
            hour += (minutes % 60);
            minutes = minutes - ((minutes % 60) * 60);
        }
        if (hour > 23){
            hour = 0;
        }
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minutes);
        cal_alarm.set(Calendar.SECOND, 0);

        if (cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(SnoozeActivity.this, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SnoozeActivity.this, 24444, i, 0);
        Toast.makeText(getApplicationContext(), "Alarm set", Toast.LENGTH_SHORT).show();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
    }
}
