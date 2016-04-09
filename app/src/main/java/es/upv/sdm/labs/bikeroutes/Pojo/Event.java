package es.upv.sdm.labs.bikeroutes.pojo;

import android.location.Location;

import java.util.ArrayList;

public class Event {


    public int ID;
    public int type;
    public String date;
    public String time;
    public Location startLocation;
    public String startName;
    public Location endLocation;
    public String endName;
    public String description;
    public boolean isSecret;
    public int organizingPersonID;

    public Event(int ID, int type, String date, String time,
                 Location startLocation, String startName, Location endLocation, String endName,
                 String description, boolean isSecret, int organizingPersonID) {
        this.ID = ID;
        //Type: 0 = bike, 1 = hiking, 2 = running
        this.type = type;
        this.date = date;
        this.time = time;
        this.startLocation = startLocation;
        this.startName = startName;
        this.endLocation = endLocation;
        this.endName = endName;
        this.description = description;
        this.isSecret = isSecret;
        this.organizingPersonID = organizingPersonID;
    }

    public Event(int ID) {
        this.ID = ID;
    }

    public static ArrayList<Event> getEvents() {
        ArrayList<Event> events = new ArrayList<Event>();

        //some test data
        Location exampleLocation1 = new Location("");//provider name is unecessary
        exampleLocation1.setLatitude(0.0d);//your coords
        exampleLocation1.setLongitude(0.0d);

        Location exampleLocation2 = new Location("");//provider name is unecessary
        exampleLocation2.setLatitude(100.0d);//your coords
        exampleLocation2.setLongitude(0.0d);

        events.add(new Event(0, 0, "05.03.16", "12:00", exampleLocation1, "Valencia", exampleLocation2, "Gandía", "Some description text...", false, 0 ));
        events.add(new Event(1, 1, "15.05.16", "12:45", exampleLocation2, "Gandía", exampleLocation1, "Valencia", "Some description text...", false, 0 ));
        events.add(new Event(2, 2, "12.06.16", "11:06", exampleLocation1, "Valencia", exampleLocation2, "Gandía", "Some description text...", true, 0 ));
        events.add(new Event(3, 1, "15.05.16", "12:45", exampleLocation2, "Gandía", exampleLocation1, "Valencia", "Some description text...", false, 0 ));
        events.add(new Event(4, 2, "12.06.16", "11:06", exampleLocation1, "Valencia", exampleLocation2, "Gandía", "Some description text...", true, 0 ));

        return events;
    }

}
