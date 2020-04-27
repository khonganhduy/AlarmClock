package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;

public class SetAlarmSettingsActivity extends AppCompatActivity implements SnoozeDialogFragment.OnCompleteListener{

    private TimePicker tp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_alarm);
        Bundle intentBundle = getIntent().getExtras();

        // MAKE FINAL IF INSIDE A LISTENER
        // Also used to initially set all of the fields in settings!
        String timeText = intentBundle.getString(AlarmListDisplayActivity.ALARM_TIME);
        final int alarm_id = intentBundle.getInt(AlarmListDisplayActivity.ALARM_ID);
        String ringtone_path = intentBundle.getString(AlarmListDisplayActivity.ALARM_RING_PATH);
        String repeatable_days = intentBundle.getString(AlarmListDisplayActivity.ALARM_REPEAT_DAYS);
        String trigger_date = intentBundle.getString(AlarmListDisplayActivity.ALARM_TRIGGER_DATE);
        boolean snooze_mode = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_SNOOZE_MODE);
        int snooze_time = intentBundle.getInt(AlarmListDisplayActivity.ALARM_SNOOZE_TIME);
        String description = intentBundle.getString(AlarmListDisplayActivity.ALARM_DESC);
        boolean vibration_on = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION);
        boolean minigame_on = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME);
        boolean alarm_on = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_ON);



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
        save.setOnClickListener(new View.OnClickListener() { // PULL DATA FROM THE SETTINGS VIEW TO UPDATE DATABASE (with updateIntent.putExtra(FIELD DEFINED IN ALARMLISTDISPLAYACTIVITY, VALUE))
            @Override
            public void onClick(View view) {
                //----------------------------
                setTimer(view);
                //---------------------------

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

                Intent updateIntent = new Intent();
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, alarm_id);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_TIME, time);
                setResult(RESULT_OK, updateIntent);
                finish();
            }
        });
        // Custom Sound Selector
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

        // Custom snooze time
        View snoozeSelector = (View) findViewById(R.id.snooze_selector);
        snoozeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnoozeDialogFragment dialogFragment = new SnoozeDialogFragment(view);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                dialogFragment.show(activity.getSupportFragmentManager(), "slider");
                /*
                Toast.makeText(SetAlarmSettingsActivity.this, "Sound Selector Clicked",
                        Toast.LENGTH_LONG).show();
                Intent soundSelectorIntent = new Intent(view.getContext() , SoundSelectorActivity.class);
                view.getContext().startActivity(soundSelectorIntent);*/

            }
        });
    }

    // Return information from fragment
    @Override
    public void onComplete(int minutes){
        TextView snoozeTime = (TextView) findViewById(R.id.second_line3);
        snoozeTime.setText(String.valueOf(minutes) + " minutes");
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
