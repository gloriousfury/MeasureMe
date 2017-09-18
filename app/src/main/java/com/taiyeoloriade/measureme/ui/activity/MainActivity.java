package com.taiyeoloriade.measureme.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.taiyeoloriade.measureme.AlarmService;
import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.adapter.MeasureListAdapter;
import com.taiyeoloriade.measureme.model.MeasureList;

import com.taiyeoloriade.measureme.utility.DatabaseHelper;
import com.taiyeoloriade.measureme.utils.AppMainServiceEvent;
import com.taiyeoloriade.measureme.utils.CommonUtil;
import com.taiyeoloriade.measureme.utils.ViewDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseHelper db;
    List<MeasureList> lists;
    String NOTIFICATION_ID = "notification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtil utils = new CommonUtil(this);
        if(utils.isFirstTimeLaunch()){
            utils.setFirstTimeLaunch(false);
            Intent viewPager = new Intent(this,ViewPagerActivity.class);
            startActivity(viewPager);
        }
        setContentView(R.layout.activity_main);



        db = new DatabaseHelper(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        scheduleAlarm();


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
        Intent i = new Intent(this, AlarmService.class);
// potentially add data to the intent
//        i.putExtra("KEY1", "Value to be used by the service");
        startService(i);


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

//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//                alertDialog.setMessage("Create a  List (e.g Daily)");
//
//
//                final EditText list_name = new EditText(this);


//quantity.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
//lot.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);


                ViewDialog alert = new ViewDialog();
                alert.showDialog(this, "Create List", "Write down the category of activities(e.g Daily, Monthly)",null);


//                LinearLayout ll = new LinearLayout(this);
//                ll.setOrientation(LinearLayout.VERTICAL);
//
//
//                ll.addView(list_name);
//                alertDialog.setView(ll);
//
//                alertDialog.setCancelable(false);
//                alertDialog.setPositiveButton("Yeah!!!", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        String listname = list_name.getText().toString();
//                        MeasureList measureList = new MeasureList(list_name.getText().toString());
//                        db.createList(measureList);
//                        db.closeDB();
//                        AdapterChanged();
//
//
////ACTION
//                    }
//                });
//
//                alertDialog.setNegativeButton("Later man", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.cancel();
//                    }
//                });
//
//
//                alertDialog.show();
//
//
//                Log.d("Taiye", "Fab 1");
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(AppMainServiceEvent event) {

//        Toast.makeText(this,"I came here",Toast.LENGTH_LONG).show();
        if (event.getEventType() == AppMainServiceEvent.DATA_ON_CHANGED) {

            AdapterChanged();
        }
//
//        textField.setText(event.message);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdapterChanged();
    }
}
