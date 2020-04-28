package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Main screen to display all existing alarms
public class AlarmListDisplayActivity extends AppCompatActivity {
    private TextView addAlarmView;
    private TextView textClock;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AlarmViewModel mViewModel;
    public static final int ALARM_REQUEST_CODE = 9;
    public static final String ALARM_ID = "id";
    public static final String ALARM_TIME = "time";
    public static final String ALARM_RING_PATH = "path";
    public static final String ALARM_REPEAT_DAYS = "repeat";
    public static final String ALARM_TRIGGER_DATE = "date";
    public static final String ALARM_SNOOZE_MODE = "snooze_mode";
    public static final String ALARM_SNOOZE_TIME = "snooze_time";
    public static final String ALARM_DESC = "desc";
    public static final String ALARM_VIBRATION = "vibration";
    public static final String ALARM_MINIGAME = "minigame";
    public static final String ALARM_ON = "alarmOn";
    public static final String ALARM_RING_NAME = "alarm_ring_name";
    public static final int SNOOZE_REQUEST_CODE = 24444;
    public static final String ALARM_POSITION = "position";
    private AlarmListDisplayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_clock);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        mViewModel = new AlarmViewModel(getApplication());

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        mAdapter = new AlarmListDisplayAdapter(this, mViewModel);
        //final AlarmListDisplayAdapter mAdapter = new AlarmListDisplayAdapter(this, mViewModel);
        recyclerView.setAdapter(mAdapter);

        mViewModel.getAlarmList().observe(this, new Observer<List<Alarm>>(){
            public void onChanged(@Nullable final List<Alarm> alarms) {
                mAdapter.setAlarms(alarms);
            }
        });

        textClock = (TextView)findViewById(R.id.textClock);

        addAlarmView = (TextView) findViewById(R.id.addAlarmButton);
        addAlarmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.insert(new Alarm((int)Math.round(Math.random() * 100000), "6:00 AM",
                        null, "M T W Th F Sa Su", null, false, null,1, false, false, false));
            }
        });
    }

    /*protected void onResume() {
        super.onResume();
        Bundle savedData = getIntent().getExtras();
        if(getIntent() != null && savedData != null){
            int position = savedData.getInt("position");
            String time = savedData.getString("time");
            textClock.setText(time);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == ALARM_REQUEST_CODE){
            int id = data.getIntExtra(ALARM_ID,0);
            String time = data.getStringExtra(ALARM_TIME);
            String path = data.getStringExtra(ALARM_RING_PATH);
            String repeat = data.getStringExtra(ALARM_REPEAT_DAYS);
            String trigger = data.getStringExtra(ALARM_TRIGGER_DATE);
            boolean snooze = data.getBooleanExtra(ALARM_SNOOZE_MODE,false);
            String desc = data.getStringExtra(ALARM_DESC);
            int snooze_time = data.getIntExtra(ALARM_SNOOZE_TIME,1);
            boolean vibration = data.getBooleanExtra(ALARM_VIBRATION, false);
            boolean minigame = data.getBooleanExtra(ALARM_MINIGAME, false);
            boolean alarmOn = data.getBooleanExtra(ALARM_ON, false);
            mViewModel.update(id, time, path, repeat, trigger, snooze, desc, snooze_time, vibration, minigame, alarmOn);
            Log.d("DEBUG", String.valueOf(data.getIntExtra(ALARM_POSITION, 20)));
            mAdapter.highlightedItemPosition = data.getIntExtra(ALARM_POSITION, -1);
            Log.d("DEBUG", String.valueOf(mAdapter.highlightedItemPosition));
            mAdapter.notifyItemChanged(mAdapter.highlightedItemPosition);
            //setTimer(time, trigger, path, snooze_time, id, vibration, minigame);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("DEBUG", "MAIN ACTIVITY DESTROYED");
        super.onDestroy();
    }
}
