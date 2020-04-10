package sjsu.android.alarmclockplusplus;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Alarm {
    @PrimaryKey
    public int alarmId;

    @ColumnInfo(name="time")
    public Calendar alarmTime;

    @ColumnInfo(name="ringtone_path")
    public String ringtonePath;

    @ColumnInfo(name="repeatable_days")
    public String repeatableDays;

    @ColumnInfo(name="trigger_date")
    public String triggerDate;

    @ColumnInfo(name = "snooze_mode")
    public boolean snoozeMode;

}
