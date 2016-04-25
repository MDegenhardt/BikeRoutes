package es.upv.sdm.labs.bikeroutes.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import es.upv.sdm.labs.bikeroutes.R;

public class CreateAccountActivity extends AppCompatActivity {

    EditText etName;
    EditText etPassword;
    EditText etRepeatpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etName = (EditText) findViewById(R.id.etCreateName);
        etPassword = (EditText) findViewById(R.id.etCreatePassword);
        etRepeatpassword = (EditText) findViewById(R.id.etRepeatPassword);
    }

    @Override
    protected void onPause() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", etName.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.putString("repeat", etRepeatpassword.getText().toString());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        etName.setText(prefs.getString("name", ""));
        etPassword.setText(prefs.getString("password", ""));
        etRepeatpassword.setText(prefs.getString("repeat", ""));
        super.onResume();
    }
}


