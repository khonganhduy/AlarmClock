package sjsu.android.alarmclockplusplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity{

    private ImageView iView;
    private TextView tView;
    private TextView tView2;
    private GestureDetectorCompat mDetector;
    private int randomInt;
    private Random randomGen;
    private int scoreTracker;
    private CountDownTimer timer;

    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTracker = 0;
        randomGen = new Random();
        randomInt = randomGen.nextInt(4);
        iView = new ImageView(this);
        iView =(ImageView)findViewById(R.id.imageView1);
        setNewImage();
        tView = new TextView(this);
        tView = (TextView)findViewById(R.id.textView1);
        tView2 = new TextView(this);
        tView2 = (TextView) findViewById(R.id.textView2);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                tView2.setText("Time Remaining: " + String.valueOf((l/1000) + 1));
            }

            @Override
            public void onFinish() {
                switchActivity();
            }
        };
        timer.start();
    }

    protected void onResume(){
        super.onResume();
        scoreTracker = 0;
        timer.cancel();
        timer.start();
    }

    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
        //Log.d("TEST", "SINGLE TAP UP";
    }

    private void switchActivity(){
        Intent myIntent = new Intent(this, GameResultActivity.class);
        myIntent.putExtra("score", scoreTracker);
        this.startActivity(myIntent);
    }

    private void setNewImage(){
        randomInt = randomGen.nextInt(4);
        if (randomInt == 0){
            iView.setImageResource(R.drawable.right_arrow);
        }
        else if (randomInt == 1){
            iView.setImageResource(R.drawable.left_arrow);
        }
        else if (randomInt == 2){
            iView.setImageResource(R.drawable.down_arrow);
        }
        else if (randomInt == 3){
            iView.setImageResource(R.drawable.up_arrow);
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            boolean pass = false;
            if(velocityX > 0 && velocityX > Math.abs(velocityY)){
                if(randomInt == 0){
                    pass = true;
                }
                Log.d(DEBUG_TAG, "Swipe right");
            }
            else if (velocityX < 0 && Math.abs(velocityX) > Math.abs(velocityY)){
                if(randomInt == 1){
                    pass = true;
                }
                Log.d(DEBUG_TAG, "Swipe left");
            }
            else if(velocityY > 0 && velocityY > Math.abs(velocityX)){
                if(randomInt == 2){
                    pass = true;
                }
                Log.d(DEBUG_TAG, "Swipe down");
            }
            else if(velocityY < 0 && Math.abs(velocityY) > Math.abs(velocityX)){
                if(randomInt == 3){
                    pass = true;
                }
                Log.d(DEBUG_TAG, "Swipe up");
            }
            if (pass){
                scoreTracker++;
                tView.setText(String.valueOf(scoreTracker));
                setNewImage();
            }
            Log.d(DEBUG_TAG, "Velocity X: " + velocityX + " Velocity Y: " + velocityY);
            return true;
        }
    }

}
