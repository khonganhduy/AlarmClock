package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class ClockActivity extends AppCompatActivity {
    private AlarmManager alarmManager;
    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        timePicker = (TimePicker)findViewById(R.id.timepicker);
        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

    }
}
