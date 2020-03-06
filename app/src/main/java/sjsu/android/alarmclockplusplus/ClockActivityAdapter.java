package sjsu.android.alarmclockplusplus;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public MyViewHolder(CardView v) {
            super(v);
            cardView = v;
            timeDisplay = (TextView) v.findViewById(R.id.timeDisplay);
            dateDisplay = (TextView) v.findViewById(R.id.dayDisplay);
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), String.format("CLICKED %s", mDataset[position]), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(view.getContext(), AlarmActivity.class);
                view.getContext().startActivity(myIntent);

            }
        });
        TextView dateDisplay = (TextView)holder.timeDisplay;
        dateDisplay.setText(mDataset[position]);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
