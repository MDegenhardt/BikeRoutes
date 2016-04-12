package es.upv.sdm.labs.bikeroutes.enumerations;

/**
 * Created by Anderson on 11/04/2016.
 */
public enum EventType {

    BIKE, HIKE, RUN, OTHER;

    private String especification;

    public String getEspecification(){
        return this.especification;
    }

    public void setEspecification(String especification){
        this.especification = especification;
    }

}
