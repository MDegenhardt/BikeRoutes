package es.upv.sdm.labs.bikeroutes.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.EventAdapter2;
import es.upv.sdm.labs.bikeroutes.util.Constants;
import es.upv.sdm.labs.bikeroutes.util.DateHelper;
import es.upv.sdm.labs.bikeroutes.util.DatePickerFragment;
import es.upv.sdm.labs.bikeroutes.util.SearchDatePickerFragment;
import es.upv.sdm.labs.bikeroutes.util.SearchTimePickerFragment;
import es.upv.sdm.labs.bikeroutes.util.TimePickerFragment;
import es.upv.sdm.labs.bikeroutes.model.Event;

public class SearchEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ListView searchResultListView;
    Context context;

    TextView tvAddress;
    Button btnSEventDate;
    Button btnSEventTime;
    RadioGroup eventType;
    RadioButton rbChecked;
    EditText etKm;
    TextView tvlocation;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String searchLocationStr;

    Date searchDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        context = this;
        searchResultListView = (ListView) findViewById(R.id.lvSearchResults);

        tvAddress = (TextView) findViewById(R.id.tvEventAddress);
        btnSEventDate = (Button) findViewById(R.id.btnSEventDate);
        btnSEventTime = (Button) findViewById(R.id.btnSEventTime);
        eventType = (RadioGroup) findViewById(R.id.rgEventType);
        etKm = (EditText) findViewById(R.id.etKm);
        tvlocation = (TextView) findViewById(R.id.tvEventAddress);

        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SearchEventActivity", "Item " + position + " clicked");
                startActivity(new Intent(context,EventDescriptionActivity.class));


            }
        });

    }

    protected void onPause() {

        editor.putInt("eventType", eventType.getCheckedRadioButtonId());
        editor.putString("date", btnSEventDate.getText().toString());
        editor.putString("time", btnSEventTime.getText().toString());
        editor.putString("km", etKm.getText().toString());
        editor.putString("location", searchLocationStr);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        rbChecked = (RadioButton) findViewById(prefs.getInt("eventType", R.id.rbBike));
        btnSEventDate.setText(prefs.getString("date", "Date: " + DateHelper.dateToString(Calendar.getInstance().getTime())));
        btnSEventTime.setText(prefs.getString("time", "Time: " + DateHelper.timeToString(Calendar.getInstance().getTime())));
        etKm.setText(prefs.getString("km", "1"));
        searchLocationStr = prefs.getString("location", "Choose a location");
        tvAddress.setText(searchLocationStr);
        super.onResume();
    }

    private void populateEventsList() {
        //Construct data source
        ArrayList<Event> arrayOfEvents = Event.getEvents();
        //Create the adapter to convert the array to views
        EventAdapter2 adapter = new EventAdapter2(this, arrayOfEvents);
        //attach the adapter to the listview
        searchResultListView.setAdapter(adapter);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new SearchTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new SearchDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void selectLocationButtonPressed(View view){
        Log.d("CreateEventActivity", "Location Button pressed");


        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            if(view.getId() == R.id.tvEventAddress || view.getId() == R.id.btnAddressLocation){
                startActivityForResult(builder.build(this), Constants.PLACE_PICKER_SEARCH_REQUEST);
            }

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PLACE_PICKER_SEARCH_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                searchLocationStr = place.getName().toString();


                Toast.makeText(this, searchLocationStr, Toast.LENGTH_LONG).show();

                tvAddress.setText(searchLocationStr);
                editor.putString("location", searchLocationStr);
                editor.apply();

//                toastMsg = String.format("LatLng: %s", place.getLatLng());

//                toastMsg = String.format("Address: %s", place.getAddress());


            }
        }

    }

    public void dashDescMapButtonPressed(View view){
        Log.d("EvenDescriptionActivity", "Map Button Pressed!");

        int eventID = 1;
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("EventID", eventID );
        startActivity(intent);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        Log.w("CreateEventActivity","Date = " + year + monthOfYear + dayOfMonth);

        searchDate.setDate(dayOfMonth);
        searchDate.setMonth(monthOfYear);
        searchDate.setYear(year);

        Log.w("CreateEventActivity","SearchDate = " + searchDate.toString());

        String dateString = DateHelper.dateToString(searchDate);

        btnSEventDate.setText("DATE: " + dateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.w("CreateEventActivity","SearchTime = " + hourOfDay + ":" + minute);

        searchDate.setHours(hourOfDay);
        searchDate.setMinutes(minute);

        String timeString = DateHelper.timeToString(searchDate);

        btnSEventTime.setText("TIME: " + timeString);
    }




    public void searchButtonClicked(View view) {
        Log.d("SearchEventActivity", "Search Button pressed");

        populateEventsList();

        String dateString = DateHelper.toFormatString(searchDate);

        Log.w("SearchEventActivity","Date = " + dateString);

    }



}