package sjsu.android.alarmclockplusplus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle myInput = this.getIntent().getExtras();
        tView = new TextView(this);
        tView = (TextView)findViewById(R.id.textView3);
        tView.setText("Your Score: " + String.valueOf(myInput.getInt("score")));
    }
}
