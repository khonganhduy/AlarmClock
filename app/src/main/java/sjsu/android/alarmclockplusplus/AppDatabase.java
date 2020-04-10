package sjsu.android.alarmclockplusplus;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Alarm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDAO alarmDao();
}
//AppDatabase db = Room.databaseBuilder(getAppContext,AppDatabase.class,"database_name").build() to get ref to database