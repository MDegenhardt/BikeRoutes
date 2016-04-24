package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import es.upv.sdm.labs.bikeroutes.R;

//login page
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean userRegistered = true;


        if (!userRegistered){
            //show create Account page
            startActivity(new Intent(this, CreateAccountActivity.class));
        }

        //for testing
//        startActivity(new Intent(this, DashboardActivity.class));
        //startActivity(new Intent(this,MyEventsActivity.class));
//        startActivity(new Intent(this,MapsActivity.class));
    }

    public void loginButtonClicked(View view){

        Log.d("MainActivity", "Login clicked");
        // if correct...
        startActivity(new Intent(this, DashboardActivity.class));

    }
}
