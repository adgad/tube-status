package com.adgad.tflstatus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
public class StatusActivity extends Activity {

    private StatusArrayAdapter mStatusArrayAdapter;

    private View mContentView;
    private View mLoadingView;
    private ListView mListView;
    private TextView mLastUpdated;
    private int mAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_load);

        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);
        mLastUpdated = (TextView) findViewById(R.id.lastUpdated);
        mAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);
        mStatusArrayAdapter = new StatusArrayAdapter(this, new ArrayList<Status>());
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mStatusArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Status selectedStatus = mStatusArrayAdapter.getItem(i);
                ColouredDialogBuilder adb = new ColouredDialogBuilder(StatusActivity.this);
                adb.setTitle(selectedStatus.getName() + " Line");
                adb.setMessage(selectedStatus.getFullDetails());
                if(selectedStatus.hasProblems()) {
                    adb.setIcon(R.drawable.ic_dialog_alert_holo_light);
                }
                String colorId = selectedStatus.getName().toLowerCase().replaceAll("\\s+","");
                String colour = getResources().getString(getResources().getIdentifier(colorId, "color", "com.adgad.tflstatus"));
                adb.setTitleColor(colour);
                adb.setDividerColor(colour);
                adb.setCancelable(true);
                adb.setPositiveButton("OK", null);
                adb.show();
            }
        });
        refreshItems();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshItems();
                break;
            default:
                break;
        }
        return true;
    }


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
            String retStatus = "error";
            try {
                if(data != null) {
                    statii = StatusJSONParser.getStatii(data);
                    if (statii.size() > 0) retStatus ="success";
                    Collections.sort(statii, new Comparator<com.adgad.tflstatus.Status>() {
                        @Override
                        public int compare(com.adgad.tflstatus.Status lhs, com.adgad.tflstatus.Status rhs) {
                            if (lhs.hasProblems()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retStatus;

        }
        protected void onPostExecute(String result) {
            if(result == "success") {
                mStatusArrayAdapter.clear();
                mStatusArrayAdapter.addAll(statii);
                // Set the "show" view to 0% opacity but visible, so that it is visible
                mContent.setAlpha(0f);
                mContent.setVisibility(View.VISIBLE);
                mLastUpdated.setText(getLastUpdatedTime());
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

                mStatusArrayAdapter.notifyDataSetChanged();
            }

        }
    }

    private void refreshItems() {
        mLoadingView.setAlpha(1f);
        mLoadingView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
        new JSONStatusTask(mContentView, mLoadingView, mAnimationDuration).execute();

    }

    private CharSequence getLastUpdatedTime() {
       return "Last Updated: " + android.text.format.DateFormat.format("EEEE HH:mm:ss", new java.util.Date());
    };
}