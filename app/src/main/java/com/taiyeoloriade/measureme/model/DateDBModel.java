package com.taiyeoloriade.measureme.model;

import static com.taiyeoloriade.measureme.R.id.status;

/**
 * Created by OLORIAKE KEHINDE on 1/14/2017.
 */

public class DateDBModel {


    int id;
    int activity_id;
    int list_id;
    int average_score;
    int percentage_score;
    String date;
//    String remindertime;
//    int status;
//    String created_at;


    // constructors
    public DateDBModel() {
    }

    public DateDBModel(int activity_id, int percentage_score, int average_score, String date) {
//        this.id = id;
        this.activity_id = activity_id;
        this.average_score = average_score;
        this.date = date;
    }

    public DateDBModel(int list_id, int percentage_score, int average_score, String date, String notUsed) {
        //        this.id = id;
        this.list_id = list_id;
        this.average_score = average_score;
        this.percentage_score = percentage_score;
        this.date = date;
    }

//    public DateDBModel(String description, int percentage, String listname) {
//        this.description = description;
//        this.percentage = percentage;
//        this.listname = listname;
//    }


//    public MeasureActivity(int id, String description, int status) {
//        this.id = id;
//        this.percentageBaseline = percentage;
//        this.status = status;
//    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public void setPercentage_score(int percentage_score) {
        this.percentage_score = percentage_score;
    }

    public void setAverage_score(int average_score) {
        this.average_score = average_score;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public int getActivity_id() {
        return this.activity_id;
    }

    public int getList_id() {
        return this.list_id;
    }

    public int getPercentage_score() {
        return this.percentage_score;
    }

    public int getAverage_score() {
        return this.average_score;
    }

    public String getDate() {
        return this.date;
    }


}