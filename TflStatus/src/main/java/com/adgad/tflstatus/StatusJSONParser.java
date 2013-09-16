package com.adgad.tflstatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arg01 on 16/09/2013.
 */
public class StatusJSONParser {

    public static List<Status> getStatii(String data) throws JSONException{
        List<Status> statii = new ArrayList();
        JSONObject jObj = new JSONObject(data), currentLine;
        Iterator<?> keys = jObj.keys();


        while( keys.hasNext() ){
            String key = (String)keys.next();
            if( jObj.get(key) instanceof JSONObject ){
                currentLine = getObject(key, jObj);
                statii.add(new Status(key, getString("status", currentLine), getString("status_details", currentLine)));
            }
        }

        return statii;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }
}
