package sjsu.android.alarmclockplusplus;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ClockActivityAdapter extends RecyclerView.Adapter<ClockActivityAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView timeDisplay;
        public TextView dateDisplay;
        public Switch mySwitch;

        public MyViewHolder(CardView v) {
            super(v);
            cardView = v;
            timeDisplay = (TextView) v.findViewById(R.id.timeDisplay);
            dateDisplay = (TextView) v.findViewById(R.id.dayDisplay);
            mySwitch = (Switch) v.findViewById(R.id.onOffSwitch);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ClockActivityAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ClockActivityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
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
        final String timeText = mDataset[position];
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), timeText, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(view.getContext(), AlarmActivity.class);
                myIntent.putExtra("time", timeText);
                view.getContext().startActivity(myIntent);

            }
        });
        holder.mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    // TODO
                    // Set alarm (if first alarm change top display to display first alarm)
                }
                else{
                    // Cancel alarm
                }
            }
        });
        // Display for alarm management screen
        TextView dateDisplay = (TextView)holder.timeDisplay;
        dateDisplay.setText(mDataset[position]);
        TextView daysDisplay = (TextView)holder.dateDisplay;
        daysDisplay.setText("S M T W TH F Sa");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
