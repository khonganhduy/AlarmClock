package sjsu.android.alarmclockplusplus;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;



public class AlarmRingService extends Service {

    private Ringtone ringtone;
    private Vibrator vibrator;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        // {number of millis before turning vibrator on, number of millis to keep vibrator on before turning off, number of millis vibrator is off before turning it on}
        long[] pattern = {0, 2000, 2000};
        // repeat is index of pattern
        vibrator.vibrate(pattern, 0);

        Log.d("DEBUG", "Service started");
        if (intent.getExtras().getString("ringtone-uri").equals("")){
            Log.d("DEBUG", "Empty string");
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            this.ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        }
        else {
            Log.d("DEBUG", "not empty string");
            Uri ringtoneUri = Uri.parse(intent.getExtras().getString("ringtone-uri"));
            this.ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        }

        ringtone.play();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        ringtone.stop();
        vibrator.cancel();

    }
}


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