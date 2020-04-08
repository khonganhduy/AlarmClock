package sjsu.android.alarmclockplusplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_alarm);
        Bundle intentBundle = getIntent().getExtras();
        String timeText = intentBundle.getString("time");
        final int position = intentBundle.getInt("position");
        final TimePicker tp = (TimePicker) findViewById(R.id.time_picker);
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
                Intent returnIntent = new Intent(view.getContext(),ClockActivity.class);
                startActivity(returnIntent);
            }
        });
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Intent returnIntent = new Intent(view.getContext(), ClockActivity.class);
                returnIntent.putExtra("time", time);
                returnIntent.putExtra("position", position);
                startActivity(returnIntent);
            }
        });
        //---------------------------------------------------
        View soundSelector = (View) findViewById(R.id.sound_selector);
        soundSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AlarmActivity.this, "Sound Selector Clicked",
                        Toast.LENGTH_LONG).show();
                Intent soundSelectorIntent = new Intent(view.getContext() , SoundSelectorActivity.class);
                view.getContext().startActivity(soundSelectorIntent);
            }
        });
    }
}
