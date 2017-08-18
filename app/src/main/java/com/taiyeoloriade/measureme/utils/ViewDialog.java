package com.taiyeoloriade.measureme.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.model.MeasureList;
import com.taiyeoloriade.measureme.utility.DatabaseHelper;

public class ViewDialog {


    DatabaseHelper db;
    public void showDialog(final Activity activity, String list_title){
        final Dialog dialog = new Dialog(activity);
        db = new DatabaseHelper(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.creation_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.title);
        TextView description = (TextView) dialog.findViewById(R.id.description);

        final EditText  writtenText = (EditText) dialog.findViewById(R.id.edt_action_track_name);
//        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView createView = (TextView) dialog.findViewById(R.id.txt_create);
        createView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listname = writtenText.getText().toString();
                MeasureList measureList = new MeasureList(listname);
                db.createList(measureList);
                db.closeDB();
                dialog.dismiss();
//                Toast.makeText(activity, "this is the " + listname, Toast.LENGTH_LONG).show();

            }
        });
        dialog.show();

    }
}