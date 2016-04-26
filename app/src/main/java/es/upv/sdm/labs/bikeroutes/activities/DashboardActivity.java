package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.adapters.EventAdapter;
import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.EventAdapter2;
import es.upv.sdm.labs.bikeroutes.model.Event;

public class DashboardActivity extends AppCompatActivity  {

    ListView recentEventsListView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        context = this;

        recentEventsListView = (ListView) findViewById(R.id.lvRecentEvents);

        //Log.d("DashboardActivity", "bla");
        populateEventsList();

        // When an item in the list is clicked
        recentEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DashboardActivity", "Item " + position + " clicked");
                startActivity(new Intent(context,EventDescriptionActivity.class));


            }
        });


    }

    private void populateEventsList(){
        //Construct data source
        ArrayList<Event> arrayOfEvents = Event.getEvents();

        Log.d("DashboardActivity", arrayOfEvents.get(0).toString());

        //Create the adapter to convert the array to views
        EventAdapter2 adapter = new EventAdapter2(this, arrayOfEvents);
        //recentEventsListView = (ListView) findViewById(R.id.lvRecentEvents);
        //attach the adapter to the listview
        recentEventsListView.setAdapter(adapter);
    }

    public void dashboardButtonClicked(View view){

        Intent intent = null;
        switch(view.getId()){
            case R.id.btn_create_route :
                intent = new Intent(this,CreateEventActivity.class);
                break;
            case R.id.btn_search_route :
                intent = new Intent(this,SearchEventActivity.class);
                break;

        }
        startActivity(intent);
    }


    /*
This method is executed when the activity is created to populate the ActionBar with actions
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        menu.findItem(R.id.menuMyAccount).setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuMyAccount:
                intent = new Intent(this, UserDerscriptionActivity.class);
                startActivity(intent);
                break;

            case R.id.menuMyEvents:
                intent = new Intent(this, EventDescriptionActivity.class);
                startActivity(intent);
                break;

            case R.id.menuCredits:
                intent = new Intent(this, CreditActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_create_route:
                // User chose the "Create" item, show the app CreateEvent...
                intent = new Intent(this, CreateEventActivity.class);
                startActivity(intent);
                return true;

            case R.id.btn_search_route:
                // User chose the "Search" item, show the app SearchEvent...
                intent = new Intent(this, SearchEventActivity.class);
                startActivity(intent);
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                //return super.onOptionsItemSelected(item);


        }
        return super.onOptionsItemSelected(item);
    }

    public void dashDescMapButtonPressed(View view){
        Log.d("EvenDescriptionActivity", "Map Button Pressed!");

        int eventID = 1;
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("EventID", eventID );
        startActivity(intent);

    }

}
