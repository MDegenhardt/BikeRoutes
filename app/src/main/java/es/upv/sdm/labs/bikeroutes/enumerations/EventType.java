package es.upv.sdm.labs.bikeroutes.enumerations;

/**
 * Created by Anderson on 11/04/2016.
 */
public enum EventType {

    BIKE, HIKE, RUN, OTHER;

    EventType(){
        this.especification = "";
    }

    private String especification;

    public String getEspecification() {
        return especification;
    }

    public void setEspecification(String especification) {
        this.especification = especification;
    }

    public static EventType getType(String type){
        if(type.equals("BIKE")) return BIKE;
        if(type.equals("HIKE")) return HIKE;
        if(type.equals("RUN")) return RUN;
        return OTHER;
    }
}
