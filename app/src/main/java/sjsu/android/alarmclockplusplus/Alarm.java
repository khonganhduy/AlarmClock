package sjsu.android.alarmclockplusplus;

import java.util.Calendar;
import java.util.Date;

public class Alarm {

    Date alarmTime;

    public Alarm(){
        alarmTime = Calendar.getInstance().getTime();
    }

    public void setAlarmTime(Date newAlarmTime){
        alarmTime = newAlarmTime;
    }

    public Date getAlarmTime(){
        return alarmTime;
    }
}
