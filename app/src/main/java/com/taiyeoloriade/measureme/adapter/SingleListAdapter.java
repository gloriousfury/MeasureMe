package com.taiyeoloriade.measureme.adapter;

/**
 * Created by OLORIAKE KEHINDE on 11/16/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.model.DateDBModel;
import com.taiyeoloriade.measureme.model.MeasureActivity;
import com.taiyeoloriade.measureme.model.MeasureList;
import com.taiyeoloriade.measureme.ui.activity.RecyclerViewClickListener;
import com.taiyeoloriade.measureme.ui.activity.SingleActivity;
import com.taiyeoloriade.measureme.ui.activity.SingleActivityList;
import com.taiyeoloriade.measureme.utility.DatabaseHelper;
import com.taiyeoloriade.measureme.utility.SessionManager;

import java.util.List;
import java.util.Random;

import static android.R.attr.id;
import static android.media.CamcorderProfile.get;
import static com.taiyeoloriade.measureme.R.id.list_name;
import static com.taiyeoloriade.measureme.R.id.overallPercentage;
import static com.taiyeoloriade.measureme.R.id.scoreview;
import static com.taiyeoloriade.measureme.R.id.slider;


public class SingleListAdapter extends RecyclerView.Adapter<SingleListAdapter.ViewHolder> {
    Context context;
    private List<MeasureActivity> measureactivitylist;

    String KEY_DESCRIPTION = "description";
    String KEY_PERCENTAGE = "percentage";
    String KEY_ID = "id";


    public SingleListAdapter(Context context, List<MeasureActivity> measureactivitylist) {
        this.context = context;
        this.measureactivitylist = measureactivitylist;


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView desc, percCompletion, percBaseline, reminder, status, createdAt;
        CardView background;
        ImageView delete, scoreviewImg;
        DatabaseHelper db;
        List<MeasureActivity> lists;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);
//            title = (TextView) view.findViewById(menu_item);
            db = new DatabaseHelper(context);
            desc = (TextView) view.findViewById(R.id.description);
            background = (CardView) view.findViewById(R.id.activities_cardLayout);
            delete = (ImageView) view.findViewById(R.id.deleteActivity);
            scoreviewImg = (ImageView) view.findViewById(R.id.scoreview);
            delete.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.deleteActivity:

                    final int Id = measureactivitylist.get(getAdapterPosition()).getId();

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("DELETE");
                    alertDialog.setMessage("Are you sure you want to delete this activity, tis irreversible");

                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            db.deleteActivity(Id);
                            measureactivitylist.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            db.closeDB();
//
////ACTION
                        }
                    });

                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });


                    alertDialog.show();


                    Log.d("Taiye", "Fab 1");


                    break;

                default:

                    String desc = measureactivitylist.get(getAdapterPosition()).getDescription().toString();
                    String perc = "" + measureactivitylist.get(getAdapterPosition()).getPercentage();
                    String id = "" + measureactivitylist.get(getAdapterPosition()).getId();
////


                    Intent singleActivity = new Intent(context, SingleActivity.class);
                    singleActivity.putExtra(KEY_DESCRIPTION, measureactivitylist.get(getAdapterPosition()).getDescription());
                    singleActivity.putExtra(KEY_PERCENTAGE, measureactivitylist.get(getAdapterPosition()).getPercentage());
                    singleActivity.putExtra(KEY_ID, measureactivitylist.get(getAdapterPosition()).getId());
                    context.startActivity(singleActivity);

            }


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_card3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.title.setText(title_list.get(position).getTitle());
        final int Id = measureactivitylist.get(position).getId();


        holder.desc.setText(measureactivitylist.get(position).getDescription());
        final int Menucolor[] = {

//                R.color.first_color,
//                R.color.second_color,
//                R.color.third_color,
//                R.color.fourth_color,
                R.color.fifth_color,
                R.color.sixth_color,
//                R.color.seventh_color


        };

        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(Menucolor.length);

        ChangeScoreView(holder, position);
        holder.background.setCardBackgroundColor(ContextCompat.getColor(context, Menucolor[index]));


    }

    @Override
    public int getItemCount() {
        return measureactivitylist.size();
    }

    public void ChangeScoreView(ViewHolder viewHolder, int position) {


        DatabaseHelper db = new DatabaseHelper(context);

        List<DateDBModel> lists = db.getAnActivityWithID(measureactivitylist.get(position).getId());



        double single_score = 0;
        double score = 0;


        for (int i = 0; i < lists.size(); i++) {

            single_score += lists.get(i).getPercentage_score();
        }

//        Toast.makeText(this, " " + score, Toast.LENGTH_LONG).show();
        int scoreBaseline = 5 * lists.size();
        score =  ( single_score / (double) scoreBaseline)*100;


//        Toast.makeText(context,String.valueOf(score) +" List Size = " + String.valueOf(lists.size()),Toast.LENGTH_LONG).show();

        if (score <= 10) {

            viewHolder.scoreviewImg.setBackground(ContextCompat.getDrawable(context, R.drawable.scoreview1));


        } else if (score > 10 && score <= 40) {

            viewHolder.scoreviewImg.setBackground(ContextCompat.getDrawable(context, R.drawable.scoreview2));

        } else if (score > 10 && score <= 55) {


            viewHolder.scoreviewImg.setBackground(ContextCompat.getDrawable(context, R.drawable.scoreview3));

        } else if (score > 55 && score <= 69) {

            viewHolder.scoreviewImg.setBackground(ContextCompat.getDrawable(context, R.drawable.scoreview4));


        } else if (score > 70) {

            viewHolder.scoreviewImg.setBackground(ContextCompat.getDrawable(context, R.drawable.scoreview5));


        }


    }


}

