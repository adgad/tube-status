package com.adgad.tflstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arg01 on 10/11/2013.
 */
public class StatusArrayAdapter extends ArrayAdapter<Status> {

    private final Context context;
    private final ArrayList<Status> statusArrayList;

    public StatusArrayAdapter(Context context, ArrayList<Status> statusArrayList) {

        super(context, R.layout.list_item, statusArrayList);

        this.context = context;
        this.statusArrayList = statusArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        // 3. Get the two text view from the rowView
        TextView topRow = (TextView) rowView.findViewById(R.id.firstLine);
        TextView bottomRow = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView img = (ImageView) rowView.findViewById(R.id.icon);
        ImageView alertIcon = (ImageView) rowView.findViewById(R.id.problem_icon);

        // 4. Set the text for textView
        String statusName = statusArrayList.get(position).getName();
        topRow.setText(statusName);
        bottomRow.setText(statusArrayList.get(position).getFullDetails());
        String colorId = statusName.toLowerCase().replaceAll("\\s+","");
        img.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier(colorId, "color", "com.adgad.tflstatus")));
        String statusShort = statusArrayList.get(position).getStatus();
        if(statusShort.equals("Good Service")) {
            alertIcon.setVisibility(View.GONE);
        }

        // 5. return rowView
        return rowView;
    }

}
