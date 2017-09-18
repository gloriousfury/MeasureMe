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

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
    BarChart bar_chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liststat);

        bar_chart = (BarChart) findViewById(R.id.bar_chart);


        db = new DatabaseHelper(this);

        Intent getData = getIntent();
        activity_id = getData.getIntExtra(KEY_ACTIVITY_ID, 0);

//        db.getAListwithId(listId);

//        Toast.makeText(this, "" +listId , Toast.LENGTH_LONG).show();

        generateDefaultData();


    }


    private void generateDefaultData() {

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.

//
        List<DateDBModel> list1 = db.getAnActivityWithID(activity_id);
        int numColumns = list1.size();
//        Toast.makeText(StatActivity.this, String.valueOf(numColumns), Toast.LENGTH_LONG).show();
//
        ArrayList<BarEntry> entries = new ArrayList<>();
        final String[] labels = new String[numColumns];

        if (numColumns > 1) {
            for (int i = 0; i <= numColumns - 1; i++) {

                double score = list1.get(i).getPercentage_score();
//            if (score > 10) {
                String date = list1.get(i).getDate();
                Toast.makeText(StatActivity.this, date, Toast.LENGTH_LONG).show();
                entries.add(new BarEntry(i + 1, (float) score));
//            labels[i] = date;


            }

        } else {
            double score = list1.get(0).getPercentage_score();
//            if (score > 10) {
            String date = list1.get(0).getDate();
            Toast.makeText(StatActivity.this, date, Toast.LENGTH_LONG).show();
            entries.add(new BarEntry(0 + 1, (float) score));
            labels[0] = date;
        }
//        Toast.makeText(StatActivity.this, labels[1], Toast.LENGTH_LONG).show();
        BarDataSet dataset = new BarDataSet(entries, "# performance scores");

        BarData data = new BarData(dataset);
        bar_chart.setData(data);
        bar_chart.setScaleMinima(numColumns + 3, 5);
        XAxis xAxis = bar_chart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new MyXAxisValueFormatter(labels));


//
//        xAxis.setValueFormatter(new IAxisValueFormatter()
//        {
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis)
//            {
//                System.out.println(value);
//                if(((int)value)<labels.length)
//                {
//                    return  (labels[(int)value]);
//                }
//                else
//                {
//                    return "";
//                }
//            }
//
//
//        });
//
//
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                int valueInt = (int) value;
//                String dayNo ;
//
//
//
//                    dayNo = "Day:" +String.valueOf(valueInt+1);
//
//
//
//                return dayNo;
//            }
//        });


    }


    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            if (mValues.length > 1) {

                return mValues[(int) value - 1];
            } else {

                return mValues[0];
            }


        }

        /**
         * this is only needed if numbers are returned, else return 0
         */

        public int getDecimalDigits() {
            return 0;
        }


    }


    private void generateDefaultData1() {

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.


        List<DateDBModel> list1 = db.getAListwithId(listId);
        int numSubcolumns = 1;
        int numColumns = list1.size();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; i < numColumns; i++) {

            double score = list1.get(i).getAverage_score();
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
