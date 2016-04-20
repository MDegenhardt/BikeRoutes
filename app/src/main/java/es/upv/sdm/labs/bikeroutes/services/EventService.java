package es.upv.sdm.labs.bikeroutes.services;

import android.content.Context;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.interfaces.AsyncExecutable;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.util.JsonParser;

/**
 * Created by Anderson on 12/04/2016.
 */
public class EventService extends AbstractService<Event> {

    public static final int ADD_EVENT                   = 0;
    public static final int REMOVE_EVENT                = 1;
    public static final int UPDATE_EVENT                = 2;
    public static final int FIND_ALL_EVENTS             = 3;
    public static final int FIND_BLOCK_EVENTS           = 4;
    public static final int FIND_EVENT_BY_ID            = 5;
    public static final int FIND_USERS_CONFIRMED        = 6;
    public static final int FIND_USERS_INVITED          = 7;
    public static final int FIND_BLOCK_USERS_CONFIRMED  = 8;
    public static final int FIND_BLOCK_INVITED          = 9;

    public static final int ERROR_REMOVE_EVENT          = 500;
    public static final int ERROR_EVENT_NOT_FOUND       = 501;

    @Override
    public void findBlock(int position, int length, ArrayList<Event> responseReference, AsyncExecutable exec) {
        this.request(FIND_BLOCK_EVENTS, "find_block_events", new String[]{"position","length"},
                new String[]{String.valueOf(position),String.valueOf(length)},null,responseReference,exec);
    }

    @Override
    public void insert(Event event, AsyncExecutable exec) {
        this.send(ADD_EVENT,"add_event",new String[]{"event"},new String[]{event.toJson()},event,null,exec);
    }

    @Override
    public void update(Event event, AsyncExecutable exec) {
        this.send(UPDATE_EVENT, "update_event", new String[]{"event"},new String[]{event.toJson()},event,null,exec);
    }

    @Override
    public void remove(int id, AsyncExecutable exec) {
        this.send(REMOVE_EVENT, "remove_event", new String[]{"id"}, new String[]{String.valueOf(id)},null,null,exec);
    }

    @Override
    public void findById(int id, Event responseReference, AsyncExecutable exec) {
        this.request(FIND_EVENT_BY_ID, "find_event_by_id",new String[]{"id"}, new String[]{String.valueOf(id)},responseReference,null,exec);
    }

    @Override
    public void findAll(ArrayList<Event> responseReference, AsyncExecutable exec) {
        this.request(FIND_ALL_EVENTS, "find_all_events", null, null, null, responseReference, exec);
    }

    public void findUsers(AsyncExecutable exec){
        //COMPLETAR
    }

    public void findGuests(AsyncExecutable exec){
        //COMPLETAR
    }

    public void findBlockUsers(int position, int length, AsyncExecutable exec){
        //COMPLETAR
    }

    public void findBlockGuests(int position, int length, AsyncExecutable exec){
        //COMPLETAR
    }

    public void findUsers(){
        findUsers(null);
    }

    public void findGuests(){
        findGuests(null);
    }

    public void findBlockUsers(int position, int length){
        findBlockUsers(position, length, null);
    }

    public void findBlockGuests(int position, int length){
        findBlockGuests(position, length, null);
    }

    @Override
    public void postExecute(int option, Event objReference, ArrayList<Event> listReference) {
        switch (option){
            case ADD_EVENT:
            case UPDATE_EVENT:
                objReference.copy(JsonParser.toEvent(response));
                ServerInfo.RESPONSE_CODE = (objReference.getId()!=0) ? ServerInfo.RESPONSE_OK : ServerInfo.ERROR_UNKNOWN;
                break;
            case REMOVE_EVENT:
                ServerInfo.RESPONSE_CODE = (JsonParser.toInt(response)==0) ? ERROR_REMOVE_EVENT : ServerInfo.RESPONSE_OK;
                break;
            case FIND_EVENT_BY_ID:
                Event e = JsonParser.toEvent(response);
                if(e!=null) {
                    objReference.copy(e);
                    ServerInfo.RESPONSE_CODE = ServerInfo.RESPONSE_OK;
                } else ServerInfo.RESPONSE_CODE = ERROR_EVENT_NOT_FOUND;
                break;
            case FIND_BLOCK_EVENTS:
            case FIND_ALL_EVENTS:
                listReference.addAll(JsonParser.toEvents(response));
                ServerInfo.RESPONSE_CODE = ServerInfo.RESPONSE_OK;
                break;
            default: ServerInfo.RESPONSE_CODE = ServerInfo.ERROR_UNKNOWN;
        }
    }

    @Override
    public void preExecute(int option) {

    }


}
