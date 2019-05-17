package com.sd.lab3_b;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private ArrayList<Student> array;
    private Boolean isUpgraded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        isUpgraded = (DatabaseHelper.getDbVersion()!=1);

        //Init Database:
        mDatabaseHelper = new DatabaseHelper(this);
        populateArrayList();

        //Init UI Elements:
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TableAdapter(array, isUpgraded));
    }

    void populateArrayList() {
        Cursor data = mDatabaseHelper.getData();

        array = new ArrayList<>();
        while (data.moveToNext()) {
            Student s;
            if (isUpgraded) {
                s = new Student(data.getInt(0),
                                data.getString(1),
                                data.getString(2),
                                data.getString(3),
                                data.getLong(4));
            } else {
                //Support
                s = new Student(data.getInt(0),
                        data.getString(1),
                        data.getLong(2));
            }
            array.add(s);
        }
    }
}
