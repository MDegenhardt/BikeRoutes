package es.upv.sdm.labs.bikeroutes.model;

/**
 * Created by Anderson on 11/04/2016.
 */
public class EventType {

    public enum Type{BIKE, HIKE, RUN, OTHER}

    private int id;
    private String especification;
    private Type type;


    public EventType(){
        this(Type.OTHER);
    }

    public EventType(Type type){
        this(0, type, EventType.getType(type));
    }

    public EventType(Type type, String especification){
        this(0, type, especification);
    }

    public EventType(int id, Type type, String especification){
        this.id = id;
        this.type = type;
        this.especification = especification;
    }


    public String getEspecification() {
        return especification;
    }
    public int getId(){
        return this.id;
    }

    public void setEspecification(String especification) {
        this.especification = especification;
    }
    public void setId(int id){
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", especification='" + especification + '\'' +
                ", type=" + type +
                '}';
    }

    public static EventType.Type getType(String type){
        if(type.equals("BIKE")) return Type.BIKE;
        if(type.equals("HIKE")) return Type.HIKE;
        if(type.equals("RUN")) return Type.RUN;
        return Type.OTHER;
    }

    public static String getType(EventType.Type type){
        if(type.equals(Type.BIKE)) return "BIKE";
        if(type.equals(Type.HIKE)) return "HIKE";
        if(type.equals(Type.RUN)) return "RUN";
        return "OTHER";
    }
}
