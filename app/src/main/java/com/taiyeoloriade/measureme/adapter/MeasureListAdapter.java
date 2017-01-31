package com.taiyeoloriade.measureme.adapter;

/**
 * Created by OLORIAKE KEHINDE on 11/16/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.model.MeasureList;
import com.taiyeoloriade.measureme.ui.activity.SingleActivityList;

import java.util.List;


public class MeasureListAdapter extends RecyclerView.Adapter<MeasureListAdapter.ViewHolder> {
    Context context;
    private List<MeasureList> todo;
    String KEY_LISTNAME= "listname";
    String KEY_LIST_ID= "list_id";

    public MeasureListAdapter(Context context, List<MeasureList> Todo) {
        this.context  = context;
        this.todo = Todo;


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listName;

        public ViewHolder(View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);
//            title = (TextView) view.findViewById(menu_item);

            listName = (TextView) view.findViewById(R.id.list_name);



        }

        @Override
        public void onClick(View v) {


            Intent listActivity = new Intent(context, SingleActivityList.class);
            listActivity.putExtra(KEY_LISTNAME,todo.get(getAdapterPosition()).getList_name());
            listActivity.putExtra(KEY_LIST_ID,todo.get(getAdapterPosition()).getId());
            context.startActivity(listActivity);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.title.setText(title_list.get(position).getTitle());

        holder.listName.setText(todo.get(position).getList_name());

//
//        if(todo.get(position).getStatus() == 1){
//            holder.status.setText("Done");
//
//        }else{
//            holder.status.setText("Not yet done");
//
//        }

    }

    @Override
    public int getItemCount() {
        return todo.size();
    }


    public void GetScoreView(){





    }

}

