package com.taiyeoloriade.measureme.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Slider;
import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.adapter.SingleListAdapter;
import com.taiyeoloriade.measureme.model.DateDBModel;
import com.taiyeoloriade.measureme.model.MeasureActivity;
import com.taiyeoloriade.measureme.model.QuotesModel;
import com.taiyeoloriade.measureme.utility.DatabaseHelper;
import com.taiyeoloriade.measureme.utils.DataUtil;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

import static android.R.attr.data;
import static android.R.attr.lines;
import static android.R.attr.numColumns;
import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import static java.lang.Boolean.TRUE;

public class SingleActivity extends AppCompatActivity implements View.OnClickListener {


    FloatingActionButton fab;
    DatabaseHelper db;
    EditText edt_description;
    ImageView back;
    TextView quote_message, quote_author;
    static Slider slider;
    String KEY_DESCRIPTION = "description";
    String KEY_PERCENTAGE = "percentage";
    String KEY_ACTIVITY_ID = "activity_id";
    String KEY_ID = "id";
    int activity_id;
    ColumnChartView columnChartView;

    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_activity);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edt_description = (EditText) findViewById(R.id.edt_description);
        back = (ImageView) findViewById(R.id.back_arrow);
        quote_author = (TextView) findViewById(R.id.quote_author);
        quote_message = (TextView) findViewById(R.id.quote_message);
        slider = (Slider) findViewById(R.id.slider);
        fab = (FloatingActionButton) findViewById(R.id.fab);



        fab.setOnClickListener(this);
        back.setOnClickListener(this);

        db = new DatabaseHelper(this);

        Intent data = getIntent();
        edt_description.setText(data.getStringExtra(KEY_DESCRIPTION));
        int currentPercentage = data.getIntExtra(KEY_PERCENTAGE, 0);
        activity_id = data.getIntExtra(KEY_ID, 0);
        slider.setValue(currentPercentage, TRUE);


    setQuoteData();



    }

    private void setQuoteData() {
        DataUtil dataUtilInstance = new DataUtil(this);

        ArrayList<QuotesModel> quoteArray = null;
        try {
            quoteArray = dataUtilInstance.readQuotesFromResources();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Random r = new Random();
        int i = r.nextInt(quoteArray.size()-1);

        String message  = quoteArray.get(i).getQuoteText();
        String author = quoteArray.get(i).getQuoteAuthor();
        quote_message.setText(message);
        quote_author.setText(author);
    }


    public MeasureActivity updateData() {


        MeasureActivity measureActivity = new MeasureActivity();
        measureActivity.setId(activity_id);
        measureActivity.setDescription(edt_description.getText().toString());
        measureActivity.setPercentage(slider.getValue());
        measureActivity.setPercentageBaseline(50);

        return measureActivity;
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void checkDatePosted() {

        List<DateDBModel> list1 = db.getAnActivityWithIDandDates(activity_id, getDateTime());
//        Toast.makeText(this, "It created", Toast.LENGTH_LONG).show();

        if (list1.size() == 0) {

            DateDBModel datedbModel = new DateDBModel();
            datedbModel.setDate(getDateTime());
            datedbModel.setAverage_score(30);
            datedbModel.setPercentage_score(slider.getValue());
            datedbModel.setActivity_id(activity_id);

            db.createActivityDate(this, datedbModel);


        } else {
            DateDBModel datedbModel = new DateDBModel();
//            datedbModel.setDate(getDateTime());
            datedbModel.setAverage_score(30);
            datedbModel.setPercentage_score(slider.getValue());
//            datedbModel.setActivity_id(activity_id);

            db.updateActivityDate(datedbModel);

            Toast.makeText(this, "It Updates", Toast.LENGTH_LONG).show();


        }


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:


                checkDatePosted();
                db.updateActivity(updateData());

                db.closeDB();
//                db.deleteToDo(activity_id);
                onBackPressed();


                break;

            case R.id.fab:
                Intent openChart = new Intent(this,StatActivity.class);
                openChart.putExtra(KEY_ACTIVITY_ID,activity_id);
                startActivity(openChart);


                break;


        }


    }


}
