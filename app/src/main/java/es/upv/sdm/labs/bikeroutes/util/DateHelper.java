package es.upv.sdm.labs.bikeroutes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anderson on 12/04/2016.
 */
public class DateHelper {

    public static Date createDate(int day, int month, int year, int hour, int minutes){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss");
        Date d = new Date();
        try {
            d = formatter.parse(day+"/"+month+"/"+year+"/"+hour+":"+minutes+":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String toJson(Date date){
        if(date==null) return "{}";
        SimpleDateFormat s = new SimpleDateFormat("{\"day\":\"dd\", \"month\":\"MM\", " +
                            "\"year\":\"yyyy\", \"hour\":\"hh\", \"minutes\":\"mm\"}");
        return s.format(date);
    }

    public static String dateToString(Date date){
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        return s.format(date);
    }


    public static String timeToString(Date date){
        SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
        return s.format(date);
    }

    public static Date fromJson(String json){
//        String []data = json.substring(1, json.length()-1).split(",");
//        int day = Integer.parseInt(data[0].split(":")[2].)
        return null;
    }
}
