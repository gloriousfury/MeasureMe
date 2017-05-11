package com.taiyeoloriade.measureme.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.adapter.SingleListAdapter;
import com.taiyeoloriade.measureme.model.DateDBModel;
import com.taiyeoloriade.measureme.model.MeasureActivity;
import com.taiyeoloriade.measureme.utility.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.media.CamcorderProfile.get;

public class SingleActivityList extends AppCompatActivity implements View.OnClickListener,RecyclerViewClickListener {


    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseHelper db;
    List<MeasureActivity> lists;
    String KEY_LISTNAME = "listname";
    String KEY_LIST_ID = "list_id";
    String listname;
    int listid;
    int score;
    int scoreBaseline;
    TextView overallPercentage, viewStats;
    int PercentageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlelist);

        db = new DatabaseHelper(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        overallPercentage = (TextView) findViewById(R.id.overallPercentage);
        viewStats = (TextView) findViewById(R.id.view_stats);
        overallPercentage.setOnClickListener(this);
        viewStats.setOnClickListener(this);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Intent listdata = getIntent();
        listname = listdata.getStringExtra(KEY_LISTNAME);
        listid = listdata.getIntExtra(KEY_LIST_ID, 0);
        getSupportActionBar().setTitle(listname + " List");
        calculateOverallPercentage();
        checkDatePosted();
        SingleListAdapter adapter = new SingleListAdapter(this, lists, this);
        recyclerView.setAdapter(adapter);


    }

    private void calculateOverallPercentage() {
        lists = db.getAllToDosByListName2(listname);

        score = 0;


        for (int i = 0; i < lists.size(); i++) {

            score += lists.get(i).getPercentage();
        }

//        Toast.makeText(this, " " + score, Toast.LENGTH_LONG).show();
        scoreBaseline = 50 * lists.size();
        PercentageResult = (int) (((double) score / (double) scoreBaseline) * 100);

        overallPercentage.setText("  " + PercentageResult + "%");

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {


            case R.id.fab:

                createListDialog();

                break;

            case R.id.view_stats:

                Intent statActivity = new Intent(this, StatActivity.class);
                statActivity.putExtra(KEY_LIST_ID, listid);
                statActivity.putExtra(KEY_LISTNAME,listname);
                startActivity(statActivity);

                break;


        }


    }

    public void createListDialog() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//                alertDialog.setTitle("CREATE");
        alertDialog.setMessage("Write down an essential activity you want to track");


        final EditText description = new EditText(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);


        ll.addView(description);
//                ll.addView(percentageCompletion);


        alertDialog.setView(ll);


        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("CREATE ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String desc = description.getText().toString();
//                        int percCompletion = Integer.parseInt(percentageCompletion.getText().toString());


                MeasureActivity measureList = new MeasureActivity(desc, listname);
                db.createMeasureActivity(measureList, listname);
                db.closeDB();

                AdapterChanged();


//ACTION
            }
        });

        alertDialog.setNegativeButton("NAH", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });


        alertDialog.show();


        Log.d("Taiye", "Fab 1");


    }



    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void checkDatePosted() {

        List<DateDBModel> list1 = db.getAListwithIdAndDates(listid, getDateTime());
//        Toast.makeText(this, "It created", Toast.LENGTH_LONG).show();

        if (list1.size() == 0) {

            DateDBModel datedbModel = new DateDBModel();
            datedbModel.setDate(getDateTime());
            datedbModel.setAverage_score(PercentageResult);
            datedbModel.setList_id(listid);
            db.createListDate(this,datedbModel);


        } else {
            DateDBModel datedbModel = new DateDBModel();
            datedbModel.setDate(getDateTime());
            datedbModel.setAverage_score(PercentageResult);
            datedbModel.setList_id(listid);
//            db.updateListDateId(listname);
            db.updateActivityList(this, datedbModel);

//            Toast.makeText(this, "It Updates", Toast.LENGTH_LONG).show();


        }


    }

    private void AdapterChanged() {

        List<MeasureActivity> list1 = db.getAllToDosByListName2(listname);

        SingleListAdapter adapter = new SingleListAdapter(this, list1, this);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
// TODO Auto-generated method stub
        super.onRestart();
        calculateOverallPercentage();
        AdapterChanged();

        //Do your code here
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
//        Movie movie = mMovieList.get(clickedItemIndex);
//        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//        intent.putExtra("movieItem", movie);
//        startActivity(intent);

        Toast.makeText(this, "I was clicked man, what a great stuff", Toast.LENGTH_SHORT).show();

    }


}
