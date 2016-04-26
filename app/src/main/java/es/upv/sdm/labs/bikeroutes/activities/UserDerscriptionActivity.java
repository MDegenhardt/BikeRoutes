package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.dao.UserDAO;
import es.upv.sdm.labs.bikeroutes.enumerations.Gender;
import es.upv.sdm.labs.bikeroutes.model.User;

public class UserDerscriptionActivity extends AppCompatActivity {

    ImageView ivUser, ivGender;
    TextView tvName, tvDescription;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_derscription);
        ivUser = (ImageView) findViewById(R.id.ivUser);
        ivGender = (ImageView) findViewById(R.id.ivGender);
        tvName = (TextView) findViewById(R.id.tvUserName);
        tvDescription = (TextView) findViewById(R.id.tvUserDescription);
        UserDAO dao = new UserDAO(this);
        user = dao.findById(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id",0));

        if(user.hasImage()){
            Bitmap b = user.getImage();
            ivUser.setVisibility(View.VISIBLE);
            ivUser.setMinimumHeight(b.getHeight());
            ivUser.setMinimumWidth(b.getWidth());
            ivUser.setImageBitmap(b);
        } else {
            ivUser.setVisibility(View.GONE);
        }

        if(user.getGender()== Gender.UNINFORMED) ivGender.setVisibility(View.GONE);
        else {
            ivGender.setVisibility(View.VISIBLE);
            ivGender.setImageResource(user.getGender()==Gender.FEMALE ? R.drawable.female : R.drawable.male);
        }
        tvName.setText(user.getName());
        tvDescription.setText(user.getDescription());

        dao.close();
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
                startActivityForResult(intent,48);
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
