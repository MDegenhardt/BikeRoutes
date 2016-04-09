package es.upv.sdm.labs.bikeroutes.other;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.pojo.Person;


public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, ArrayList<Person> persons) {
        super(context, 0, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Person person = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_person, parent, false);
        }

        TextView tvFirstName = (TextView) convertView.findViewById(R.id.tvPersonFirstName);
        TextView tvLastName = (TextView) convertView.findViewById(R.id.tvPersonLastName);
        ImageView imgOrg = (ImageView) convertView.findViewById(R.id.imgOrg);

        tvFirstName.setText(person.firstName);
        tvLastName.setText(person.lastName);

        imgOrg.setImageResource(0);
        //imgOrg.setImageResource(R.drawable.organise);


        // Return the completed view to render on screen
        return convertView;
    }
}
