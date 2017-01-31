package com.taiyeoloriade.measureme.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.taiyeoloriade.measureme.model.DateDBModel;
import com.taiyeoloriade.measureme.model.MeasureActivity;
import com.taiyeoloriade.measureme.model.MeasureList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.name;
import static android.R.attr.tag;
import static android.R.id.list;
import static android.provider.Contacts.SettingsColumns.KEY;
import static com.taiyeoloriade.measureme.R.id.list_name;

/**
 * Created by OLORIAKE KEHINDE on 1/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String LOG = DatabaseHelper.class.getName();
    // Database Name
    private static final String DATABASE_NAME = "MeasureMeUltimateManager";
    private static final String TABLE_MEASURELISTS = "lists";
    private static final String TABLE_MEASUREACTIVITIES = "activities";
    private static final String TABLE_LIST_ACTIVITY = "listandactivities";
    private static final String TABLE_DATE = "activitiesandlist";


    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_STATUS = "status";

    // Measure Activities column names
    private static final String KEY_DESC = "description";
    private static final String KEY_PERCENTAGE_PROGRESS = "percentage_progress";
    private static final String KEY_PERCENTAGE_BASELINE = "percentage_baseline";
    private static final String KEY_REMINDER_TIME = "reminder_time";


    private static final String KEY_LIST_NAME = "list_name";

    private static final String KEY_LIST_ID = "list_id";
    private static final String KEY_ACTIVITY_ID = "activity_id";
    private static final String KEY_AVERAGE_SCORE = "average_score";
    private static final String KEY_STORE_DATE = "store_date";
    private static final String KEY_LIST_DATE_ID = "list_date";

    Context context;


    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_MEASURELISTS = "CREATE TABLE " + TABLE_MEASURELISTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LIST_NAME + " TEXT," + KEY_PERCENTAGE_BASELINE + " INTEGER,"
            + KEY_LIST_DATE_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_MEASUREACTIVITIES = "CREATE TABLE "
            + TABLE_MEASUREACTIVITIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DESC
            + " TEXT," + KEY_LIST_NAME + " TEXT," + KEY_PERCENTAGE_PROGRESS + " INTEGER," + KEY_PERCENTAGE_BASELINE + " INTEGER," + KEY_REMINDER_TIME
            + " DATETIME," + KEY_CREATED_AT + " DATETIME" + ")";


    // todo_tag table create statement
//    private static final String CREATE_TABLE_LIST_ACTIVITY = "CREATE TABLE "
//            + TABLE_LIST_ACTIVITY + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_LIST_ID + " INTEGER," + KEY_ACTIVITY_ID + " INTEGER,"
//            + KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_DATE_LIST_ACTIVITY = "CREATE TABLE "
            + TABLE_DATE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_LIST_ID + " INTEGER," + KEY_ACTIVITY_ID + " INTEGER," + KEY_PERCENTAGE_PROGRESS + " INTEGER,"
            + KEY_AVERAGE_SCORE + " INTEGER,"
            + KEY_STORE_DATE + " DATETIME" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_MEASURELISTS);
        db.execSQL(CREATE_TABLE_MEASUREACTIVITIES);
        db.execSQL(CREATE_TABLE_DATE_LIST_ACTIVITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREACTIVITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASURELISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);
        // create new tables
        onCreate(db);


    }

    public long createList(MeasureList measureList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LIST_NAME, measureList.getList_name());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long list_id = db.insert(TABLE_MEASURELISTS, null, values);
        return list_id;
    }


    public long createActivityDate(Context context,DateDBModel dbmodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STORE_DATE, dbmodel.getDate());
        values.put(KEY_ACTIVITY_ID, dbmodel.getActivity_id());
//        values.put(KEY_LIST_ID, todo.getPercentage());
        values.put(KEY_AVERAGE_SCORE, dbmodel.getAverage_score());
        values.put(KEY_PERCENTAGE_PROGRESS, dbmodel.getPercentage_score());


        // insert row
        long activityDate_id = db.insert(TABLE_DATE, null, values);


        // insert tag_ids
//        for (long list_id : list_ids) {
//            createListActivity(activity_id, list_id);
//        }

        return activityDate_id;
    }

    public long createListDate(Context context,DateDBModel dbmodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STORE_DATE, dbmodel.getDate());
//        values.put(KEY_ACTIVITY_ID, dbmodel.getActivity_id());
        values.put(KEY_LIST_ID, dbmodel.getList_id());
        values.put(KEY_AVERAGE_SCORE, dbmodel.getAverage_score());
//        values.put(KEY_PERCENTAGE_PROGRESS, dbmodel.getPercentage_score());
        // insert row
        long listDate_id = db.insert(TABLE_DATE, null, values);
        // insert tag_ids
//        for (long list_id : list_ids) {
//            createListActivity(activity_id, list_id);
//        }

//        Toast.makeText(context, "It's coming here", Toast.LENGTH_LONG).show();
        return listDate_id;
    }


    /**
     * getting all tags
     */
    public List<MeasureList> getAllLists() {
        List<MeasureList> measureLists = new ArrayList<MeasureList>();
        String selectQuery = "SELECT  * FROM " + TABLE_MEASURELISTS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MeasureList t = new MeasureList();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setList_name(c.getString(c.getColumnIndex(KEY_LIST_NAME)));

                // adding to tags list
                measureLists.add(t);
            } while (c.moveToNext());
        }
        return measureLists;
    }


    /**
     * Creating a todo
     */
    public long createMeasureActivity(MeasureActivity todo, String listname) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DESC, todo.getDescription());
        values.put(KEY_PERCENTAGE_PROGRESS, todo.getPercentage());
        values.put(KEY_LIST_NAME, listname);
        values.put(KEY_PERCENTAGE_BASELINE, 50);
//        values.put(KEY_REMINDER_TIME, todo.getDescription());
//        values.put(KEY_STATUS, todo.getStatus());
//        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long activity_id = db.insert(TABLE_MEASUREACTIVITIES, null, values);

        // insert tag_ids
//        for (long list_id : list_ids) {
//            createListActivity(activity_id, list_id);
//        }

        return activity_id;
    }

    /*
 * getting all todos under single tag
 * */
    public List<MeasureActivity> getAllToDosByListName(String list_name) {
        List<MeasureActivity> activity_list = new ArrayList<MeasureActivity>();

        String selectQuery = "SELECT  * FROM " + TABLE_MEASUREACTIVITIES + " td, "
                + TABLE_MEASURELISTS + " tg, " + TABLE_LIST_ACTIVITY + " tt WHERE tg."
                + KEY_LIST_NAME + " = '" + list_name + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_LIST_ID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_ACTIVITY_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MeasureActivity td = new MeasureActivity();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setDescription((c.getString(c.getColumnIndex(KEY_DESC))));
                td.setPercentage((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_PROGRESS))));

//                td.setPercentageBaseline((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_BASELINE))));
//                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                activity_list.add(td);
            } while (c.moveToNext());
        }

        return activity_list;
    }


    public List<MeasureActivity> getAllToDosByListName2(String listname) {
        List<MeasureActivity> activity_list = new ArrayList<MeasureActivity>();

//        String selectQuery = "SELECT  * FROM " + TABLE_MEASUREACTIVITIES + " WHERE "
//                + KEY_LIST_NAME + " = " + listname ;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM  activities   where list_name='" + listname + "'", null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MeasureActivity td = new MeasureActivity();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setDescription((c.getString(c.getColumnIndex(KEY_DESC))));
                td.setPercentage((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_PROGRESS))));
                td.setListname((c.getString(c.getColumnIndex(KEY_LIST_NAME))));
//                td.setPercentageBaseline((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_BASELINE))));
//                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                activity_list.add(td);
            } while (c.moveToNext());
        }

        return activity_list;
    }


    public List<DateDBModel> getAnActivityWithDates(int measureId) {
        List<DateDBModel> stored_activities = new ArrayList<DateDBModel>();

//        String selectQuery = "SELECT  * FROM " + TABLE_MEASUREACTIVITIES + " WHERE "
//                + KEY_LIST_NAME + " = " + listname ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DATE + " WHERE " + KEY_ACTIVITY_ID + " ='" + measureId + "'", null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DateDBModel td = new DateDBModel();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setActivity_id((c.getString(c.getColumnIndex(KEY_DESC))));
                td.setAverage_score((c.getInt(c.getColumnIndex(KEY_AVERAGE_SCORE))));
                td.setPercentage_score((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_PROGRESS))));
//                td.setPercentageBaseline((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_BASELINE))));
                td.setDate(c.getString(c.getColumnIndex(KEY_STORE_DATE)));

                // adding to todo list
                stored_activities.add(td);
            } while (c.moveToNext());
        }

        return stored_activities;
    }




    public List<DateDBModel> getAnActivityWithIDandDates(int measureId, String Date) {
        List<DateDBModel> stored_activities = new ArrayList<DateDBModel>();

//        String selectQuery = "SELECT  * FROM " + TABLE_MEASUREACTIVITIES + " WHERE "
//                + KEY_LIST_NAME + " = " + listname ;

//        String date = getDateTime();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM activitiesandlist WHERE activity_id = " + measureId +
                " AND store_date ='"+ Date + "'" ,null);

//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DATE + " WHERE " + KEY_ACTIVITY_ID + " ='" + measureId + "'"
//                + " AND " + KEY_STORE_DATE + " ='" + Date + "'", null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DateDBModel td = new DateDBModel();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setActivity_id((c.getString(c.getColumnIndex(KEY_DESC))));
                td.setAverage_score((c.getInt(c.getColumnIndex(KEY_AVERAGE_SCORE))));
                td.setPercentage_score((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_PROGRESS))));
//                td.setPercentageBaseline((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_BASELINE))));
                td.setDate(c.getString(c.getColumnIndex(KEY_STORE_DATE)));

                // adding to todo list
                stored_activities.add(td);
            } while (c.moveToNext());
        }

        return stored_activities;
    }



    public List<DateDBModel> getAnActivityWithID(int measureId) {
        List<DateDBModel> stored_activities = new ArrayList<DateDBModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM activitiesandlist WHERE activity_id = " + measureId +" " ,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DateDBModel td = new DateDBModel();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setActivity_id((c.getString(c.getColumnIndex(KEY_DESC))));
//                td.setAverage_score((c.getInt(c.getColumnIndex(KEY_AVERAGE_SCORE))));
                td.setPercentage_score((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_PROGRESS))));
//                td.setPercentageBaseline((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_BASELINE))));
                td.setDate(c.getString(c.getColumnIndex(KEY_STORE_DATE)));

                // adding to todo list
                stored_activities.add(td);
            } while (c.moveToNext());
        }

        return stored_activities;
    }


    public List<DateDBModel> getAListwithId(int listId) {
        List<DateDBModel> stored_activities = new ArrayList<DateDBModel>();

//        String selectQuery = "SELECT  * FROM " + TABLE_MEASUREACTIVITIES + " WHERE "
//                + KEY_LIST_NAME + " = " + listname ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_DATE + " WHERE " + KEY_LIST_ID + " ='" + listId + "'", null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DateDBModel td = new DateDBModel();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setActivity_id((c.getString(c.getColumnIndex(KEY_DESC))));
                td.setAverage_score((c.getInt(c.getColumnIndex(KEY_AVERAGE_SCORE))));
                td.setPercentage_score((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_PROGRESS))));
//                td.setPercentageBaseline((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_BASELINE))));
                td.setDate(c.getString(c.getColumnIndex(KEY_STORE_DATE)));

                // adding to todo list
                stored_activities.add(td);
            } while (c.moveToNext());
        }

        return stored_activities;
    }

    public List<DateDBModel> getAListwithIdAndDates(int listId, String dates) {
        List<DateDBModel> stored_activities = new ArrayList<DateDBModel>();

//        String selectQuery = "SELECT  * FROM " + TABLE_MEASUREACTIVITIES + " WHERE "
//                + KEY_LIST_NAME + " = " + listname ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM activitiesandlist WHERE list_id = " + listId +
                " AND store_date ='"+ dates + "'" ,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DateDBModel td = new DateDBModel();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setActivity_id((c.getString(c.getColumnIndex(KEY_DESC))));
//                td.setList_id((c.getInt(c.getColumnIndex(KEY_LIST_ID))));
                td.setAverage_score((c.getInt(c.getColumnIndex(KEY_AVERAGE_SCORE))));
//                td.setPercentage_score((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_PROGRESS))));
//                td.setPercentageBaseline((c.getInt(c.getColumnIndex(KEY_PERCENTAGE_BASELINE))));
                td.setDate(c.getString(c.getColumnIndex(KEY_STORE_DATE)));

                // adding to todo list
                stored_activities.add(td);
            } while (c.moveToNext());
        }

        return stored_activities;
    }




    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /*
     * Updating a todo
     */
    public void updateActivity(MeasureActivity measureActivity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DESC, measureActivity.getDescription());
        values.put(KEY_PERCENTAGE_PROGRESS, measureActivity.getPercentage());
        values.put(KEY_PERCENTAGE_BASELINE, measureActivity.getPercentageBaseline());
//        values.put(KEY_LIST_NAME,"Daily");
//        values.put(KEY_CREATED_AT, "");
//        values.put(KEY);

        // updating row
        db.update(TABLE_MEASUREACTIVITIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(measureActivity.getId())});


    }

    public void updateListDateId(String listname, int list_date_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LIST_DATE_ID, list_date_id);
        // updating row
        db.update(TABLE_MEASURELISTS, values, KEY_LIST_NAME + " = ?",
                new String[]{String.valueOf(listname)});


    }


    public void updateActivityDate(DateDBModel dbmodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_STORE_DATE, dbmodel.getDate());
//        values.put(KEY_ACTIVITY_ID, dbmodel.getActivity_id());
//        values.put(KEY_LIST_ID, todo.getPercentage());
        values.put(KEY_AVERAGE_SCORE, dbmodel.getAverage_score());
        values.put(KEY_PERCENTAGE_PROGRESS, dbmodel.getPercentage_score());


        // updating row
        db.update(TABLE_DATE, values, KEY_ACTIVITY_ID + " = ?",
                new String[]{String.valueOf(dbmodel.getActivity_id())});

    }

    public void updateActivityList(Context context,DateDBModel dbmodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_STORE_DATE, dbmodel.getDate());
//        values.put(KEY_ACTIVITY_ID, dbmodel.getActivity_id());
//        values.put(KEY_LIST_ID, todo.getPercentage());
        values.put(KEY_AVERAGE_SCORE, dbmodel.getAverage_score());
        values.put(KEY_PERCENTAGE_PROGRESS, dbmodel.getPercentage_score());


        // updating row
        db.update(TABLE_DATE, values, KEY_LIST_ID + " = ?",
                new String[]{String.valueOf(dbmodel.getList_id())});


//        Toast.makeText(context,"It updates", Toast.LENGTH_LONG).show();


    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /*
 * Deleting a todo
 */
    public void deleteActivity(int measureActivityId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEASUREACTIVITIES, KEY_ID + "=?",
                new String[]{String.valueOf(measureActivityId)});
    }

}
