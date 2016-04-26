package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.PersonAdapter;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.EventType;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.services.EventService;
import es.upv.sdm.labs.bikeroutes.services.ServerInfo;
import es.upv.sdm.labs.bikeroutes.util.DateHelper;
import es.upv.sdm.labs.bikeroutes.util.async.PostExecute;

public class EventDescriptionActivity extends AppCompatActivity {

    ListView personsParticipatingListView;
    TextView tvEventDate;
    TextView tvEventTime;
    ImageView ivEventType;
    TextView tvEventStart;
    TextView tvEventEnd;
    TextView tvEventDescription;

    Context context;
    Intent intent;


    Event event;
    int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        context = this;
        intent = getIntent();
        //get from intent
        eventID = intent.getIntExtra("eventID", 0 );

        personsParticipatingListView = (ListView) findViewById(R.id.lvPersonsInEvent);
        tvEventDate = (TextView) findViewById(R.id.tvEventTime);
        tvEventTime = (TextView) findViewById(R.id.tvEventDate);
        ivEventType = (ImageView) findViewById(R.id.ivEventType);
        tvEventStart = (TextView) findViewById(R.id.tvEventStart);
        tvEventEnd = (TextView) findViewById(R.id.tvEventEnd);
        tvEventDescription = (TextView) findViewById(R.id.tvEventDescription);
        tvEventDescription.setMovementMethod(new ScrollingMovementMethod());

        populatePersonsList();


    }

    @Override
    protected void onResume() {
        super.onResume();

        event = new Event();

        new EventService().findById(eventID, event, new PostExecute() {
            @Override
            public void postExecute(int option) {
                if(ServerInfo.RESPONSE_CODE == ServerInfo.RESPONSE_OK){
                    //ok

                    tvEventDate.setText(DateHelper.dateToString(event.getDate()));
                    tvEventTime.setText(DateHelper.timeToString(event.getDate()));
                    EventType.Type type = event.getType().getType();
                    int img = (type==EventType.Type.HIKE) ? R.drawable.hike : (type==EventType.Type.RUN) ? R.drawable.running : R.drawable.bike;
                    ivEventType.setImageResource(img);
                    tvEventStart.setText(event.getDeparture().getAddress());
                    tvEventEnd.setText(event.getArrival().getAddress());
                    tvEventDescription.setText(event.getDescription());



                    Log.d("EDescriptionActivity", "Event searched!");
                    Toast.makeText(context, R.string.event_searched, Toast.LENGTH_LONG).show();


                } else{
                    //not ok
                    Log.d("EDescriptionActivity", "Error searching event!");
                    Toast.makeText(context, R.string.error_searching_event, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void populatePersonsList(){
        //Construct data source
        ArrayList<User> arrayOfPersons = User.getUsers();
        //Create the adapter to convert the array to views
        PersonAdapter adapter = new PersonAdapter(this, arrayOfPersons);
        //attach the adapter to the listview
        personsParticipatingListView.setAdapter(adapter);
    }

    /*
This method is executed when the activity is created to populate the ActionBar with actions
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.event_desc_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuInvite:
                // User chose the "Invite" item
                Log.d("EvenDescriptionActivity", "Invite Pressed!");
                return true;

            case R.id.menuShowRoute:
                // User chose the "Show Route" item
                Log.d("EvenDescriptionActivity", "Show Route Pressed!");
                return true;

            case R.id.menuParticipate:
                // User chose the "Participate" item
                Log.d("EvenDescriptionActivity", "Participate Pressed!");
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void eventDescMapButtonPressed(View view){
        Log.d("EvenDescriptionActivity", "Map Button Pressed!");

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("eventID", eventID );
        startActivity(intent);

    }


}
