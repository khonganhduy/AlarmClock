package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class SnoozeActivity extends AppCompatActivity {
    Button snooze;
    Button dismiss;
    private Bundle myInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myInput = this.getIntent().getExtras();
        setContentView(R.layout.activity_snooze);
        snooze = (Button)findViewById(R.id.snooze_btn);
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopRingtone = new Intent(view.getContext(), AlarmRingService.class);
                view.getContext().stopService(stopRingtone);
                setSnoozeTimer(view);
                Intent intent = new Intent(getApplicationContext(), AlarmListDisplayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        dismiss = (Button)findViewById(R.id.dismiss_btn);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopRingtone = new Intent(view.getContext(), AlarmRingService.class);
                view.getContext().stopService(stopRingtone);
                Intent intent = new Intent(getApplicationContext(), AlarmListDisplayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);
        Log.d("DEBUG", "SNOOZE TIME: " + String.valueOf(myInput.getInt(AlarmListDisplayActivity.ALARM_SNOOZE_TIME)));
        minutes += myInput.getInt(AlarmListDisplayActivity.ALARM_SNOOZE_TIME);
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

        Intent i = new Intent(v.getContext(), AlarmBroadcastReceiver.class);

        int alarmId = myInput.getInt(AlarmListDisplayActivity.ALARM_ID);
        i.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, myInput.getInt(AlarmListDisplayActivity.ALARM_SNOOZE_TIME));
        i.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH,  myInput.getString(AlarmListDisplayActivity.ALARM_RING_PATH));
        i.putExtra(AlarmListDisplayActivity.ALARM_ID, alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(), alarmId, i, 0);
        Toast.makeText(getApplicationContext(), "Alarm set", Toast.LENGTH_SHORT).show();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
    }
}
