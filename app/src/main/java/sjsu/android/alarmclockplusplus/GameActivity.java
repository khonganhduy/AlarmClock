package sjsu.android.alarmclockplusplus;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;


import android.view.View;


public class GameActivity extends AppCompatActivity{


    private SimulationView simulationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulationView = new SimulationView(this, this.getIntent().getExtras());
        simulationView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(simulationView);
    }
}
