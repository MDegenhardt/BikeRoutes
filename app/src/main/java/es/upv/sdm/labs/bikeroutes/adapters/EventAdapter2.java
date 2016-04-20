package es.upv.sdm.labs.bikeroutes.adapters;

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
        import es.upv.sdm.labs.bikeroutes.enumerations.EventType;
        import es.upv.sdm.labs.bikeroutes.model.Event;


public class EventAdapter2 extends ArrayAdapter<Event> {

    public EventAdapter2(Context context, ArrayList<Event> events) {
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

        tvDate.setText(event.getDate().toString());
        tvTime.setText(event.getDate().getTime()+"");

        EventType type = event.getType();
        int img = (type==EventType.HIKE) ? R.drawable.hike : (type==EventType.RUN) ? R.drawable.running : R.drawable.bike;
        ivEventType.setImageResource(img);


        Log.d("EventAdapter", "StartName: " + event.getDeparture().getAddress());
        tvStart.setText(event.getDeparture().getAddress());
        tvEnd.setText(event.getArrival().getAddress());

        // Return the completed view to render on screen
        return convertView;
    }
}
