package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.services.EventService;
import es.upv.sdm.labs.bikeroutes.util.Constants;
import es.upv.sdm.labs.bikeroutes.util.DatePickerFragment;
import es.upv.sdm.labs.bikeroutes.util.TimePickerFragment;

public class CreateEventActivity extends AppCompatActivity {

    Event currentEvent;
    static int id = 0;
//    int PLACE_PICKER_START_REQUEST = 1;
//    int PLACE_PICKER_END_REQUEST = 2;
//    int PLACE_PICKER_SEARCH_REQUEST = 3;

//    Event evento = new Event();
//    new EventService().insert(evento);


//    new EventService.findById(2,evento);
//
//    ArrayList<Event> response = new ArrayList<>();
//    new EventService.findAll(response, new AsynExecutable()... / new PostExecute());

    TextView tvStart;
    TextView tvEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        tvStart = (TextView) findViewById(R.id.tvEventStart);
        tvEnd = (TextView) findViewById(R.id.tvEventEnd);

        currentEvent = new Event();


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
                String toastMsg;
                toastMsg = String.format("Place: %s", place.getName());

//                toastMsg = String.format("LatLng: %s", place.getLatLng());

//                toastMsg = String.format("Address: %s", place.getAddress());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                tvStart.setText(place.getName());


            }
        }

        else if (requestCode == Constants.PLACE_PICKER_END_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg;
                toastMsg = String.format("Place: %s", place.getName());

//                toastMsg = String.format("LatLng: %s", place.getLatLng());

//                toastMsg = String.format("Address: %s", place.getAddress());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                tvEnd.setText(place.getName());


            }
        }
    }


    public void createEventButtonPressed(View view){
        Log.d("CreateEventActivity", "Create Button pressed");

    }

}
