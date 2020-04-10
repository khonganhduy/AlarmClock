package sjsu.android.alarmclockplusplus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;


public class AlarmListDisplayActivity extends AppCompatActivity {
    private TextView addAlarmView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> myDataset;

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
        myDataset = new ArrayList<String>(Arrays.asList("6:00 AM", "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "2:15 PM"));
        mAdapter = new AlarmListDisplayAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        Bundle savedData = getIntent().getExtras();

        addAlarmView = (TextView) findViewById(R.id.addAlarmButton);
        addAlarmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlarm("5:00 AM");
            }
        });
    }

    protected void onResume() {
        super.onResume();
        Bundle savedData = getIntent().getExtras();
        if(getIntent() != null && savedData != null){
            int position = savedData.getInt("position");
            String time = savedData.getString("time");
            modifyData(time, position);
        }
    }

    private void modifyData(String item, int position){
        myDataset.set(position, item);
        mAdapter.notifyItemChanged(position);
    }

    private void addAlarm(String item){
        myDataset.add(item);
        mAdapter.notifyItemInserted(myDataset.size()-1);
    }
}
