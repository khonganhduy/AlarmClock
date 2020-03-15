package sjsu.android.alarmclockplusplus;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        try {
            Date date = dateFormat.parse(timeText);
            TimePicker tp = (TimePicker) findViewById(R.id.time_picker);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            tp.setHour(c.get(Calendar.HOUR));
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
                // Use AlarmManager + broadcaster/receivers(apis deprecated) -- new APIs JobScheduler and JobIntentService
                Intent returnIntent = new Intent(view.getContext(), ClockActivity.class);
                startActivity(returnIntent);
            }
        });
    }
}
