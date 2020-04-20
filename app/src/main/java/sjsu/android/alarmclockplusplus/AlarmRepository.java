package sjsu.android.alarmclockplusplus;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {
    private AlarmDAO dao;
    private LiveData<List<Alarm>> alarmList;

    AlarmRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        dao = db.alarmDao();
        alarmList = dao.getAll();
    }

    LiveData<List<Alarm>> getAlarmList(){
        return alarmList;
    }

    void insert(final Alarm alarm){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAlarm(alarm);
            }
        });
    }
}
