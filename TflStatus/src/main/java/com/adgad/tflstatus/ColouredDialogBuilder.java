package com.adgad.tflstatus;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.adgad.tflstatus.R;
public class ColouredDialogBuilder extends AlertDialog.Builder{

    /** The custom_body layout */
    private View mDialogView;

    /** optional dialog title layout */
    private TextView mTitle;
    /** optional alert dialog image */
    private ImageView mIcon;
    /** optional message displayed below title if title exists*/
    private TextView mMessage;
    /** The colored holo divider. You can set its color with the setDividerColor method */
    private View mDivider;

    public ColouredDialogBuilder(Context context) {
        super(context);

        mDialogView = View.inflate(context, R.layout.coloured_dialog_layout, null);
        setView(mDialogView);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
    }

    /**
     * Use this method to color the divider between the title and content.
     * Will not display if no title is set.
     *
     * @param colorString for passing "#ffffff"
     */
    public ColouredDialogBuilder setDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    @Override
    public ColouredDialogBuilder setTitle(CharSequence text) {
        mTitle.setText(text);
        return this;
    }

    public ColouredDialogBuilder setTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    @Override
    public ColouredDialogBuilder setMessage(int textResId) {
        mMessage.setText(textResId);
        return this;
    }

    @Override
    public ColouredDialogBuilder setMessage(CharSequence text) {
        mMessage.setText(text);
        return this;
    }

    @Override
    public ColouredDialogBuilder setIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        mIcon.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public ColouredDialogBuilder setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    /**
     * This allows you to specify a custom layout for the area below the title divider bar
     * in the dialog. As an example you can look at example_ip_address_layout.xml and how
     * I added it in TestDialogActivity.java
     *
     * @param resId  of the layout you would like to add
     * @param context
     */
    public ColouredDialogBuilder setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        ((FrameLayout)mDialogView.findViewById(R.id.customPanel)).addView(customView);
        return this;
    }

    @Override
    public AlertDialog show() {
        if (mTitle.getText().equals("")) mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);
        return super.show();
    }

}