package com.taiyeoloriade.measureme.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.adapter.MeasureListAdapter;
import com.taiyeoloriade.measureme.adapter.SingleListAdapter;
import com.taiyeoloriade.measureme.model.MeasureActivity;
import com.taiyeoloriade.measureme.model.MeasureList;

import com.taiyeoloriade.measureme.utility.DatabaseHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseHelper db;
    List<MeasureList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        scheduleAlarm();

//            MeasureList first = new MeasureList();
//            first.setList_name("Daily");
//            db.createList(first);


//        MeasureActivity measureList = new MeasureActivity("first one",10);
//        db.createMeasureActivity(measureList,new long[]{0});
//
//        MeasureActivity measureList2 = new MeasureActivity("second one",10);
//        db.createMeasureActivity(measureList2,new long[]{0});
//
//        MeasureActivity measureList3 = new MeasureActivity("thired one",10);
//        db.createMeasureActivity(measureList3,new long[]{0});


        AdapterChanged();


        lists = db.getAllLists();

        List<MeasureList> allToDos = db.getAllLists();
        for (MeasureList todo : allToDos) {
            Log.d("ToDo", todo.getList_name());
        }

        MeasureListAdapter adapter = new MeasureListAdapter(this, lists);
        recyclerView.setAdapter(adapter);


    }

    private void scheduleAlarm() {


//        Date dat  = new Date();//initializes to now
//        Calendar cal_alarm = Calendar.getInstance();
//        Calendar cal_now = Calendar.getInstance();
//        cal_now.setTime(dat);
//        cal_alarm.setTime(dat);
//        cal_alarm.set(Calendar.HOUR_OF_DAY,10);//set the alarm time
//        cal_alarm.set(Calendar.MINUTE, 0);
//        cal_alarm.set(Calendar.SECOND,0);
////        if(cal_alarm.before(cal_now)){//if its in the past increment
////            cal_alarm.add(Calendar.DATE,1);
////        }
//        //SET YOUR AlarmManager here
//
//
//        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC, cal_alarm.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
//        //set the alarm for particular time

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 13);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            int day = 86400000;

            Intent intent1 = new Intent(MainActivity.this, AlarmReciever1.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + day, AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            Intent intent1 = new Intent(MainActivity.this, AlarmReciever1.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.retryButton:
//
//                loadUIData();
//                break;


            case R.id.fab:
//                Intent Message = new Intent(this, PostMessageActivity.class);
//                startActivity(Message);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Create a  List (e.g Daily)");


                final EditText list_name = new EditText(this);


//quantity.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
//lot.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);


                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);


                ll.addView(list_name);
                alertDialog.setView(ll);

                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Yeah!!!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String listname = list_name.getText().toString();
                        MeasureList measureList = new MeasureList(list_name.getText().toString());
                        db.createList(measureList);
                        db.closeDB();
                        AdapterChanged();


//ACTION
                    }
                });

                alertDialog.setNegativeButton("Later man", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });


                alertDialog.show();


                Log.d("Taiye", "Fab 1");
                break;


        }


    }

    private void AdapterChanged() {

        lists = db.getAllLists();

        List<MeasureList> allToDos = db.getAllLists();
        for (MeasureList todo : allToDos) {
            Log.d("ToDo", todo.getList_name());
        }

        MeasureListAdapter adapter = new MeasureListAdapter(this, lists);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


}
