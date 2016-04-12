package es.upv.sdm.labs.bikeroutes.services;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.User;

/**
 * Created by Anderson on 12/04/2016.
 */
public class EventService extends AbstractService<Event> {

    @Override
    public void insert(Event event) {

    }

    @Override
    public void update(Event event) {

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

    @Override
    public ArrayList<Event> findBlock(int position, int length) {
        return null;
    }

    public ArrayList<User> findUsers(){
        return null;
    }

    public ArrayList<User> findGuests(){
        return null;
    }

    public ArrayList<User> findBlockUsers(int position, int length){
        return null;
    }

    public ArrayList<User> findBlockGuests(int position, int length){
        return null;
    }
}
