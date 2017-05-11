package com.taiyeoloriade.measureme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rey.material.widget.Slider;
import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.model.DateDBModel;
import com.taiyeoloriade.measureme.model.MeasureActivity;
import com.taiyeoloriade.measureme.utility.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

import static java.lang.Boolean.TRUE;

public class StatActivity extends AppCompatActivity implements View.OnClickListener {


    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseHelper db;
    EditText edt_description;
    ImageView back;
    static Slider slider;
    String KEY_DESCRIPTION = "description";
    String KEY_PERCENTAGE = "percentage";
    String KEY_ID = "id";
    int activity_id;
    String description;
    String KEY_ACTIVITY_ID = "activity_id";
    ColumnChartView columnChartView;

    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    int listId;
    String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liststat);

        columnChartView = (ColumnChartView) findViewById(R.id.chart);

        db = new DatabaseHelper(this);

        Intent getData =  getIntent();
      activity_id = getData.getIntExtra(KEY_ACTIVITY_ID, 0);

        db.getAListwithId(listId);

//        Toast.makeText(this, "" +listId , Toast.LENGTH_LONG).show();

        generateDefaultData();



    }







    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }




    private void generateDefaultData() {

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.


        List<DateDBModel> list1 = db.getAnActivityWithID(activity_id);
        int numSubcolumns = 1;
        int numColumns = list1.size();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; i < numColumns; i++) {

            int score = list1.get(i).getPercentage_score();
            String date = list1.get(i).getDate();
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) score, ChartUtils.pickColor()).setLabel(date));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Day");
                axisY.setName("Progress score");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        columnChartView.setColumnChartData(data);

    }

    private void generateDefaultData1() {

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.


        List<DateDBModel> list1 = db.getAListwithId(listId);
        int numSubcolumns = 1;
        int numColumns = list1.size();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; i < numColumns; i++) {

            int score = list1.get(i).getAverage_score();
            String date = list1.get(i).getDate();
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) score, ChartUtils.pickColor()).setLabel(date));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Day");
                axisY.setName("Progress score");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        columnChartView.setColumnChartData(data);

    }

    @Override
    public void onClick(View view) {

    }

//



}
