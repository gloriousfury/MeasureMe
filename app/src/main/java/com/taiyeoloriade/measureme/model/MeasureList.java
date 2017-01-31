package com.taiyeoloriade.measureme.model;

/**
 * Created by OLORIAKE KEHINDE on 1/14/2017.
 */

public class MeasureList {

    int id;
    String list_name;

    // constructors
    public MeasureList() {

    }

    public MeasureList(String list_name) {
        this.list_name = list_name;
    }

    public MeasureList(int id, String list_name) {
        this.id = id;
        this.list_name = list_name;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getList_name() {
        return this.list_name;
    }
}
    
