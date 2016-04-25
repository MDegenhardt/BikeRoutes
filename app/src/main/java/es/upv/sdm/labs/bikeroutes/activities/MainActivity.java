package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    }

    public void loginButtonClicked(View view){

        Log.d("MainActivity", "Login clicked");
        // if correct...
        startActivity(new Intent(this, DashboardActivity.class));

    }

    public void createAccount(View view) {
        startActivity(new Intent(this, CreateAccountActivity.class));
    }

    public void resetPassword(View view) {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }
}
