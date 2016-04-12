package es.upv.sdm.labs.bikeroutes.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import es.upv.sdm.labs.bikeroutes.interfaces.Enviable;

/**
 * Created by Anderson on 12/04/2016.
 */
public class Location {

    private double latitude;
    private double longitude;
    private String address;

    public Location(){
        this(0,0,"");
    }

    public Location(String address, Context context){
        this(Location.getLocationFromAddress(address, context));
    }

    public Location(double latitude, double longitude, Context context){
        this(Location.getLocationFromCoordinates(latitude, longitude, context));
    }


    public Location(Location location){
        this(location.getLatitude(), location.getLongitude(), location.getAddress());
    }

    public Location(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                '}';
    }

    public static Location getLocationFromAddress(String address, Context context){
        List<Address> l = null;
        double latitude = 0;
        double longitude = 0;
        try {
            l = new Geocoder(context, Locale.getDefault()).getFromLocationName(address, 1);
            if(l.size()>0){
                latitude = l.get(0).getLatitude();
                longitude = l.get(0).getLongitude();
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        if(l==null) latitude = longitude = 0;

        return new Location(latitude, longitude, address);
    }

    public static Location getLocationFromCoordinates(double latitude, double longitude, Context context){
        List<Address> l = null;
        String address = "";
        try {
            l = new Geocoder(context, Locale.getDefault()).getFromLocation(latitude, longitude, 1);
            if(l.size()>0){
                Address a = l.get(0);
                address += a.getAddressLine(0)+", "+a.getAddressLine(1)+", "+a.getAddressLine(2);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        if(l==null) address = "";
        return new Location(latitude, longitude, address);
    }
}
