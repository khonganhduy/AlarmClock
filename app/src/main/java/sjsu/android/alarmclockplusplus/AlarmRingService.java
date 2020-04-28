package sjsu.android.alarmclockplusplus;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

public class AlarmRingService extends Service {

    private Ringtone ringtone;
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");*/
        android.os.Debug.waitForDebugger();
        Intent notificationIntent = new Intent(this, SnoozeActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification =
                new Notification.Builder(this, "RingService").setOngoing(true)
                        .setContentTitle("yep")
                        .setSmallIcon(android.R.drawable.stat_sys_download)
                        .setContentText("double yep")
                        .build();

        startForeground( 1, notification );
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        Log.d("DEBUG", "SERVICE STARTED");

        mediaPlayer = new MediaPlayer();
        if (intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION)){
            vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
            // {number of millis before turning vibrator on, number of millis to keep vibrator on before turning off, number of millis vibrator is off before turning it on}
            long[] pattern = {0, 2000, 2000};
            // repeat is index of pattern
            vibrator.vibrate(pattern, 0);
        }

        Log.d("DEBUG", "Service started");
        if (intent.getExtras().getString(AlarmListDisplayActivity.ALARM_RING_PATH).equals("")){
            Log.d("DEBUG", "Empty string");
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            this.ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
            ringtone.play();
        }
        else {
            Log.d("DEBUG", "not empty string");
            String path = intent.getStringExtra(AlarmListDisplayActivity.ALARM_RING_PATH);
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(path);
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                Log.d("DEBUG", "MUSIC START");
            }
            catch (Exception e){
                Toast.makeText(this, "Could not find music file", Toast.LENGTH_SHORT);
                Log.d("DEBUG", "COULD NOT FIND MUSIC FILE");
            }
            //this.ringtone = RingtoneManager.getRingtone(this, uri);
        }

        Log.d("DEBUG", "returning");
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy()
    {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        if (ringtone != null){
            ringtone.stop();
        }
        if (vibrator != null){
            vibrator.cancel();
        }
        Log.d("DEBUG", "SERVICE STOPPED");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("DEBUG", "attempting to restart service");
        Intent myIntent = new Intent("com.android.ServiceStopped");
        myIntent.setClass(this, AlarmBroadcastReceiver.class);
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, rootIntent.getExtras().getInt(AlarmListDisplayActivity.ALARM_SNOOZE_TIME));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, rootIntent.getExtras().getInt(AlarmListDisplayActivity.ALARM_RING_PATH));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, rootIntent.getExtras().getInt(AlarmListDisplayActivity.ALARM_ID));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_VIBRATION, rootIntent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_MINIGAME, rootIntent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME));
        Log.d("DEBUG", "restarting service");
        sendBroadcast(myIntent);
        super.onTaskRemoved(rootIntent);
    }
}

/*
public class AlarmRingService extends Service {

    private Ringtone ringtone;
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //super.onStartCommand(intent, flags, startId);
        Log.d("DEBUG", "SERVICE STARTED");
        mediaPlayer = new MediaPlayer();
        if (intent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION)){
            vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
            // {number of millis before turning vibrator on, number of millis to keep vibrator on before turning off, number of millis vibrator is off before turning it on}
            long[] pattern = {0, 2000, 2000};
            // repeat is index of pattern
            vibrator.vibrate(pattern, 0);
        }

        Log.d("DEBUG", "Service started");
        if (intent.getExtras().getString(AlarmListDisplayActivity.ALARM_RING_PATH).equals("")){
            Log.d("DEBUG", "Empty string");
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            this.ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
            ringtone.play();
        }
        else {
            Log.d("DEBUG", "not empty string");
            String path = intent.getStringExtra(AlarmListDisplayActivity.ALARM_RING_PATH);
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(path);
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
            catch (Exception e){
                Toast.makeText(this, "Could not find music file", Toast.LENGTH_SHORT);
                Log.d("DEBUG", "COULD NOT FIND MUSIC FILE");
            }
            //this.ringtone = RingtoneManager.getRingtone(this, uri);
        }


        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        if (ringtone != null){
            ringtone.stop();
        }
        if (vibrator != null){
            vibrator.cancel();
        }
        Log.d("DEBUG", "SERVICE STOPPED");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("DEBUG", "attempting to restart service");
        Intent myIntent = new Intent("com.android.ServiceStopped");
        myIntent.setClass(this, AlarmBroadcastReceiver.class);
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, rootIntent.getExtras().getInt(AlarmListDisplayActivity.ALARM_SNOOZE_TIME));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, rootIntent.getExtras().getInt(AlarmListDisplayActivity.ALARM_RING_PATH));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, rootIntent.getExtras().getInt(AlarmListDisplayActivity.ALARM_ID));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_VIBRATION, rootIntent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_VIBRATION));
        myIntent.putExtra(AlarmListDisplayActivity.ALARM_MINIGAME, rootIntent.getExtras().getBoolean(AlarmListDisplayActivity.ALARM_MINIGAME));
        Log.d("DEBUG", "restarting service");
        sendBroadcast(myIntent);
        super.onTaskRemoved(rootIntent);
    }
}
*/

/*
public class AlarmBroadcastService extends JobIntentService {

    NotificationManager alarmNotifyManager;
    public AlarmBroadcastService(){
        super();
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent){
        Bundle data = intent.getExtras();
        String notificationMsg = data.getString("notification") + " alarm";
        notification(notificationMsg);
    }

    private void notification(String message){
        alarmNotifyManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, AlarmListDisplayActivity.class), 0);
        NotificationCompat.Builder alarmBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name)).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message)).setContentText(message);
        alarmBuilder.setContentIntent(pendIntent);
        alarmNotifyManager.notify(1, alarmBuilder.build());


    }
}*/