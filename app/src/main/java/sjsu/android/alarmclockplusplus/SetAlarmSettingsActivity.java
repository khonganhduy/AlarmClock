package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

public class SetAlarmSettingsActivity extends AppCompatActivity implements SnoozeDialogFragment.OnCompleteListener{

    private TimePicker tp;
    private int snooze_time;
    private boolean vibration_on, minigame_on;
    private String description, ringtone_path, repeatable_days, trigger_date;
    TextView snoozeTimeTextView, musicTextView, dateTextView;
    EditText descriptionEditText;
    Switch vibrationToggleSwitch, minigameToggleSwitch;

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
        ringtone_path = intentBundle.getString(AlarmListDisplayActivity.ALARM_RING_PATH);
        repeatable_days = intentBundle.getString(AlarmListDisplayActivity.ALARM_REPEAT_DAYS);
        trigger_date = intentBundle.getString(AlarmListDisplayActivity.ALARM_TRIGGER_DATE);
        final boolean snooze_mode = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_SNOOZE_MODE);
        snooze_time = intentBundle.getInt(AlarmListDisplayActivity.ALARM_SNOOZE_TIME);
        description = intentBundle.getString(AlarmListDisplayActivity.ALARM_DESC);
        vibration_on = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION);
        minigame_on = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME);
        final boolean alarm_on = intentBundle.getBoolean(AlarmListDisplayActivity.ALARM_ON);

        // Set description to previously entered description for alarm if it exists
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        if(description != null){
            descriptionEditText.setText(description);
        }
        // Set vibration toggle switch to on or off based on previous settings
        vibrationToggleSwitch = (Switch) findViewById(R.id.vibrationToggle);
        vibrationToggleSwitch.setChecked(vibration_on);
        // Set mingame toggle switch to on or off based on previous settings
        minigameToggleSwitch = (Switch) findViewById(R.id.minigameToggle);
        minigameToggleSwitch.setChecked(minigame_on);
        // Set music display to chosen song from previous settings
        musicTextView = (TextView) findViewById(R.id.second_line1);
        if(ringtone_path != null){
            musicTextView.setText(ringtone_path);
        }
        // Set snooze time to previous selected value
        snoozeTimeTextView = (TextView) findViewById(R.id.snoozeTimeTextView);
        snoozeTimeTextView.setText(String.valueOf(snooze_time) + " minutes");


        final TextView dateTextView = (TextView) findViewById(R.id.dateDisplayTextView);
        if(trigger_date != null){
            dateTextView.setText(trigger_date);
        }


        tp = (TimePicker) findViewById(R.id.time_picker);
        // Set the time picker to display alarm's current time
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
                finish();
            }
        });
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() { // PULL DATA FROM THE SETTINGS VIEW TO UPDATE DATABASE (with updateIntent.putExtra(FIELD DEFINED IN ALARMLISTDISPLAYACTIVITY, VALUE))
            @Override
            public void onClick(View view) {
                //----------------------------
                //setTimer(view);
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
                if(musicTextView.getText().toString() != getString(R.string.music_default_text)) {
                    ringtone_path = musicTextView.getText().toString();
                }
                // Pull states from the Day buttons and save to repeatable days
                // code here
                // Trigger dates have more priority, thus overwrite repeatable days
                if(dateTextView.getText().toString() != getString(R.string.date_message)){
                    repeatable_days = null;
                    trigger_date = dateTextView.getText().toString();
                }
                description = descriptionEditText.getText().toString();

                Intent updateIntent = new Intent();
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, alarm_id);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_TIME, time);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, snooze_time);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, ringtone_path);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_REPEAT_DAYS, repeatable_days);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_TRIGGER_DATE, trigger_date);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_MODE, snooze_mode);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_DESC, description);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_VIBRATION, vibration_on);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_MINIGAME, minigame_on);
                updateIntent.putExtra(AlarmListDisplayActivity.ALARM_ON, alarm_on);
                
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

        // Area for date selection options to be displayed
        View dateSelector = (View) findViewById(R.id.date_selector);
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // MONTH STARTS AT 0 SO WE CHANGE IT TO MATCH NORMAL CONVENTION
                int normalMonth = month + 1;
                dateTextView.setText(normalMonth + "/" + day + "/" + year);
            }
        };
        // Display the datePicker dialog when user clicks on the date area
        dateSelector.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(SetAlarmSettingsActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.show();
            }
        });
        vibrationToggleSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vibration_on = b;
            }
        });

        minigameToggleSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minigame_on = b;
            }
        });
    }

    // Return information from fragment
    @Override
    public void onComplete(int minutes){
        snooze_time = minutes;
        snoozeTimeTextView.setText(String.valueOf(minutes) + " minutes");
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
