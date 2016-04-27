package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.PersonAdapter;
import es.upv.sdm.labs.bikeroutes.interfaces.AsyncExecutable;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.services.EventService;
import es.upv.sdm.labs.bikeroutes.services.ServerInfo;
import es.upv.sdm.labs.bikeroutes.services.UserService;

public class InviteActivity extends AppCompatActivity {

    ListView inviteListView;
    Context context;
    EditText etSearchUser;
    User user;
    int eventID;
    ProgressBar pbInvite;
    ArrayList<User> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        context = this;
        inviteListView = (ListView) findViewById(R.id.lvInviteSearch);
        etSearchUser = (EditText) findViewById(R.id.etSearchUser);
        Intent intent = new Intent(context, EventDescriptionActivity.class);
        intent.putExtra("eventID", eventID );
        pbInvite = (ProgressBar) findViewById(R.id.pbInvite);
        searchResults = new ArrayList<>();

        // When an item in the list is clicked
        inviteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("InviteActivity", "Item " + position + " clicked");
                EventService es = new EventService();
                User userInvited = (User) inviteListView.getAdapter().getItem(position);
                // TODO invite user
                es.invite(eventID, userInvited.getId());
            }
        });
    }

    public void searchFriends(View view) {
        searchResults.clear();
        UserService us = new UserService();
        if (etSearchUser.getText().length() == 0) {
            Toast.makeText(InviteActivity.this, getString(R.string.search_field_empty), Toast.LENGTH_LONG).show();
        } else {
            us.findByMailOrName(etSearchUser.getText().toString(), searchResults, new AsyncExecutable() {
                @Override
                public void postExecute(int option) {
                    pbInvite.setVisibility(View.GONE);
                    if(ServerInfo.RESPONSE_CODE==UserService.ERROR_INCORRECT_DATA){
                        Toast.makeText(InviteActivity.this, getString(R.string.error_incorrect_search_input), Toast.LENGTH_LONG).show();
                    } else {
                        //Create the adapter to convert the array to views
                        PersonAdapter adapter = new PersonAdapter(context, searchResults);
                        //attach the adapter to the listview
                        inviteListView.setAdapter(adapter);
                    }

                    if(searchResults.isEmpty()) {
                        Toast.makeText(InviteActivity.this, getString(R.string.no_results_found), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void preExecute(int option) {
                    pbInvite.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
