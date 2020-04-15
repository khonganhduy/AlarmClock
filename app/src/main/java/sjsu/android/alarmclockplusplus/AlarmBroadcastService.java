package sjsu.android.alarmclockplusplus;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;


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
}