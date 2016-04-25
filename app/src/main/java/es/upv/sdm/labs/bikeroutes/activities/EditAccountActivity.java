package es.upv.sdm.labs.bikeroutes.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Date;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.util.AccountDatePickerFragment;
import es.upv.sdm.labs.bikeroutes.util.DateHelper;

public class EditAccountActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    Date date = new Date();
    Button btnUserBirth;
    EditText name;
    EditText description;
    RadioGroup sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        name = (EditText) findViewById(R.id.etName);
        sex = (RadioGroup) findViewById(R.id.rgSex);
        description = (EditText) findViewById(R.id.etUserDescription);
        btnUserBirth = (Button) findViewById(R.id.btnUserBirth);
    }

    @Override
    protected void onPause() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name.getText().toString());
        editor.putString("birth", btnUserBirth.getText().toString());
        editor.putInt("sex", sex.getCheckedRadioButtonId());
        editor.putString("description", description.getText().toString());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(prefs.getString("name", ""));
        btnUserBirth.setText(prefs.getString("birth", "Select date"));
        sex.check(prefs.getInt("sex", R.id.female));
        description.setText(prefs.getString("description", ""));
        super.onResume();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new AccountDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuAddFriends:
                intent = new Intent(this, AddFriendsActivity.class);
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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        Log.w("CreateEventActivity","Date = " + year + monthOfYear + dayOfMonth);

        date.setDate(dayOfMonth);
        date.setMonth(monthOfYear);
        date.setYear(year);

        Log.w("CreateEventActivity","Date = " + date.toString());

        String dateString = DateHelper.dateToString(date);

        btnUserBirth.setText(dateString);
    }

}
