package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.adapters.EventAdapter;
import es.upv.sdm.labs.bikeroutes.adapters.PersonAdapter;
import es.upv.sdm.labs.bikeroutes.dao.UserDAO;
import es.upv.sdm.labs.bikeroutes.interfaces.AsyncExecutable;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.services.ServerInfo;
import es.upv.sdm.labs.bikeroutes.services.UserService;

public class AddFriendsActivity extends AppCompatActivity {

    ListView friendsSearchListView;
    Context context;
    EditText etSearchFriend;
    User user;
    ProgressBar pbAddFriends;
    ArrayList<User> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        context = this;
        friendsSearchListView = (ListView) findViewById(R.id.lvFriendsSearch);
        etSearchFriend = (EditText) findViewById(R.id.etSearchFriend);
        UserDAO dao = new UserDAO(this);
        user = dao.findById(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id",0));
        pbAddFriends = (ProgressBar) findViewById(R.id.pbAddFriends);
        searchResults = new ArrayList<>();

        // When an item in the list is clicked
        friendsSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("AddFriendsActivity", "Item " + position + " clicked");
                UserService us = new UserService();
                final User newFriend = (User) friendsSearchListView.getAdapter().getItem(position);
                us.addFriend(user, newFriend);
                if(ServerInfo.RESPONSE_CODE==UserService.ERROR_ALREADY_FRIENDS) {
                    Toast.makeText(AddFriendsActivity.this, getString(R.string.already_friend) + " " + newFriend.getName(), Toast.LENGTH_LONG).show();
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle(getString(R.string.add_friend))
                            .setMessage(getString(R.string.do_you_really_want_to_add) + newFriend.getName() + getString(R.string.ad_your_friend))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(AddFriendsActivity.this, newFriend.getName()+" "+getString(R.string.added_as_friend), Toast.LENGTH_LONG).show();

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });
    }

    public void searchFriends(View view) {
        searchResults.clear();
        UserService us = new UserService();
        if (etSearchFriend.getText().length() == 0) {
            Toast.makeText(AddFriendsActivity.this, getString(R.string.search_field_empty), Toast.LENGTH_LONG).show();
        } else {
            us.findByMailOrName(etSearchFriend.getText().toString(), searchResults, new AsyncExecutable() {
                @Override
                public void postExecute(int option) {
                    pbAddFriends.setVisibility(View.GONE);
                    if(ServerInfo.RESPONSE_CODE==UserService.ERROR_INCORRECT_DATA){
                        Toast.makeText(AddFriendsActivity.this, getString(R.string.error_incorrect_search_input), Toast.LENGTH_LONG).show();
                    } else {
                        //Create the adapter to convert the array to views
                        PersonAdapter adapter = new PersonAdapter(context, searchResults);
                        //attach the adapter to the listview
                        friendsSearchListView.setAdapter(adapter);
                        //ib.setVisibility(View.GONE);
                    }

                    if(searchResults.isEmpty()) {
                        Toast.makeText(AddFriendsActivity.this, getString(R.string.no_results_found), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void preExecute(int option) {
                    pbAddFriends.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
