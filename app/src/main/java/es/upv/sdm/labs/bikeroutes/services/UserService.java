package es.upv.sdm.labs.bikeroutes.services;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.interfaces.AsyncExecutable;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.util.JsonParser;

/**
 * Created by Anderson on 12/04/2016.
 */
public class UserService extends AbstractService<User> {

    public static final int ADD_USER                        = 0;
    public static final int FIND_USER_BY_ID                 = 1;
    public static final int FIND_USER_BY_MAIL               = 2;
    public static final int UPDATE_USER                     = 3;
    public static final int REMOVE_USER                     = 4;
    public static final int FIND_ALL_USERS                  = 5;
    public static final int FIND_BLOCK_USERS                = 6;
    public static final int FIND_EVENTS_CONFIRMED           = 7;
    public static final int FIND_EVENTS_INVITED             = 8;
    public static final int FIND_EVENTS_HISTORIC            = 9;
    public static final int FIND_BLOCK_EVENTS_CONFIRMED     = 10;
    public static final int FIND_BLOCK_EVENTS_INVITED       = 11;
    public static final int FIND_BLOCK_EVENTS_HISTORIC      = 12;
    public static final int ADD_FRIEND                      = 13;
    public static final int REMOVE_FRIEND                   = 14;
    public static final int FIND_FRIENDS                    = 15;
    public static final int FIND_BLOCK_FRIENDS              = 16;



    public static final int ERROR_ADD_USER_MAIL     = 101;
    public static final int ERROR_REMOVE_USER       = 102;
    public static final int ERROR_ADD_FRIEND        = 103;
    public static final int ERROR_REMOVE_FRIEND     = 104;
    public static final int ERROR_ALREADY_FRIENDS   = 105;
    public static final int ERROR_USER_NOT_FOUND    = 106;

    /*public UserService(Context context) {
        super(context);
    }*/

    @Override
    public void insert(User user, AsyncExecutable exec) {
        //this.dato = user;
        this.send(ADD_USER, "add_user", new String[]{"user"}, new String[]{user.toJson()}, user, null, exec);
    }

    @Override
    public void update(User user, AsyncExecutable exec) {
        this.send(UPDATE_USER, "update_user", new String[]{"user"}, new String[]{user.toJson()}, null, null, exec);
    }

    @Override
    public void remove(int id, AsyncExecutable exec) {
        this.send(REMOVE_USER, "remove_user", new String[]{"id"}, new String[]{String.valueOf(id)}, null, null, exec);
    }

    @Override
    public void findById(int id, User responseReference, AsyncExecutable exec) {
        this.objData = responseReference;
        this.request(FIND_USER_BY_ID, "find_user_by_id", new String[]{"id"}, new String[]{String.valueOf(id)}, responseReference, null, exec);
    }

    @Override
    public void findAll(ArrayList<User> responseReference, AsyncExecutable exec) {
        this.request(FIND_ALL_USERS, "find_all_users", null, null, null, responseReference, exec);
    }

    @Override
    public void findBlock(int position, int length, ArrayList<User> responseReference, AsyncExecutable exec) {
        this.request(FIND_BLOCK_USERS, "find_block_users", new String[]{"position", "length"},
                new String[]{String.valueOf(position), String.valueOf(length)}, null, responseReference, exec);
    }

    public void findByMail(String mail, User responseReference, AsyncExecutable exec){
        this.request(FIND_USER_BY_MAIL, "find_user_by_mail", new String[]{"mail"}, new String[]{mail}, responseReference, null, exec);
    }

    public void findEvents(AsyncExecutable exex){
        //COMPLETAR
    }

    public void findEventsGuest(AsyncExecutable exex){
        //COMPLETAR
    }

    public void findHistoric(AsyncExecutable exex){
        //COMPLETAR
    }

    public void findBlockEvents(int position, int length, AsyncExecutable exex){
        //COMPLETAR
    }

    public void findBlockEventsGuest(int position, int length, AsyncExecutable exex){
        //COMPLETAR
    }

    public void findBlockHistoric(int position, int length, AsyncExecutable exex){
        //COMPLETAR
    }

    public void findFriends(User user, AsyncExecutable exec){
        this.request(FIND_FRIENDS, "find_friends", new String[]{"user_id"}, new String[]{String.valueOf(user.getId())}, null, user.getFriends(), exec);
    }

    public void findBlockFriends(User user, int position, int length, AsyncExecutable exec){
        this.request(FIND_FRIENDS, "find_block_friends", new String[]{"user_id", "position", "length"},
                new String[]{String.valueOf(user.getId()), String.valueOf(position), String.valueOf(length)}, null, user.getFriends(), exec);
    }

    public void removeFriend(User user, User oldFriend, AsyncExecutable exec){
        this.send(ADD_FRIEND, "remove_friend", new String[]{"user_id", "friend_id"},
                new String[]{String.valueOf(user.getId()), String.valueOf(oldFriend.getId())}, oldFriend, user.getFriends(), exec);
    }

    public void addFriend(User user, User newFriend, AsyncExecutable exec){
        this.send(ADD_FRIEND, "add_friend", new String[]{"user_id", "friend_id"},
                new String[]{String.valueOf(user.getId()), String.valueOf(newFriend.getId())}, newFriend, user.getFriends(), exec);
    }

    public void findByMail(String mail, User responseReference){
        findByMail(mail, responseReference, null);
    }

    public void findEvents(){
        findEvents(null);
    }

    public void findEventsGuest(){
        findEventsGuest(null);
    }

    public void findHistoric(){
        findHistoric(null);
    }

    public void findBlockEvents(int position, int length){
        findBlockEvents(position, length, null);
    }

    public void findBlockEventsGuest(int position, int length){
        findBlockEventsGuest(position, length, null);
    }

    public void findBlockHistoric(int position, int length){
        findBlockHistoric(position, length, null);
    }

    public void findFriends(User user){
        findFriends(user, null);
    }

    public void findBlockFriends(User user, int position, int length){
        findBlockFriends(user, position, length, null);
    }

    public void removeFriend(User user, User oldFriend){
        removeFriend(user, oldFriend, null);
    }

    public void addFriend(User user, User newFriend){
        addFriend(user, newFriend, null);
    }

     @Override
     public void onResponse(int option, InputStream in) {
        switch (option){
            case FIND_USER_BY_ID:
                Log.i("NUNCA AQUI", "ONRESPONSE");
                User u = JsonParser.toUser(in);
                if(u!=null) {
                    objData.copy(u);
                    ServerInfo.RESPONSE_CODE = ServerInfo.RESPONSE_OK;
                } else ServerInfo.RESPONSE_CODE = ERROR_USER_NOT_FOUND;
                break;
            default:
                ServerInfo.RESPONSE_CODE = ServerInfo.ERROR_UNKNOWN;
        }
    }

    @Override
    public void postExecute(int option, User objReference, ArrayList<User> listReference) {
        switch (option){
            case ADD_USER:
                Integer res[] = JsonParser.toInts(response);
                try {
                    ServerInfo.RESPONSE_CODE = res[0];
                    objReference.setId(res[1]);
                } catch (NullPointerException | ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                    ServerInfo.RESPONSE_CODE = ServerInfo.ERROR_UNKNOWN;
                }
                break;
            //case FIND_USER_BY_ID: break;
            case FIND_USER_BY_MAIL:
                User u = JsonParser.toUser(response);
                if(u!=null) {
                    objReference.copy(u);
                    ServerInfo.RESPONSE_CODE = ServerInfo.RESPONSE_OK;
                } else ServerInfo.RESPONSE_CODE = ERROR_USER_NOT_FOUND;
                break;
            case FIND_BLOCK_USERS:
            case FIND_ALL_USERS:
            case FIND_FRIENDS:
            case FIND_BLOCK_FRIENDS:
                listReference.addAll(JsonParser.toUsers(response));
                ServerInfo.RESPONSE_CODE = ServerInfo.RESPONSE_OK;
                break;
            case UPDATE_USER:
                ServerInfo.RESPONSE_CODE = JsonParser.toInt(response);
                break;
            case ADD_FRIEND:
                int aux = JsonParser.toInt(response);
                ServerInfo.RESPONSE_CODE = (aux==0) ? ERROR_REMOVE_USER : aux;
                if(ServerInfo.RESPONSE_CODE==ServerInfo.RESPONSE_OK) listReference.add(objReference);
                break;
            case REMOVE_FRIEND:
                ServerInfo.RESPONSE_CODE = (JsonParser.toInt(response)==0) ? ERROR_REMOVE_USER : ServerInfo.RESPONSE_OK;
                if(ServerInfo.RESPONSE_CODE==ServerInfo.RESPONSE_OK) listReference.remove(objReference);
                break;
            case REMOVE_USER:
                ServerInfo.RESPONSE_CODE = (JsonParser.toInt(response)==0) ? ERROR_REMOVE_USER : ServerInfo.RESPONSE_OK;
                break;
            default:
                ServerInfo.RESPONSE_CODE = ServerInfo.ERROR_UNKNOWN;
        }
    }

    @Override
    public void preExecute(int option) {

    }
}
