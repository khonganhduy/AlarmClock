package sjsu.android.alarmclockplusplus;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AlarmBroadcastReceiver extends JobScheduler {
    @Override
    public int schedule(@NonNull JobInfo jobInfo) {
        return JobScheduler.RESULT_FAILURE;
    }

    @Override
    public int enqueue(@NonNull JobInfo jobInfo, @NonNull JobWorkItem jobWorkItem) {
        return JobScheduler.RESULT_FAILURE;
    }

    @Override
    public void cancel(int i) {

    }

    @Override
    public void cancelAll() {

    }

    @NonNull
    @Override
    public List<JobInfo> getAllPendingJobs() {
        return null;
    }

    @Nullable
    @Override
    public JobInfo getPendingJob(int i) {
        return null;
    }
}
