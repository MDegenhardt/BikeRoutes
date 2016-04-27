package es.upv.sdm.labs.bikeroutes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.EventAdapter;
import es.upv.sdm.labs.bikeroutes.model.Event;

public class MyEventsActivity extends AppCompatActivity {

    TabHost tabHost;
    ListView upcomingEventsListView;
    ListView recentEventsListView;
    ListView invitationsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec(("TAB1"));
        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabUpcomingEvents), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutUpcomingEvents);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(("TAB2"));

        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabRecentEvents), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutRecentEvents);
        tabHost.addTab(spec);

        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabEventInvitations), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutInvitations);
        tabHost.addTab(spec);


        recentEventsListView = (ListView) findViewById(R.id.lvMyRecentEvents);
        upcomingEventsListView = (ListView) findViewById(R.id.lvMyUpcomingEvents);
        invitationsListView = (ListView) findViewById(R.id.lvMyInvitations);


        tabHost.setCurrentTab(0);
        populateEventsList();


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                if (tabHost.getCurrentTab() == 0) {
                   //do sth.
                    populateEventsList();
                    Log.d("MyEventsActivity", "Tab 2 selected");
                }
                if (tabHost.getCurrentTab() == 1) {
                    //do sth.
                    populateEventsList();
                    Log.d("MyEventsActivity", "Tab 1 selected");

                }
            }
        });
    }

    private void populateEventsList(){
        //Construct data source
        //TODO: change to correct data
        ArrayList<Event> arrayOfUpcomingEvents = Event.getEvents();
        ArrayList<Event> arrayOfRecentEvents = Event.getEvents();
        //Create the adapter to convert the array to views
        EventAdapter adapter1 = new EventAdapter(this, arrayOfUpcomingEvents);
        EventAdapter adapter2 = new EventAdapter(this, arrayOfRecentEvents);
        //attach the adapter to the listview
        recentEventsListView.setAdapter(adapter1);
        upcomingEventsListView.setAdapter(adapter2);
    }


    /*
This method is executed when the activity is created to populate the ActionBar with actions
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_events_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
