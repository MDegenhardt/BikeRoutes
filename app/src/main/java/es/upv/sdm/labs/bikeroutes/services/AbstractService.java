package es.upv.sdm.labs.bikeroutes.services;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import es.upv.sdm.labs.bikeroutes.interfaces.AsyncExecutable;

/**
 * Created by Anderson on 12/04/2016.
 */
public abstract class AbstractService<T> {

    private enum TypeRequest{SEND, RECEIVE}
    //protected enum TypeReceive{INT, LIST, OBJ, UNDEFINED}

    //private TypeReceive typeReceive;

    /*public AbstractService(){
        this.typeReceive = TypeReceive.UNDEFINED;
    }*/

    //protected Integer[] intData;
    protected T objData;
    protected ArrayList<T> listData;
    //protected Context context;
    //protected InputStream response;

    //public void setTypeReceive(TypeReceive typeReceive){this.typeReceive = typeReceive;}

    public void insert(T t){
        insert(t, null);
    }

    public void update(T t){
        update(t, null);
    }

    public void remove(int id){
        remove(id, null);
    }

    public void findById(int id, T responseReference){
        findById(id, responseReference, null);
    }

    public void findBlock(int position, int length, ArrayList<T> responseReference){
        findBlock(position, length, responseReference, null);
    }

    public void findAll(ArrayList<T> responseReference){
        findAll(responseReference,null);
    }


    /*public T getDato(){
        return this.dato;
    }

    public ArrayList<T> getDatos(){
        return this.datos;
    }*/

    public abstract void findBlock(int position, int length, ArrayList<T> responseReference, AsyncExecutable exec);

    public abstract void insert(T t, AsyncExecutable exec);

    public abstract void update(T t, AsyncExecutable exec);

    public abstract void remove(int id, AsyncExecutable exec);

    public abstract void findById(int id, T responseReference, AsyncExecutable exec);

    public abstract void findAll(ArrayList<T> responseReference, AsyncExecutable exec);

    //public abstract void postExecute(int option, T responseReference, ArrayList<T> listReference);

    //public abstract void preExecute(int option);

    protected abstract void onResponse(int option, InputStream in);

    protected abstract void putParams(int option, String[] params);

    protected void onRequest(int option){}

    /*public AbstractService(Context context){
        this.context = context;
    }*/

    protected void request(int option, String action, String[]params, /*String values[], T objReference, ArrayList<T> listReference, */AsyncExecutable exec){
        request(option,action,params,null,exec);
    }

    protected void request(int option, String action, String[]params, String values[], /*T objReference, ArrayList<T> listReference, */AsyncExecutable exec){
        new MyAsyncTask(TypeRequest.RECEIVE, option, action, params, values, /*objReference, listReference, */exec).execute();
    }

    private void request(String action, String[] params, String[] values, int option){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ServerInfo.SERVER_SCHEME);
        builder.authority(ServerInfo.SERVER);
        for(String path : ServerInfo.PATHS) builder.appendPath(path);
        builder.appendQueryParameter("action", action);
        if(params!=null && params!=null)
            for(int k=0;k<params.length && k<values.length;k++) builder.appendQueryParameter(params[k], values[k]);
        try {
            URL url = new URL(builder.build().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            onResponse(option, connection.getInputStream());
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void send(int option, String action, String[]params, /*String values[], T objReference, ArrayList<T> listReference,*/ AsyncExecutable exec){
        send(option,action,params,null,exec);
    }

    protected void send(int option, String action, String[]params, String values[],/* T objReference, ArrayList<T> listReference,*/ AsyncExecutable exec){
        new MyAsyncTask(TypeRequest.SEND, option, action, params, values, /*objReference, listReference, */exec).execute();
    }

    private void send(String action, String [] params, String[] values, int option){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ServerInfo.SERVER_SCHEME);
        builder.authority(ServerInfo.SERVER);
        for(String path : ServerInfo.PATHS) builder.appendPath(path);
        String body = "action="+action;
        if(params!=null && params!=null)
            for(int k=0;k<params.length && k<values.length;k++) body += "&"+params[k]+"="+values[k];
        try {
            URL url = new URL(builder.build().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(body);
            osw.flush();
            osw.close();
            onResponse(option, connection.getInputStream());
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    protected class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private int option;
        private String action;
        private String[] params;
        private String[] values;
        private TypeRequest type;
        //private InputStream is;
        private AsyncExecutable exec;
        //private T objReference;
        //private ArrayList<T> listReference;

        public MyAsyncTask(TypeRequest type, int option, String action, String[] params, AsyncExecutable exec){
            this(type, option, action, params, null, exec);
        }

        public MyAsyncTask(TypeRequest type, int option, String action, String[] params, String[] values, /*T objReference, ArrayList<T> listReference, */AsyncExecutable exec){
            this.type = type;
            this.option = option;
            this.values = values;
            this.params = params;
            this.action = action;
            this.exec = exec;
            /*this.objReference = objReference;
            this.listReference = listReference;*/
        }


        @Override
        protected Void doInBackground(Void... params) {
            onRequest(option);
            if(values==null && this.params!=null) {
                this.values = new String[this.params.length];
                putParams(option, this.values);
            }
            if(type.equals(TypeRequest.RECEIVE)) request(this.action, this.params, this.values, option);
            else if(type.equals(TypeRequest.SEND)) send(this.action, this.params, this.values, option);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //response = this.is;
            //postExecute(option, objReference, listReference);
            if(exec!=null) exec.postExecute(this.option);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //preExecute(option);
            if(exec!=null) exec.preExecute(this.option);
        }
    }
}
