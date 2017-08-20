package com.taiyeoloriade.measureme.model;

/**
 * Created by OLORIAKE KEHINDE on 1/14/2017.
 */

public class MeasureActivity {


    int id;
    String description;
    double percentage;
    double percentageBaseline;
    String listname;
    String remindertime;
    int status;
    String created_at;


    // constructors
    public MeasureActivity() {
    }

    public MeasureActivity(String description, double percentage, double percentageBaseline, String listname, int status) {
        this.description = description;
        this.status = status;
        this.percentage = percentage;
        this.percentageBaseline = percentageBaseline;
        this.listname = listname;
    }

    public MeasureActivity(int id, String description, double percentage, double percentageBaseline) {
        this.description = description;
        this.id = id;

        this.percentageBaseline = percentageBaseline;
        this.percentage = percentage;
    }

    public MeasureActivity(String description, double percentage, String listname) {
        this.description = description;
        this.percentage = percentage;
        this.listname = listname;
    }

    public MeasureActivity(String description, String listname) {
        this.description = description;
        this.listname = listname;
    }


//    public MeasureActivity(int id, String description, int status) {
//        this.id = id;
//        this.percentageBaseline = percentage;
//        this.status = status;
//    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }


    public void setPercentageBaseline(double percentageBaseline) {
        this.percentageBaseline = percentageBaseline;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    // getters
    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPercentage() {
        return this.percentage;
    }

    public double getPercentageBaseline() {
        return this.percentageBaseline;
    }

    public String dgetListname() {
        return this.listname;
    }

    public int getStatus() {
        return this.status;
    }

}
