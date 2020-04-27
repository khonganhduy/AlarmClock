package sjsu.android.alarmclockplusplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "alarm_table")
public class Alarm {
    @PrimaryKey
    @NonNull
    private int alarmId;

    @NonNull
    @ColumnInfo(name = "time")
    private String alarmTime;

    @Nullable
    @ColumnInfo(name = "ringtone_path")
    private String ringtonePath;

    @Nullable
    @ColumnInfo(name = "repeatable_days")
    private String repeatableDays;

    @Nullable
    @ColumnInfo(name = "trigger_date")
    private String triggerDate;

    @NonNull
    @ColumnInfo(name = "snooze_mode")
    private boolean snoozeMode;

    public Alarm(int alarmId, String alarmTime){
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.snoozeMode = false;
    }

    @Ignore
    public Alarm(int alarmId, String alarmTime, boolean snoozeMode){
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.snoozeMode = snoozeMode;
    }

    @Ignore
    public Alarm(int alarmId, String alarmTime, String triggerDate){
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.triggerDate = triggerDate;
        this.snoozeMode = false;
    }

    @Ignore
    public Alarm(int alarmId, String alarmTime, String ringtonePath, String triggerDate){
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.ringtonePath = ringtonePath;
        this.triggerDate = triggerDate;
        this.snoozeMode = false;
    }

    @Ignore
    public Alarm(int alarmId, String alarmTime, String ringtonePath, String repeatableDays, String triggerDate, boolean snoozeMode){
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.ringtonePath = ringtonePath;
        this.repeatableDays = repeatableDays;
        this.triggerDate = triggerDate;
        this.snoozeMode = snoozeMode;
    }

    public void setAlarmId(int alarmId){
        this.alarmId = alarmId;
    }

    public int getAlarmId(){
        return this.alarmId;
    }

    public String getAlarmTime(){
        return alarmTime;
    }
    public void setAlarmTime(String alarmTime){
        this.alarmTime = alarmTime;
    }

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

