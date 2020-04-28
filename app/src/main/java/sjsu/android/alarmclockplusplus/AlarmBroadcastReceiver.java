package sjsu.android.alarmclockplusplus;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.util.Log;
import android.widget.Toast;

import androidx.legacy.content.WakefulBroadcastReceiver;

import static android.os.VibrationEffect.createWaveform;

/*
public class AlarmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        ComponentName comp = new ComponentName(context.getPackageName(), AlarmListDisplayActivity.class.getName());
        startWakefulService(context, intent.setComponent(comp));
        setResultCode(Activity.RESULT_OK);
    }
}
*/


public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        String filepath = intent.getStringExtra(AlarmListDisplayActivity.ALARM_RING_PATH);
        int snoozeTime = intent.getIntExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, 1);
        //Log.d("DEBUG", filepath);
        Log.d("DEBUG", "BROADCAST RECEIVED");
        /*
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        // {number of millis before turning vibrator on, number of millis to keep vibrator on before turning off, number of millis vibrator is off before turning it on}
        long[] pattern = {0, 2000, 2000};
        // repeat is index of pattern
        vibrator.vibrate(pattern, 0);
        */
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Alarm is ON")
                .setContentText("You set up the alarm").build();


        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        noti.flags|= Notification.FLAG_AUTO_CANCEL;

        /*
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
        */


        Intent startIntent = new Intent(context, AlarmRingService.class);
        startIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, "");
        if (filepath == null){
            startIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, "");
        }
        else{
            startIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, filepath);
        }
        startIntent.putExtra(AlarmListDisplayActivity.ALARM_VIBRATION, intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION));
        Log.d("DEBUG", "Attempting to start service " + filepath);
        startIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, snoozeTime);
        startIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, intent.getExtras().getInt(AlarmListDisplayActivity.ALARM_ID));
        startIntent.putExtra(AlarmListDisplayActivity.ALARM_MINIGAME, intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME));


        Log.d("DEBUG", context.toString());
        Log.d("DEBUG", startIntent.toString());
        context.startForegroundService(startIntent);
        Log.d("DEBUG", "post service");


        // Start game activity
        if (intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME)){
            Intent myIntent = new Intent(context, GameActivity.class);
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, snoozeTime);
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, filepath);
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, intent.getExtras().getInt(AlarmListDisplayActivity.ALARM_ID));
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_VIBRATION, intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION));
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_MINIGAME, intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME));
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("DEBUG", "STARTING MINIGAME");
            context.startActivity(myIntent);
        }
        else {
            Intent myIntent = new Intent(context, SnoozeActivity.class);
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, snoozeTime);
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, filepath);
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, intent.getExtras().getInt(AlarmListDisplayActivity.ALARM_ID));
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_VIBRATION, intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION));
            myIntent.putExtra(AlarmListDisplayActivity.ALARM_MINIGAME, intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME));
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("DEBUG", "STARTING SNOOZE");
            context.startActivity(myIntent);
        }
    }
}
