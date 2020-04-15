package sjsu.android.alarmclockplusplus;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Alarm {
    @PrimaryKey
    public int alarmId;
/*
    @ColumnInfo(name = "time")
    public Calendar alarmTime;
*/
    @ColumnInfo(name = "ringtone_path")
    public String ringtonePath;

    @ColumnInfo(name = "repeatable_days")
    public String repeatableDays;

    @ColumnInfo(name = "trigger_date")
    public String triggerDate;

    @ColumnInfo(name = "snooze_mode")
    public boolean snoozeMode;

    /*
    public Calendar getAlarmTime(){
        return alarmTime;
    }
    public void setAlarmTime(Calendar alarmTime){
        this.alarmTime = alarmTime;
    }
*/
    public String getRingtonePath(){
        return ringtonePath;
    }
    public void setRingtonePath(String ringtonePath){
        this.ringtonePath = ringtonePath;
    }

    public String getRepeatableDays(){
        return repeatableDays;
    }
    public void setRepeatableDays(String repeatableDays){
        this.repeatableDays = repeatableDays;
    }

    public String getTriggerDate(){
        return triggerDate;
    }
    public void setTriggerDate(String triggerDate){
        this.triggerDate = triggerDate;
    }

    public boolean isSnoozeMode(){
        return snoozeMode;
    }
    public void setSnoozeMode(boolean snoozeMode){
        this.snoozeMode = snoozeMode;
    }
}

