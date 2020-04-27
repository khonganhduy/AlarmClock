package sjsu.android.alarmclockplusplus;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmListDisplayAdapter extends RecyclerView.Adapter<AlarmListDisplayAdapter.MyViewHolder> {
    private List<Alarm> mDataset;
    private final AlarmViewModel mViewModel;
    private final Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView timeDisplay;
        public TextView dateDisplay;
        public Switch mySwitch;
        public ImageButton deleteButton;
        public AlarmManager alarmManager;

        public MyViewHolder(CardView v) {
            super(v);
            cardView = v;
            timeDisplay = (TextView) v.findViewById(R.id.timeDisplay);
            dateDisplay = (TextView) v.findViewById(R.id.dayDisplay);
            mySwitch = (Switch) v.findViewById(R.id.onOffSwitch);
            deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
            alarmManager = (AlarmManager) v.getContext().getSystemService(Context.ALARM_SERVICE);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlarmListDisplayAdapter(Context context, AlarmViewModel avm) {
        this.context = context;
        mViewModel = avm;
    }

    // allows for UI to update
    public void setAlarms(List<Alarm> alarmList) {
        mDataset = alarmList;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlarmListDisplayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Alarm alarm = mDataset.get(position);
        final String timeText = alarm.getAlarmTime();
        final MyViewHolder vh = holder;

        //Bind alarm to cardview
        vh.cardView.setTag(alarm);
        // Go to settings to update alarm settings
        vh.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SetAlarmSettingsActivity.class);
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_TIME, timeText);
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_ID, alarm.getAlarmId());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, alarm.getRingtonePath());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_REPEAT_DAYS, alarm.getRepeatableDays());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_TRIGGER_DATE, alarm.getTriggerDate());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_MODE, alarm.isSnoozeMode());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, alarm.getSnoozeTime());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_DESC, alarm.getDescription());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_VIBRATION, alarm.isVibration_on());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_MINIGAME, alarm.isMinigame_on());
                myIntent.putExtra(AlarmListDisplayActivity.ALARM_ON, alarm.isAlarm_on());

                ((Activity) context).startActivityForResult(myIntent, AlarmListDisplayActivity.ALARM_REQUEST_CODE);
            }
        });
        // Toggle on or off alarms
        vh.mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // FIX LISTENER TO ACTIVATE/DEACTIVATE ALARM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
               // SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
                //Calendar calendar = Calendar.getInstance();
                //try{
                   // Date date = dateFormat.parse(timeText);
                    //calendar.setTime(date);
                    //int requestCode = alarm.getAlarmId();
                    if(isChecked){
                        mViewModel.update(alarm.getAlarmId(), true);
                        setTimer(compoundButton, alarm);

                    /*Intent receiverIntent = new Intent(compoundButton.getContext(), AlarmBroadcastReceiver.class);
                    receiverIntent.putExtra("notification",timeText);
                    vh.pendingIntent = PendingIntent.getBroadcast(compoundButton.getContext(), requestCode, receiverIntent, 0);
                    vh.alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), vh.pendingIntent);*/
                    }
                    else{
                        mViewModel.update(alarm.getAlarmId(), false);
                        Intent i = new Intent(compoundButton.getContext(), AlarmBroadcastReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(compoundButton.getContext(), alarm.getAlarmId(), i, 0);
                        pendingIntent.cancel();
                    }
               // } catch (ParseException e) {
                   // e.printStackTrace();
               // }
            }
        });
        // Set alarm to off or on based on previous settings
        vh.mySwitch.setChecked(alarm.isAlarm_on());
        //Delete alarm from management screen as well as database
        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.mySwitch.setChecked(false);
                mViewModel.delete((Alarm)vh.cardView.getTag());
            }
        });
        // Display for alarm management screen
        TextView dateDisplay = (TextView)holder.timeDisplay;

        dateDisplay.setText(alarm.getAlarmTime());

        TextView daysDisplay = (TextView)holder.dateDisplay; //MAYBE CHANGE THIS LATER...

        if(alarm.getRepeatableDays() == null){
            if(alarm.getTriggerDate() != null){
                daysDisplay.setText(alarm.getTriggerDate());
            }
            else{
                daysDisplay.setText("M T W Th F Sa Su");
            }
        }
        else {
            daysDisplay.setText(alarm.getRepeatableDays());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    // CURRENTLY WORKING METHOD TO SET AN ALARM
    public void setTimer(View v, Alarm alarm){

        AlarmManager alarmManager = (AlarmManager) v.getContext().getSystemService(Context.ALARM_SERVICE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        try{
            Date newDate = dateFormat.parse(alarm.getAlarmTime());
            Date date = new Date();

            cal_now.setTime(date);
            cal_alarm.setTime(date);

            cal_alarm.set(Calendar.HOUR_OF_DAY, newDate.getHours());
            cal_alarm.set(Calendar.MINUTE, newDate.getMinutes());
            cal_alarm.set(Calendar.SECOND, 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(v.getContext(), AlarmBroadcastReceiver.class);
        i.putExtra(AlarmListDisplayActivity.ALARM_RING_PATH, alarm.getRingtonePath());
        i.putExtra(AlarmListDisplayActivity.ALARM_SNOOZE_TIME, alarm.getSnoozeTime());
        i.putExtra(AlarmListDisplayActivity.ALARM_ID, alarm.getAlarmId());
        //Log.d("DEBUG", alarm.getRingtonePath());
        Log.d("DEBUG", String.valueOf(alarm.getSnoozeTime()));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(), alarm.getAlarmId(), i, 0);
        Toast.makeText(v.getContext(), "Alarm set", Toast.LENGTH_SHORT).show();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
    }

}
