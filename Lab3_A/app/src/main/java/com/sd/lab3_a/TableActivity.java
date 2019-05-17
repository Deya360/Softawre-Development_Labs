package com.sd.lab3_a;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private ArrayList<Student> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //Init Database:
        mDatabaseHelper = new DatabaseHelper(this);
        populateArrayList();

        //Init UI Elements:
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TableAdapter(array));

        if (array.size()==0) {
            Toast.makeText(this, "No Items to Show!", Toast.LENGTH_SHORT).show();
        }
    }

    void populateArrayList() {
        Cursor data = mDatabaseHelper.getData();

        array = new ArrayList<>();
        while (data.moveToNext()) {
            Student s = new Student(data.getInt(0),
                        data.getString(1),
                        data.getLong(2));
            array.add(s);
        }
    }
}
