package com.taiyeoloriade.measureme.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.model.MeasureActivity;
import com.taiyeoloriade.measureme.model.MeasureList;
import com.taiyeoloriade.measureme.utility.DatabaseHelper;

import org.greenrobot.eventbus.EventBus;

public class ViewDialog {


    DatabaseHelper db;
    EditText writtenText;
    String listName;

    public void showDialog(final Activity activity, final String page_action, String page_description, String list_name) {
        listName = list_name;
        final Dialog dialog = new Dialog(activity);
        db = new DatabaseHelper(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.creation_dialog);

        final TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView description = (TextView) dialog.findViewById(R.id.description);

        title.setText(page_action);
        description.setText(page_description);

        writtenText = (EditText) dialog.findViewById(R.id.edt_action_track_name);
//        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button createView = (Button) dialog.findViewById(R.id.btn_create);
        createView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page_action.contentEquals("Create List")) {
                    String listname = writtenText.getText().toString();
                    createList(listname);
                    dialog.dismiss();
                } else if (page_action.contentEquals("Create Activity")) {
                    String activityname = writtenText.getText().toString();
                    createActivity(activityname);

                    dialog.dismiss();
                }


//                Toast.makeText(activity, "this is the " + listname, Toast.LENGTH_LONG).show();

            }
        });
        dialog.show();

    }

    public void createList(String writtenText) {

        MeasureList measureList = new MeasureList(writtenText);
        db.createList(measureList);
        db.closeDB();


        //Posting event
        AppMainServiceEvent event = new AppMainServiceEvent();
        event.setEventType(AppMainServiceEvent.DATA_ON_CHANGED);
        EventBus.getDefault().post(event);


    }


    public void createActivity(String activity_name) {

//                        int percCompletion = Integer.parseInt(percentageCompletion.getText().toString());


        MeasureActivity measureList = new MeasureActivity(activity_name, listName);
        db.createMeasureActivity(measureList, listName);
        db.closeDB();

        //Posting event
        AppMainServiceEvent event = new AppMainServiceEvent();
        event.setEventType(AppMainServiceEvent.DATA_ON_CHANGED);
        EventBus.getDefault().post(event);


    }


}