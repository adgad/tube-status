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


        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(BASE_URL));
            content = response.getEntity().getContent();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        return convertStreamToString(content);
    };

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
