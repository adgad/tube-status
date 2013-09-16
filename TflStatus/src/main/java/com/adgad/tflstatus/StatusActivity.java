package com.adgad.tflstatus;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
public class StatusActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        TextView textView = (TextView) findViewById(R.id.textView);

        new JSONStatusTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }

    private class JSONStatusTask extends AsyncTask<String, Void, String> {

        List<com.adgad.tflstatus.Status> statii = new ArrayList<com.adgad.tflstatus.Status>();

        @Override
        protected String doInBackground(String... params) {
            String data = ( (new StatusHttpClient()).getTubeStatusData());

            try {
                statii = StatusJSONParser.getStatii(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ((com.adgad.tflstatus.Status)statii.get(0)).getStatus();

        }
        protected void onPostExecute(String result) {

            // add AndroidViews Cards
            com.adgad.tflstatus.Status currentStatus = statii.get(0);
            TextView tv = (TextView) findViewById(R.id.textView);
            tv.setText(currentStatus.getStatus());
        }
    }

}