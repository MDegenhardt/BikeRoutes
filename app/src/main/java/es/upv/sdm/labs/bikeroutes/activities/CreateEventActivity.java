package es.upv.sdm.labs.bikeroutes.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.dao.UserDAO;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.EventType;
import es.upv.sdm.labs.bikeroutes.model.Location;
import es.upv.sdm.labs.bikeroutes.services.EventService;
import es.upv.sdm.labs.bikeroutes.services.ServerInfo;
import es.upv.sdm.labs.bikeroutes.util.Constants;
import es.upv.sdm.labs.bikeroutes.util.DateHelper;
import es.upv.sdm.labs.bikeroutes.util.DatePickerFragment;
import es.upv.sdm.labs.bikeroutes.util.TimePickerFragment;
import es.upv.sdm.labs.bikeroutes.util.async.PostExecute;


public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Event event;

//    Event evento = new Event();
//    new EventService().insert(evento);


//    new EventService.findById(2,evento);
//
//    ArrayList<Event> response = new ArrayList<>();
//    new EventService.findAll(response, new AsynExecutable()... / new PostExecute());

    TextView tvStart;
    TextView tvEnd;
    Button btnEventDate;
    Button btnEventTime;
    RadioGroup rgEventType;
    TextView tvDescription;
    CheckBox cbSecret;

    Date date = new Date();

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String endLocationStr;
    String startLocationStr;

    private EventType mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        tvStart = (TextView) findViewById(R.id.tvEventStart);
        tvEnd = (TextView) findViewById(R.id.tvEventEnd);
        btnEventDate = (Button) findViewById(R.id.btnEventDate);
        btnEventTime = (Button) findViewById(R.id.btnEventTime);
        rgEventType = (RadioGroup) findViewById(R.id.rgEventType);
        tvDescription = (TextView) findViewById(R.id.etEventDescription);
        cbSecret = (CheckBox) findViewById(R.id.cbSecretEvent);


        mType = new EventType();
    }

    @Override
    protected void onPause() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putInt("eventType", rgEventType.getCheckedRadioButtonId());
        editor.putString("date", btnEventDate.getText().toString());
        editor.putString("time", btnEventTime.getText().toString());
        editor.putString("start", startLocationStr);
        editor.putString("end", endLocationStr);
        editor.putString("description", tvDescription.getText().toString());
        editor.putBoolean("secret", cbSecret.isChecked());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
//        rgEventType.check(prefs.getInt("rgEventType", R.id.rbBike));

        int typeInt = prefs.getInt("eventType", R.id.rbBike);
        rgEventType.check(typeInt);

        int t = rgEventType.getCheckedRadioButtonId();
        mType.setType(t==R.id.rbBike ? EventType.Type.BIKE : t==R.id.rbRun ? EventType.Type.RUN : EventType.Type.HIKE);

        startLocationStr = prefs.getString("start", "Choose a location");
        tvStart.setText(startLocationStr);
        endLocationStr = prefs.getString("end", "Choose a location");
        tvEnd.setText(endLocationStr);
        btnEventDate.setText(prefs.getString("date", "Date: " + DateHelper.dateToString(Calendar.getInstance().getTime())));
        btnEventTime.setText(prefs.getString("time", "Time: " + DateHelper.timeToString(Calendar.getInstance().getTime())));
        tvDescription.setText(prefs.getString("description", ""));
        cbSecret.setChecked(prefs.getBoolean("secret", false));
        super.onResume();
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }



    /*
This method is executed when the activity is created to populate the ActionBar with actions
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.create_event_menu, menu);
        menu.findItem(R.id.menuInviteFriend).setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuInviteFriend:
                // User chose the "Invite Friend" item, show the app CreateEvent...
                // do sth.
                Log.d("CreateEventActivity", "Invite Friend Menu pressed");
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void selectLocationButtonPressed(View view){
        Log.d("CreateEventActivity", "Location Button pressed");


        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            if(view.getId() == R.id.btnStartLocation || view.getId() == R.id.tvEventStart){
                startActivityForResult(builder.build(this), Constants.PLACE_PICKER_START_REQUEST);
            }
            else if(view.getId() == R.id.btnEndLocation || view.getId() == R.id.tvEventEnd){
                startActivityForResult(builder.build(this), Constants.PLACE_PICKER_END_REQUEST);
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
        if (requestCode == Constants.PLACE_PICKER_START_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);


//                startLocationStr = String.format("Place: %s", place.getName());
                startLocationStr = place.getName().toString();

//                toastMsg = String.format("LatLng: %s", place.getLatLng());

//                toastMsg = String.format("Address: %s", place.getAddress());
                Toast.makeText(this, startLocationStr, Toast.LENGTH_LONG).show();



                tvStart.setText(startLocationStr);
                editor.putString("start", startLocationStr);
                editor.apply();



            }
        }

        else if (requestCode == Constants.PLACE_PICKER_END_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);


                endLocationStr = place.getName().toString();

//                toastMsg = String.format("LatLng: %s", place.getLatLng());

//                toastMsg = String.format("Address: %s", place.getAddress());
                Toast.makeText(this, endLocationStr, Toast.LENGTH_LONG).show();

                tvEnd.setText(endLocationStr);
                editor.putString("end", endLocationStr);
                editor.apply();


            }
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        Log.w("CreateEventActivity","Date = " + year + monthOfYear + dayOfMonth);

        date.setDate(dayOfMonth);
        date.setMonth(monthOfYear);
        date.setYear(year);

        Log.w("CreateEventActivity","Date = " + date.toString());

        String dateString = DateHelper.dateToString(date);

        btnEventDate.setText("DATE: " + dateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.w("CreateEventActivity","Time = " + hourOfDay + ":" + minute);

        date.setHours(hourOfDay);
        date.setMinutes(minute);

        String timeString = DateHelper.timeToString(date);

        btnEventTime.setText("TIME: " + timeString);
    }

    public void createEventButtonPressed(View view){
        Log.d("CreateEventActivity", "Create Button pressed");

        event = new Event();

        event.setType(mType);
        event.setDeparture(new Location(startLocationStr,this));
        event.setArrival(new Location(endLocationStr,this));
        event.setSecret(cbSecret.isChecked());
        event.setDate(date);
        event.setDescription(tvDescription.getText().toString());
        event.setOver(false);
        UserDAO dao = new UserDAO(this);


        event.setOrganizer(dao.findById(prefs.getInt("user_id", 0)));
        dao.close();


        new EventService().insert(event, new PostExecute() {
            @Override
            public void postExecute(int option) {
                if(ServerInfo.RESPONSE_CODE == ServerInfo.RESPONSE_OK){
                    //ok
                    Log.d("CreateEventActivity", "Event created!");

                } else{
                    //not ok
                    Log.d("CreateEventActivity", "Error creating event!");
                }
            }
        });



    }


}
