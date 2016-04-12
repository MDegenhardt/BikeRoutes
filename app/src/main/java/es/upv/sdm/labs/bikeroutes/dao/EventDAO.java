package es.upv.sdm.labs.bikeroutes.dao;

import android.content.Context;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.model.Event;

/**
 * Created by Anderson on 12/04/2016.
 */
public class EventDAO extends AbstractDAO<Event> {

    public EventDAO(Context context) {
        super(context);
    }

    @Override
    public void insert(Event user) {

    }

    @Override
    public void update(Event user) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Event findById(int id) {
        return null;
    }

    @Override
    public ArrayList<Event> findAll() {
        return null;
    }
}
