package es.upv.sdm.labs.bikeroutes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.util.JsonParser;

public class TesteActivity extends AppCompatActivity {

    ImageView ivFoto;
    TextView tvNome, tvEmail, tvGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvGenero = (TextView) findViewById(R.id.tvGenero);
        ivFoto = (ImageView) findViewById(R.id.ivFoto);

        User u = MainActivity.user;
        tvNome.setText(u.getName());
        tvEmail.setText(u.getMail());
        tvGenero.setText(u.getGender().toString());
        if(u.getImage()!=null)ivFoto.setImageBitmap(u.getImage());
    }
}
