package sjsu.android.alarmclockplusplus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AlarmListDisplayActivity extends AppCompatActivity {
    private TextView addAlarmView;
    private TextView textClock;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AlarmViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_clock);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        final AlarmListDisplayAdapter mAdapter = new AlarmListDisplayAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mViewModel = new AlarmViewModel(getApplication());

        mViewModel.getAlarmList().observe(this, new Observer<List<Alarm>>(){
            public void onChanged(@Nullable final List<Alarm> alarms) {
                mAdapter.setAlarms(alarms);
            }
        });

        //Bundle savedData = getIntent().getExtras();

        textClock = (TextView)findViewById(R.id.textClock);

        addAlarmView = (TextView) findViewById(R.id.addAlarmButton);
        addAlarmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.insert(new Alarm((int)Math.round(Math.random() * 100000), "6:00 AM"));
            }
        });
    }

    protected void onResume() {
        super.onResume();
        Bundle savedData = getIntent().getExtras();
        if(getIntent() != null && savedData != null){
            int position = savedData.getInt("position");
            String time = savedData.getString("time");
            //modifyData(time, position);
            textClock.setText(time);
        }
    }
}
