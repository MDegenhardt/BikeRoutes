package es.upv.sdm.labs.bikeroutes.services;

import java.util.ArrayList;

/**
 * Created by Anderson on 12/04/2016.
 */
public abstract class AbstractService<T> {


    public abstract void insert(T t);

    public abstract void update(T t);

    public abstract void remove(int id);

    public abstract T findById(int id);

    public abstract ArrayList<T> findAll();

    public abstract ArrayList<T> findBlock(int position, int length);
}
