package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.PersonAdapter;
import es.upv.sdm.labs.bikeroutes.model.User;

public class MyFriendsActivity extends AppCompatActivity {

    ListView friendsListView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        context = this;

        friendsListView = (ListView) findViewById(R.id.lvMyFriends);

        //Log.d("DashboardActivity", "bla");
        populateFriendsList();

        // When an item in the list is clicked
        /*
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DashboardActivity", "Item " + position + " clicked");
                startActivity(new Intent(context, EventDescriptionActivity.class));


            }
        });
        */

    }

    private void populateFriendsList() {
        //Construct data source
        ArrayList<User> arrayFriends = User.getUsers();

        Log.d("MyFriendsActivity", arrayFriends.get(0).toString());

        //Create the adapter to convert the array to views
        PersonAdapter adapter = new PersonAdapter(this, arrayFriends);
        //recentEventsListView = (ListView) findViewById(R.id.lvRecentEvents);
        //attach the adapter to the listview
        friendsListView.setAdapter(adapter);
    }
}
