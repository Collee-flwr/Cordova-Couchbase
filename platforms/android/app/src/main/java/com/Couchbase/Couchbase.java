package com.Couchbase;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryOptions;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.android.AndroidContext;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.ProxySelector;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import io.cordova.hellocordova.MainActivity;

/**
 * This class echoes a string called from JavaScript.
 */
public class Couchbase extends CordovaPlugin {


    static protected Database database = null;
    static protected Manager manager;
    DatabaseOptions opt = null;
    URL url = null;
    Context context;
    private Activity activity;
    private String uuid;


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        context = cordova.getContext();
        activity = cordova.getActivity();
        try {
            manager = new Manager(new AndroidContext(context), null);
        }
        catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {


       if (action.equals("createNewDatabase")){
           String dbName = args.getString(0);
           this.createNewDatabase(dbName,callbackContext);
       }

       else if (action.equals("insertDocument")){
           String dbName = args.getString(0);
           JSONObject doc = args.getJSONObject(1);
           this.insertDocument(dbName,doc,callbackContext);
       }
       else if (action.equals("query")){
           String dbName = args.getString(0);
           this.query(dbName,callbackContext);
       }
        return false;
    }



    private void createNewDatabase(String dbName, CallbackContext callbackContext){

        opt = new DatabaseOptions();
        opt.setCreate(true);

        try {

            database = manager.openDatabase(dbName,opt);
            Log.d("SmartFall","Created database");
            callbackContext.success("Created database: " + dbName);

        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

    }


    private void insertDocument(String dbName,JSONObject doc, CallbackContext callbackContext) throws JSONException {

        try {
            database = manager.getDatabase(dbName);

        }catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        Document document = database.createDocument();

        Map<String, Object> prop= null;

        try {

            prop = new ObjectMapper().readValue(doc.toString(), HashMap.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            document.putProperties(prop);

        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        String m = database.toString();
        String k = document.getId();
        //String d = document.getProperties().toString();
        Document d = database.getDocument(k);
        m = k + " " + d;
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setLimit(1);

        callbackContext.success(database.getDocumentCount());



    }




    private void uploadDocuments(String dbName, URL url, CallbackContext callbackContext){


    }

    private void query(String dbName,CallbackContext callbackContext){

        try {
            database = manager.getDatabase(dbName);

        }catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }



        QueryOptions queryOptions = new QueryOptions();
        Map<String, Object> docs = null;
        try {
            docs = database.getAllDocs(queryOptions);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        //find key
        String key = "";
        List<Map<String,Object>> rowsAsMaps = new ArrayList<Map<String,Object>>();
        List<QueryRow> rows = (List<QueryRow>) docs.get("rows");
        if (rows != null) {
            for (QueryRow row : rows) {
                key = row.getDocumentId();
            }
        }

        //get the document
        Document o = database.getDocument(key);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(o.getUserProperties().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callbackContext.success(o.getUserProperties().toString());


    }


}
