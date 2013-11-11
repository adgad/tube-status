package com.adgad.tflstatus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
public class StatusActivity extends Activity {

    StatusArrayAdapter adapter;

    private View mContentView;
    private View mLoadingView;
    private int mAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_load);

        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);

        mContentView.setVisibility(View.GONE);

        mAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);

        adapter = new StatusArrayAdapter(this, new ArrayList<Status>());
        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.listview);

        // 3. setListAdapter
        listView.setAdapter(adapter);
        new JSONStatusTask(mContentView, mLoadingView, mAnimationDuration).execute();
    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.status, menu);
//        return true;
//    }

    private class JSONStatusTask extends AsyncTask<String, Void, String> {

        private List<com.adgad.tflstatus.Status> statii = new ArrayList<com.adgad.tflstatus.Status>();

        private View mContent;
        private View mSpinner;
        private int mDuration;

        public JSONStatusTask(View content, View spinner, int duration) {
            mContent = content;
            mSpinner = spinner;
            mDuration = duration;

        }
        @Override
        protected String doInBackground(String... params) {
            String data = ( (new StatusHttpClient()).getTubeStatusData());

            try {
                statii = StatusJSONParser.getStatii(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (statii.get(0)).getStatus();

        }
        protected void onPostExecute(String result) {
            adapter.addAll(statii);
            // Set the "show" view to 0% opacity but visible, so that it is visible
            mContent.setAlpha(0f);
            mContent.setVisibility(View.VISIBLE);

            // Animate the "show" view to 100% opacity, and clear any animation listener set on the view.
            mContent.animate()
                    .alpha(1f)
                    .setDuration(mDuration)
                    .setListener(null);

            // Animate the "hide" view to 0% opacity.
            mSpinner.animate()
                    .alpha(0f)
                    .setDuration(mDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoadingView.setVisibility(View.GONE);
                        }
                    });

            adapter.notifyDataSetChanged();
        }
    }

}