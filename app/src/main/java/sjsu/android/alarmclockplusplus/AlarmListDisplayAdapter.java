package sjsu.android.alarmclockplusplus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmListDisplayAdapter extends RecyclerView.Adapter<AlarmListDisplayAdapter.MyViewHolder> {
    private List<Alarm> mDataset;
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
        public PendingIntent pendingIntent;

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
    public AlarmListDisplayAdapter(Context context) {

    }

    public void setAlarms(List<Alarm> alarmList) {
        mDataset = alarmList;
        notifyDataSetChanged();
    }
    //public void updateData(String data, int position){
      //  mDataset.set(position, data);
        //notifyItemChanged(position);
    //}

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
        final String timeText = mDataset.get(position).getAlarmTime();
        final MyViewHolder vh = holder;
        vh.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SetAlarmSettingsActivity.class);
                myIntent.putExtra("time", timeText);
                myIntent.putExtra("position", position);
                view.getContext().startActivity(myIntent);
            }
        });
        vh.mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
                Calendar calendar = Calendar.getInstance();
                try{
                    Date date = dateFormat.parse(timeText);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int requestCode = position;
                if(isChecked){
                    Intent receiverIntent = new Intent(compoundButton.getContext(), AlarmBroadcastReceiver.class);
                    receiverIntent.putExtra("notification",timeText);
                    vh.pendingIntent = PendingIntent.getBroadcast(compoundButton.getContext(), requestCode, receiverIntent, 0);
                    vh.alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), vh.pendingIntent);
                }
                else{
                    vh.pendingIntent.cancel();
                    vh.alarmManager.cancel(vh.pendingIntent);
                }
            }
        });
        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(vh.getAdapterPosition());
            }
        });
        // Display for alarm management screen
        TextView dateDisplay = (TextView)holder.timeDisplay;

        dateDisplay.setText(mDataset.get(position).getAlarmTime());
        TextView daysDisplay = (TextView)holder.dateDisplay;
        daysDisplay.setText("S M T W TH F Sa");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    private void delete(int pos){
        mDataset.remove(pos);
        notifyItemRemoved(pos);
    }
}
