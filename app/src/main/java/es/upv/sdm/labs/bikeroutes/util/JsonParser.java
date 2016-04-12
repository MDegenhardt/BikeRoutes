package es.upv.sdm.labs.bikeroutes.util;

import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.util.pojo.EventPOJO;
import es.upv.sdm.labs.bikeroutes.util.pojo.UserPOJO;

/**
 * Created by Anderson on 12/04/2016.
 */
public class JsonParser {

    public static User toUser(InputStream in){
        UserPOJO u = new GsonBuilder().create().fromJson(new InputStreamReader(in), UserPOJO.class);
        return u.getUser();
    }

    public static Event toEvent(InputStream in){
        EventPOJO e =  new GsonBuilder().create().fromJson(new InputStreamReader(in), EventPOJO.class);
        return e.getEvent();
    }

    public static User toUser(String json){
        UserPOJO u = new GsonBuilder().create().fromJson(json, UserPOJO.class);
        return u.getUser();
    }

    public static Event toEvent(String json){
        EventPOJO e =  new GsonBuilder().create().fromJson(json, EventPOJO.class);
        return e.getEvent();
    }

}
