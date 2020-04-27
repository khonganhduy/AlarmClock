package sjsu.android.alarmclockplusplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SnoozeActivity extends AppCompatActivity {
    Button dismiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        dismiss = (Button)findViewById(R.id.dismiss_btn);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopRingtone = new Intent(view.getContext(), AlarmRingService.class);
                view.getContext().stopService(stopRingtone);
            }
        });
    }
}
