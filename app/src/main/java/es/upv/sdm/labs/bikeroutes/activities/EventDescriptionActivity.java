package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.PersonAdapter;
import es.upv.sdm.labs.bikeroutes.pojo.Event;
import es.upv.sdm.labs.bikeroutes.pojo.User;

public class EventDescriptionActivity extends AppCompatActivity {

    ListView personsParticipatingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        personsParticipatingListView = (ListView) findViewById(R.id.lvPersonsInEvent);
        TextView tvDescription = (TextView) findViewById(R.id.tvEventDescription);
        tvDescription.setMovementMethod(new ScrollingMovementMethod());

        populatePersonsList();


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


}
