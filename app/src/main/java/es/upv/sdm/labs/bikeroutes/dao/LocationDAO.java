package es.upv.sdm.labs.bikeroutes.dao;

import android.content.Context;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.pojo.Location;

/**
 * Created by Anderson on 12/04/2016.
 */
public class LocationDAO extends AbstractDAO<Location> {

    public LocationDAO(Context context) {
        super(context);
    }

    @Override
    public void insert(Location location) {

    }

    @Override
    public void update(Location location) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Location findById(int id) {
        return null;
    }

    @Override
    public ArrayList<Location> findAll() {
        return null;
    }
}
