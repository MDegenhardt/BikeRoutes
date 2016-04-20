package es.upv.sdm.labs.bikeroutes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.R;
import es.upv.sdm.labs.bikeroutes.enumerations.Gender;
import es.upv.sdm.labs.bikeroutes.model.Event;
import es.upv.sdm.labs.bikeroutes.model.EventType;
import es.upv.sdm.labs.bikeroutes.model.EventType.Type;
import es.upv.sdm.labs.bikeroutes.model.Location;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.services.EventService;
import es.upv.sdm.labs.bikeroutes.services.ServerInfo;
import es.upv.sdm.labs.bikeroutes.services.UserService;
import es.upv.sdm.labs.bikeroutes.util.DateHelper;
import es.upv.sdm.labs.bikeroutes.util.async.PostExecute;

//login page
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserService u = new UserService();
        EventService e = new EventService();
        final ArrayList<User> l = new ArrayList<>();
        final ArrayList<Event> m = new ArrayList<>();
        final User anderson = new User(1, "Anderson", "als@email.com", "123", "black", Gender.MALE, null);
        final User saiane = new User(7, "Saiane", "saiane@gmail.com", "123", "flocos", Gender.FEMALE, null);
        final User wendell = new User(8, "Wendell", "wendell@gmail.com", "123", "flocos", Gender.MALE, null);
        final User yago = new User(9, "Yago", "ogay@gmail.com", "123", "flocos", Gender.UNINFORMED, null);
        final User jessica = new User(23, "Jéssica", "j@email.com", "1234", "smile", Gender.FEMALE, null);

        final Event alcoy = new Event(9, new EventType(1,Type.BIKE,"Bike"), DateHelper.createDate(1,1,2018,0,0),new Location(41,45,85,""),
                new Location(42,0,0,"Rua José Rodrigues"),"Alcoy",true,anderson);
        final Event ibicuitinga = new Event(10, new EventType(2,Type.HIKE,"Hike"), DateHelper.createDate(8,8,2088,8,8),new Location(43,60,95,""),
                new Location(44,0,0,"Rua José Rodrigues, Ibicuitinga"),"Ibicuitinga",true,wendell);
        final Event solonopole = new Event(11, new EventType(28,Type.OTHER,"Caminhada"), DateHelper.createDate(4,8,2017,5,6),new Location(45,45,85,""),
                new Location(46,0,0,"Solonopole"),"Solonopole",true,saiane);
        final Event jucas = new Event(12, new EventType(3,Type.RUN,"Run"), DateHelper.createDate(1,11,2016,20,0),new Location(47,37.0902,-95.7129,""),
                new Location(48,0,0,"Ibicuitinga"),"Jucás",false,yago);
        final Event berlim = new Event(13, new EventType(30,Type.OTHER, "Senderismo"), DateHelper.createDate(7,1,2017,23,0),new Location(49,60,-95,""),
                new Location(50,0,0,"Berlim"),"Berlim",true,jessica);
        final Event valencia = new Event(14, new EventType(1,Type.BIKE, "Bike"), DateHelper.createDate(7,7,2017,19,7),new Location(51,60,-95,""),
                new Location(52,0,0,"Valencia"),"valencia",true,anderson);

        //1 6  9 16 20
        final User jenni = new User(26, "Jenni", "jennifer@email.com", "12345", "jennifer", Gender.FEMALE, null);

        Event china = new Event(18, new EventType(32, Type.OTHER, "other china"), DateHelper.createDate(31,1,2020,23,23),new Location(59,0,0,"Limoeiro Ceará"),
                new Location(60,0,0,"Hong Kong"), "china", false, anderson);

        Event china2 = new Event(19, new EventType(34, Type.OTHER, "other CHINA"), DateHelper.createDate(10,10,2010,12,12),new Location(61,12,12,"doze"),
                new Location(62,10,10,"dez"), "CHINA", true, jenni);

        Event china3 = new Event(20, new EventType(35, Type.OTHER, "other CHINA"), DateHelper.createDate(10,10,2010,12,12),new Location(63,12,12,"doze"),
                new Location(64,10,10,"dez"), "CHINA", true, jenni);

        china2.setOver(false);
        final User user = saiane;
        final Event event = alcoy;

        u.findBlockHistoric(1, 10, user, new PostExecute() {
            @Override
            public void postExecute(int option) {
                hey();
                printList(user.getHistoric());
            }
        });


                //for testing
        startActivity(new Intent(this, DashboardActivity.class));
        //startActivity(new Intent(this,MyEventsActivity.class));
        //startActivity(new Intent(this,EventDescriptionActivity.class));
    }

    private static void printList(ArrayList list){
        int k=0;
        for(Object o : list) {hey(o.toString());k++;}
        hey("TAMANHO", k+"");
    }

    private static void hey(String s, String x){
        Log.i(s, x);
    }

    private static void hey(Object o){
        if(o==null) Log.i("ANDERSON", "NULO");
        else if (o instanceof ArrayList) printList((ArrayList)o);
        else Log.i("ANDERSON", o.toString());
    }

    private static void hey(){
        hey("CODIGO", ServerInfo.RESPONSE_CODE+"");
    }
}
