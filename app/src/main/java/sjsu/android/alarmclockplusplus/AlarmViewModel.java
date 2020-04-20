package sjsu.android.alarmclockplusplus;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> alarmList;

    public AlarmViewModel(Application app){
        super(app);
        alarmRepository = new AlarmRepository(app);
        alarmList = alarmRepository.getAlarmList();
    }

    LiveData<List<Alarm>> getAlarmList(){
        return alarmList;
    }

    void insert(Alarm alarm){
        alarmRepository.insert(alarm);
    }
}
