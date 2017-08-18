package com.taiyeoloriade.measureme.views;

import android.content.Context;
import android.util.AttributeSet;

//TODO: This class should be used in the insect list to display danger level
public class DangerLevelView extends android.support.v7.widget.AppCompatTextView {
    Context context;
    AttributeSet attrs;
    int defStyleAttry;
    int dangerLevel;


    public DangerLevelView(Context context) {
        super(context);
        this.context = context;
    }

    public DangerLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
    }

    public DangerLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.defStyleAttry = defStyleAttr;
    }

//    public DangerLevelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public void setDangerLevel(int dangerLevel) {
        //TODO: Update the view appropriately based on the level input
        this.dangerLevel = dangerLevel;
    }

    public int getDangerLevel() {
        //TODO: Report the current level back as an integer
        return dangerLevel;
    }
}
