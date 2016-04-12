package es.upv.sdm.labs.bikeroutes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.model.User;


public class PersonAdapter extends AbstractAdapter<User>{

    public PersonAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.list_item_person, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = this.getData().get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(getResource(), parent, false);
        }

        TextView tvFirstName = (TextView) convertView.findViewById(R.id.tvPersonFirstName);
        TextView tvLastName = (TextView) convertView.findViewById(R.id.tvPersonLastName);
        ImageView imgOrg = (ImageView) convertView.findViewById(R.id.imgOrg);

        tvFirstName.setText(user.getName());
        //tvLastName.setText(user.lastName);

        imgOrg.setImageBitmap(user.getImage());
        //imgOrg.setImageResource(R.drawable.organise);


        // Return the completed view to render on screen
        return convertView;
    }
}
