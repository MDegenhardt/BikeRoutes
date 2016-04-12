package es.upv.sdm.labs.bikeroutes.services;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.pojo.Event;
import es.upv.sdm.labs.bikeroutes.pojo.User;

/**
 * Created by Anderson on 12/04/2016.
 */
public class UserService extends AbstractService<User> {

    @Override
    public void insert(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public ArrayList<User> findBlock(int position, int length) {
        return null;
    }

    public User findByMail(String mail){
        return null;
    }

    public ArrayList<Event> findEvents(){
        return null;
    }

    public ArrayList<Event> findEventsGuest(){
        return null;
    }

    public ArrayList<Event> findBlockEvents(int position, int length){
        return null;
    }

    public ArrayList<Event> findBlockEventsGuest(int position, int length){
        return null;
    }

}
