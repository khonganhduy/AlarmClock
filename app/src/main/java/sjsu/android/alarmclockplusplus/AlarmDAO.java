package sjsu.android.alarmclockplusplus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Query("SELECT * FROM alarm_table")   //get all alarms to list them in main screen
    LiveData<List<Alarm>> getAll();

    @Query("SELECT * FROM alarm_table WHERE alarmId = (:alarmId)")     //get an alarm with specific id to cancel or modify
    Alarm findById(int alarmId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlarm(Alarm alarm);      //insert a new alarm

    @Query("DELETE FROM alarm_table WHERE alarmId = (:id)")
    void deleteAlarm(int id);      //delete an existing alarm

}
