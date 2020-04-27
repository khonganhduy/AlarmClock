package sjsu.android.alarmclockplusplus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// Interface used for the database to perform SQLite operations
@Dao
public interface AlarmDAO {
    @Query("SELECT * FROM alarm_table")   //get all alarms to list them in main screen
    LiveData<List<Alarm>> getAll();

    @Query("SELECT * FROM alarm_table WHERE alarmId = (:alarmId)")     //get an alarm with specific id to cancel or modify
    Alarm findById(int alarmId);

    @Query("SELECT * FROM alarm_table WHERE time = (:alarmTime)")
    Alarm findByTime(String alarmTime);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlarm(Alarm alarm);      //insert a new alarm

    @Query("DELETE FROM alarm_table WHERE alarmId = (:id)")
    void deleteAlarmById(int id);      //delete an existing alarm

    @Delete
    void deleteAlarm(Alarm alarm);

    @Query("UPDATE alarm_table SET time = (:alarmTime), ringtone_path = (:ring_path), repeatable_days = (:repeat_days), trigger_date = (:trig_date), snooze_mode = (:snooze) WHERE alarmId = (:alarmId)")
    void update(int alarmId, String alarmTime, String ring_path, String repeat_days, String trig_date, boolean snooze);

}
