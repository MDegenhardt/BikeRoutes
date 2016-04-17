package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.util.DatePickerFragment;

public class UserDerscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_derscription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_description_menu, menu);
        menu.findItem(R.id.menuEdit).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuEdit:
                intent = new Intent(this, EditAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAddFriends:
                intent = new Intent(this, AddFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.menuMyFriends:
                intent = new Intent(this, MyFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAddPhoto:
                // adding photo
                break;
            case (android.R.id.home):
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
