package es.upv.sdm.labs.bikeroutes.model;

import android.graphics.Bitmap;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.interfaces.Enviable;
import es.upv.sdm.labs.bikeroutes.enumerations.Gender;

/**
 * Created by Anderson on 11/04/2016.
 */
public class User{

    private int id;
    private String name;
    private String mail;
    private String password;
    private String description;
    private Gender gender;
    private Bitmap image;
    private ArrayList<User> friends;
    private ArrayList<Event> events;
    private ArrayList<Event> invited;

    public User(){
        this("", "", "", null);
    }

    public User(String name, String mail, String password, Bitmap image) {
        this(0, name, mail, password, "", Gender.UNINFORMED, image, new ArrayList<User>(), new ArrayList<Event>(), new ArrayList<Event>());
    }

    public User(int id, String name, String mail, String password, String description, Gender gender,
                Bitmap image, ArrayList<User> friends, ArrayList<Event> events, ArrayList<Event> invited) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.description = description;
        this.gender = gender;
        this.image = image;
        this.friends = friends;
        this.events = events;
        this.invited = invited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getInvited() {
        return invited;
    }

    public void setInvited(ArrayList<Event> invited) {
        this.invited = invited;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", gender=" + gender +
                ", image=" + image +
                ", friends=" + friends +
                ", events=" + events +
                ", invited=" + invited +
                '}';
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> persons = new ArrayList<User>();


        persons.add(new User("Juan", "juan@email.com", "123", null ));
        persons.add(new User("Hans", "hans@email.com", "123", null ));
        persons.add(new User("Max", "max@email.com", "123", null ));
        persons.add(new User("Klaus", "klaus@email.com", "123", null ));
        persons.add(new User("Lucas", "lucas@email.com", "123", null ));

        return persons;
    }
}
