package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
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
import es.upv.sdm.labs.bikeroutes.dao.UserDAO;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.User;

public class DashboardActivity extends AppCompatActivity  {

    ListView recentEventsListView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recentEventsListView = (ListView) findViewById(R.id.lvRecentEvents);
        UserDAO dao = new UserDAO(this);
        this.user = dao.findById(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id",0));
        dao.close();

        //Log.d("DashboardActivity", "bla");
        populateEventsList();

        // When an item in the list is clicked
        recentEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DashboardActivity", "Item " + position + " clicked");
                startActivity(new Intent(getApplicationContext(),EventDescriptionActivity.class));


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
        if(user.getImage()!=null) menu.findItem(R.id.menuMyAccount).setIcon(new BitmapDrawable(getResources(), user.getImage()));
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
                startActivity(new Intent(this, MyEventsActivity.class));
                break;
            case R.id.menuSettings:
                startActivity(new Intent(this, MySettingsActivity.class));
                break;
            case R.id.menuLogOut:
                UserDAO dao = new UserDAO(this);
                dao.remove(user.getId());
                dao.close();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                editor.remove("user_id");
                editor.remove("language");
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.menuCredits:
                intent = new Intent(this, CreditActivity.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
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
