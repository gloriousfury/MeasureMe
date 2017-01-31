package com.taiyeoloriade.measureme.ui.activity;

/**
 * Created by OLORIAKE KEHINDE on 11/16/2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.utility.DatabaseHelper;


public class TodoActivity extends AppCompatActivity implements View.OnClickListener {


    DatabaseHelper db;
    Button createTag, createToDo;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);


        createTag = (Button)findViewById(R.id.btnCreateTag);
        createToDo = (Button)findViewById(R.id.btnCreateTodo);
        createTag.setOnClickListener(this);
        createToDo.setOnClickListener(this);
        db = new DatabaseHelper(this);



        recycler = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);






    }


    public void CreateTag(Button button) {



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnCreateTag:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Create a Tag");


                final EditText tag_name = new EditText(this);


//quantity.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
//lot.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);


                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);


                ll.addView(tag_name);
                alertDialog.setView(ll);

                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        String tagname_string = tag_name.getText().toString();
//                        Tag tag = new Tag(tag_name.getText().toString());
//                        db.createTag(tag);


                        Toast test = Toast.makeText(TodoActivity.this, "has been added to your tags", Toast.LENGTH_LONG);
                        test.show();



//ACTION
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });


                alertDialog.show();

                break;

            case R.id.btnCreateTodo:


//                Intent TodoClass = new Intent(this, SingleTodoActivity.class);
//                startActivity(TodoClass);

                break;




        }

    }

    ;


}

