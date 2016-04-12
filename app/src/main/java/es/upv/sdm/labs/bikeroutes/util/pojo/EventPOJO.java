package es.upv.sdm.labs.bikeroutes.util.pojo;

import java.util.Date;

import es.upv.sdm.labs.bikeroutes.enumerations.EventType;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.Location;

/**
 * Created by Anderson on 12/04/2016.
 */
public class EventPOJO extends AbstractPOJO{

    private Events[] events;

    public EventPOJO(){}

    public EventPOJO(Event event){
        this.events = new Events[1];
        Events e = this.events[0] = new Events();
        e.setId(event.getId());
        e.setType(event.getType().toString());
        e.setEspecificationType(event.getType().getEspecification());
        e.setDate(event.getDate());
        e.setDeparture(event.getDeparture());
        e.setArrival(event.getArrival());
        e.setDescription(event.getDescription());
        e.setSecret(event.isSecret());
        e.setIdOrganizer(event.getOrganizer().getId());
    }

    public Event getEvent(){
        if(this.events==null || this.events.length==0) return null;
        Events e = this.events[0];
        Event res = new Event();
        res.setId(e.getId());
        res.setType(EventType.getType(e.getType()));
        res.getType().setEspecification(e.getEspecificationType());
        res.setDate(e.getDate());
        res.setDeparture(e.getDeparture());
        res.setArrival(e.getArrival());
        res.setDescription(e.getDescription());
        res.setSecret(e.isSecret());
        res.getOrganizer().setId(e.getIdOrganizer());
        return res;
    }

    public Events[] getEvents() {
        return events;
    }

    public void setEvents(Events[] events) {
        this.events = events;
    }

    private class Events{

        private int id;
        private String type;
        private String especificationType;
        private Date date;
        private Location departure;
        private Location arrival;
        private String description;
        private boolean secret;
        private int idOrganizer;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEspecificationType() {
            return especificationType;
        }

        public void setEspecificationType(String especificationType) {
            this.especificationType = especificationType;
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

        public int getIdOrganizer() {
            return idOrganizer;
        }

        public void setIdOrganizer(int idOrganizer) {
            this.idOrganizer = idOrganizer;
        }
    }
}
