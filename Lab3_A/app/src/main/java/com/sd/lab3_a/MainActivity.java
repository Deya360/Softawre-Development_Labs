package com.sd.lab3_a;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import io.bloco.faker.Faker;

public class MainActivity extends AppCompatActivity {
    private static final int DEFAULT_ROW_COUNT = 5;
    private static boolean isUpgraded = false;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        execDBInitCommands();

        // UI Button Listeners
        Button viewBtn = findViewById(R.id.view_button);
        viewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TableActivity.class);
            startActivity(intent);
            }
        });

        Button insertBtn = findViewById(R.id.insert_button);
        insertBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.addData(generateRandStudent());

                // Extra Unnecessary Beautification
                String msg = "Record Added Successfully";
                Cursor cursor = mDatabaseHelper.getLastID();
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);
                    msg+= " @:" + id;
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button updateBtn = findViewById(R.id.update_button);
        updateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.updateLastAddedName("Ivanov Ivan Ivanovich");

                // Extra Unnecessary Beautification
                String msg = "Record Updated Successfully";
                Cursor cursor = mDatabaseHelper.getLastID();
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);
                    msg+= " @:" + id;
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void execDBInitCommands() {
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.deleteAllData();

        for (int i = 0; i < DEFAULT_ROW_COUNT; i++) {
            mDatabaseHelper.addData(generateRandStudent());
        }
    }

    Student generateRandStudent() {
        Faker rng = new Faker();
        String[] fullName = {rng.name.firstName(), rng.name.firstName(), rng.name.lastName()};
        return new Student(TextUtils.join(" ", fullName), System.currentTimeMillis());
    }
}
