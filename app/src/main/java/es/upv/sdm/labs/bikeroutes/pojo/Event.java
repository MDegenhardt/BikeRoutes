package es.upv.sdm.labs.bikeroutes.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import es.upv.sdm.labs.bikeroutes.interfaces.Enviable;
import es.upv.sdm.labs.bikeroutes.enumerations.EventType;

/**
 * Created by Anderson on 11/04/2016.
 */
public class Event implements Enviable{

    private int id;
    private EventType type;
    private Date date;
    private Location departure;
    private Location arrival;
    private String description;
    private boolean secret;
    private User organizer;
    private ArrayList<User> guests;
    private ArrayList<User> users;

    public Event(){}

    public Event(EventType type, Date date, Location departure, Location arrival, String description, boolean secret, User organizer) {
        this(0, type, date, departure, arrival, description, secret, organizer, null, null);
    }

    public Event(int id, EventType type, Date date, Location departure, Location arrival, String description,
                 boolean secret, User organizer, ArrayList<User> guests, ArrayList<User> users) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.departure = departure;
        this.arrival = arrival;
        this.description = description;
        this.secret = secret;
        this.guests = guests;
        this.users = users;
        this.organizer = organizer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getDeparture() {
        return departure;
    }

    public void setDeparture(Location departure) {
        this.departure = departure;
    }

    public Location getArrival() {
        return arrival;
    }

    public void setArrival(Location arrival) {
        this.arrival = arrival;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public User getOrganizer(){
        return this.organizer;
    }

    public void setOrganizer(User organizer){
        this.organizer = organizer;
    }

    public ArrayList<User> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<User> guests) {
        this.guests = guests;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type=" + type +
                ", date=" + date +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", description='" + description + '\'' +
                ", secret=" + secret +
                ", organizer=" + organizer +
                ", guests=" + guests +
                ", users=" + users +
                '}';
    }


    public static ArrayList<Event> getEvents() {
        ArrayList<Event> events = new ArrayList<Event>();

        //some test data
        /*android.location.Location exampleLocation1 = new android.location.Location("");//provider name is unecessary
        exampleLocation1.setLatitude(0.0d);//your coords
        exampleLocation1.setLongitude(0.0d);

        android.location.Location exampleLocation2 = new android.location.Location("");//provider name is unecessary
        exampleLocation2.setLatitude(100.0d);//your coords
        exampleLocation2.setLongitude(0.0d);*/

        Location exampleLocation1 = new Location(0,0,"");
        Location exampleLocation2 = new Location(100,0,"");


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss");
        Date d1, d2, d3, d4, d5;
        d1 = d2 = d3 = d4 = d5 = new Date();
        try {
            d1 = formatter.parse("05/03/2016/12:00:00");
            d2 = formatter.parse("15/05/2016/12:45:00");
            d3 = formatter.parse("12/06/2016/11:06:00");
            d4 = formatter.parse("15/05/2016/12:45:00");
            d5 = formatter.parse("12/06/2016/11:06:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = new User("Anderson", "anderson@email.com", "234", null);

        events.add(new Event(EventType.BIKE, d1, exampleLocation1, exampleLocation2, "Some description text...", false, user));
        events.add(new Event(EventType.OTHER, d2, exampleLocation2, exampleLocation1, "Some description text...", false, user ));
        events.add(new Event(EventType.HIKE, d3, exampleLocation1, exampleLocation2, "Some description text...", true, user ));
        events.add(new Event(EventType.RUN, d4, exampleLocation2, exampleLocation1, "Some description text...", false, user ));
        events.add(new Event(EventType.HIKE, d5, exampleLocation1, exampleLocation2, "Some description text...", true, user ));

        return events;
    }

    @Override
    public String toJson() {
        return null;
    }
}
