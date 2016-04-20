package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.enumerations.EventType;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Polyline route = null;
    ProgressBar progressBar;
    UiSettings uiSettings;

    Intent intent;



    //test data
    LatLng pos1 = new LatLng(39.4666667, -0.3666667);
    LatLng pos2 = new LatLng(39.1666667, -0.25);
    String pos1Title = "StartName";
    String pos1Desc  = "StartDescription";
    String pos2Title = "FinishName";
    String pos2Desc  = "FinishDescription";
    EventType type = EventType.HIKE;
//    int img = (type==EventType.HIKE) ? R.drawable.hike : (type==EventType.RUN) ? R.drawable.running : R.drawable.bike;


    // LatLngBounds (LatLng southwest, LatLng northeast)
//    LatLngBounds routeBounds = (pos1.latitude < pos2.latitude) ? new LatLngBounds(pos1, pos2) : new LatLngBounds(pos2,pos1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        intent = getIntent();

        int evID;
        evID = intent.getIntExtra("EventID", 0 );
        Log.d("MapsActivity", "EvID: " + Integer.toString(evID));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (isConnectionAvailable()) {
            (new RouteAsyncTask()).execute(pos1, pos2);
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        // Set the camera to the greatest possible zoom level that includes the bounds
//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(routeBounds, 0));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos1, 10));

        addMarker(pos1.latitude, pos1.longitude, pos1Title,
                pos1Desc, BitmapDescriptorFactory.HUE_GREEN);

        addMarker(pos2.latitude, pos2.longitude, pos2Title,
               pos2Desc, BitmapDescriptorFactory.HUE_BLUE);

        supportInvalidateOptionsMenu();
    }

    private void addMarker(double latitude, double longitude, String title, String snippet, float color) {
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(latitude, longitude));
        options.title(title);
        options.snippet(snippet);
        options.icon(BitmapDescriptorFactory.defaultMarker(color));

        mMap.addMarker(options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mMap != null) {
            getMenuInflater().inflate(R.menu.maps_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mNormalMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mTerrainMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mSatelliteMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
        }
        return true;
    }

    /**
     * Check whether Internet connectivity is available.
     */
    private boolean isConnectionAvailable() {

        // Get a reference to the ConnectivityManager
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get information for the current default data network
        NetworkInfo info = manager.getActiveNetworkInfo();
        // Return true if there is network connectivity
        return ((info != null) && info.isConnected());
    }


    private class RouteAsyncTask extends AsyncTask<LatLng, Void, List<LatLng>> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<LatLng> doInBackground(LatLng... params) {

            List<LatLng> pointsList = null;

            String typeStr = (type==EventType.BIKE) ? "bicycling" : "walking";

            String uri = String.format(Locale.US, "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=%1$f,%2$f&destination=%3$f,%4$f&mode=" + typeStr + "&key="
                    + getResources().getString(R.string.google_maps_key) ,
                    params[0].latitude, params[0].longitude, params[1].latitude, params[1].longitude );

            Log.d("MapsActivity", Double.toString(params[0].latitude));
            Log.d("MapsActivity", uri);
            try {
                URL url = new URL(uri);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject object = new JSONObject(response.toString());
                    JSONArray routesArray = object.getJSONArray("routes");
                    JSONObject route = routesArray.getJSONObject(0);
                    JSONObject polyline = route.getJSONObject("overview_polyline");
                    pointsList = PolyUtil.decode(polyline.getString("points"));

                    JSONArray legsArray = route.getJSONArray("legs");
                    JSONObject legs = legsArray.getJSONObject(0);

                    JSONObject distance = legs.getJSONObject("distance");
                    JSONObject duration = legs.getJSONObject("duration");

                    int distValue = (int) distance.get("value");
                    String distValueStr = (String) distance.get("text");

                    int durValue = (int) duration.get("value");
                    String durValueStr = (String) duration.get("text");

//                    Log.i("MapsActivity", Integer.toString(distValue));
                    Log.i("MapsActivity", distValueStr);

//                    Log.i("MapsActivity", Integer.toString(durValue));
                    Log.i("MapsActivity", durValueStr);

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return pointsList;
        }

        @Override
        protected void onPostExecute(List<LatLng> result) {
            if (result != null) {
                if (route != null) {
                    route.remove();
                }
                route = mMap.addPolyline(new PolylineOptions()
                        .addAll(result)
                        .color(Color.parseColor("#FF0000"))
                        .width(12)
                        .geodesic(true));
            } else {
                Toast.makeText(MapsActivity.this, "Could not display route", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
