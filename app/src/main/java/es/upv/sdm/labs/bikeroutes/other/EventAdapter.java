package es.upv.sdm.labs.bikeroutes.other;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.pojo.Event;


public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_event, parent, false);
        }

        TextView tvDate = (TextView) convertView.findViewById(R.id.tvEventDate);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvEventTime);
        ImageView ivEventType = (ImageView) convertView.findViewById(R.id.ivEventType);
        TextView tvStart = (TextView) convertView.findViewById(R.id.tvEventStart);
        TextView tvEnd = (TextView) convertView.findViewById(R.id.tvEventEnd);

        tvDate.setText(event.date);
        tvTime.setText(event.time);

        switch (event.type){
            case 0:
                ivEventType.setImageResource(R.drawable.bike);
                break;
            case 1:
                ivEventType.setImageResource(R.drawable.hike);
                break;
            case 2:
                ivEventType.setImageResource(R.drawable.running);
                break;
            default:
                ivEventType.setImageResource(R.drawable.bike);
                break;

        }


        Log.d("EventAdapter", "StartName: " + event.startName);
        tvStart.setText(event.startName);
        tvEnd.setText(event.endName);

        // Return the completed view to render on screen
        return convertView;
    }
}
