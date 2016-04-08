package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.other.DatePickerFragment;
import es.upv.sdm.labs.bikeroutes.other.EventAdapter;
import es.upv.sdm.labs.bikeroutes.other.TimePickerFragment;
import es.upv.sdm.labs.bikeroutes.pojo.Event;

public class SearchEventActivity extends AppCompatActivity {

    ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        searchResultListView = (ListView) findViewById(R.id.lvSearchResults);

    }

    private void populateEventsList() {
        //Construct data source
        ArrayList<Event> arrayOfEvents = Event.getEvents();
        //Create the adapter to convert the array to views
        EventAdapter adapter = new EventAdapter(this, arrayOfEvents);
        //attach the adapter to the listview
        searchResultListView.setAdapter(adapter);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void searchButtonClicked(View view) {

        populateEventsList();

    }

}