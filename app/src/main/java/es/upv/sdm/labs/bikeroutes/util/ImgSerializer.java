package es.upv.sdm.labs.bikeroutes.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class ImgSerializer implements Serializable{

    public static String serialize(Bitmap bitmap){
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, ba);
        byte []b = ba.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap deserialize(String s){
        byte []b = Base64.decode(s, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }


}
