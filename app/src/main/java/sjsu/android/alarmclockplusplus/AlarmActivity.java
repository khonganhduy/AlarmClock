package sjsu.android.alarmclockplusplus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlarmActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_alarm);
        recyclerView = (RecyclerView)findViewById(R.id.alarm_options);
        String[] test = new String[]{"Test1", "Test2"};
        mAdapter = new AlarmActivityAdapter(test);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
