package com.adgad.tflstatus;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by arg01 on 16/09/2013.
 */
public class StatusHttpClient {

    private static String BASE_URL = "http://adgad-tfl.herokuapp.com";

    public String getTubeStatusData() {

        String strContent = "";
        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(BASE_URL));
            strContent = convertStreamToString(response.getEntity().getContent());
            //make sure we get valid content back!
            if(strContent.indexOf("Bakerloo") >= 0) return strContent;
        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        return null;
    };

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
