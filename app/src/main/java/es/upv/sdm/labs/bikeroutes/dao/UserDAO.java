package es.upv.sdm.labs.bikeroutes.dao;

import android.content.Context;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.pojo.User;

/**
 * Created by Anderson on 12/04/2016.
 */
public class UserDAO extends AbstractDAO<User> {

    public UserDAO(Context context) {
        super(context);
    }

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
}
