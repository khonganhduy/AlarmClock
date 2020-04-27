package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;

public class SetAlarmSettingsActivity extends AppCompatActivity {

    private TimePicker tp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_alarm);
        Bundle intentBundle = getIntent().getExtras();
        String timeText = intentBundle.getString("time");
        final int alarm_id = intentBundle.getInt("id");

        tp = (TimePicker) findViewById(R.id.time_picker);

        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        try {
            Date date = dateFormat.parse(timeText);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            tp.setHour(c.get(Calendar.HOUR_OF_DAY));
            tp.setMinute(c.get(Calendar.MINUTE));

        } catch (ParseException e) {
            // Do nothing, this exception does not affect the overall program
        }
        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent returnIntent = new Intent(view.getContext(), AlarmListDisplayActivity.class);
                startActivity(returnIntent);*/
                setResult(RESULT_CANCELED);
            }
        });
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------
                setTimer(view);
                //---------------------------

                // TODO Schedule alarm and save data to display on main activity
                String time;
                String mins = Integer.toString(tp.getMinute());
                if(tp.getMinute() < 10){
                    mins = "0" + mins;
                }
                if(tp.getHour() > 12) {
                    time = (tp.getHour() - 12) + ":" + mins + " PM";
                }
                else{
                    if(tp.getHour() == 0) {
                        time = "12:" + mins + " AM";
                    }
                    else{
                        time = tp.getHour() + ":" + mins + " AM";
                    }
                }
                /*Intent returnIntent = new Intent(view.getContext(), AlarmListDisplayActivity.class);
                returnIntent.putExtra("time", time);
                returnIntent.putExtra("position", position);
                startActivity(returnIntent);*/
                Intent updateIntent = new Intent();
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, alarm_id);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_TIME, time);
                setResult(RESULT_OK, updateIntent);
                finish();
            }
        });
        //---------------------------------------------------
        View soundSelector = (View) findViewById(R.id.sound_selector);
        soundSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SetAlarmSettingsActivity.this, "Sound Selector Clicked",
                        Toast.LENGTH_LONG).show();
                Intent soundSelectorIntent = new Intent(view.getContext() , SoundSelectorActivity.class);
                view.getContext().startActivity(soundSelectorIntent);
            }
        });
    }

    //------------------------------------------------------------------
    // CURRENTLY WORKING METHOD TO SET AN ALARM
    public void setTimer(View v){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, tp.getHour());
        cal_alarm.set(Calendar.MINUTE, tp.getMinute());
        cal_alarm.set(Calendar.SECOND, 0);

        if (cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(SetAlarmSettingsActivity.this, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SetAlarmSettingsActivity.this, 24444, i, 0);
        Toast.makeText(getApplicationContext(), "Alarm set", Toast.LENGTH_SHORT).show();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);


    }
}
