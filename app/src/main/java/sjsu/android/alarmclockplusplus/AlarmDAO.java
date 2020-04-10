package sjsu.android.alarmclockplusplus;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Query("SELECT * FROM alarm")   //get all alarms to list them in main screen
    List<Alarm> getAll();

    @Query("SELECT * FROM alarm WHERE alarmId = (:alarmId)")     //get an alarm with specific id to cancel or modify
    Alarm findById(int alarmId);

    @Insert
    void insertAlarm(Alarm alarm);      //insert a new alarm

    @Delete
    void deleteAlarm(Alarm alarm);      //delete an existing alarm

}
