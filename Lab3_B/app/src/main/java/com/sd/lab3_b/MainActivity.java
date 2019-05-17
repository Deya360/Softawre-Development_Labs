package com.sd.lab3_b;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import io.bloco.faker.Faker;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    static final Uri CONTENT_URI = Uri.parse("content://com.sd.lab3_a.StudentProvider/cpstudents");

    private static final int DEFAULT_ROW_COUNT = 5;
    private static boolean isUpgraded = false;
    private DatabaseHelper mDatabaseHelper;

    private Button insertBtn, updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        execDBInitCommands();

        // UI Button Listeners
        Button viewBtn = findViewById(R.id.view_button);
        viewBtn.setOnClickListener(this);

        insertBtn = findViewById(R.id.insert_button);
        insertBtn.setOnClickListener(this);

        updateBtn = findViewById(R.id.update_button);
        updateBtn.setOnClickListener(this);

        Button upgradeBtn = findViewById(R.id.upgrade_button);
        upgradeBtn.setOnClickListener(this);
    }

    void execDBInitCommands() {
        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.deleteAllData();

        String[] projection = new String[]{"id","full_name","date_added"};
        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);

        while (cursor.moveToNext()) {
            Student s = new Student(cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getLong(2));
            mDatabaseHelper.addDataSupport(s);
        }
    }

    Student generateRandStudent() {
        Faker rng = new Faker();
        return new Student(rng.name.firstName(), rng.name.firstName(), rng.name.firstName(), System.currentTimeMillis());
    }

    @Override
    public void onClick(View v) {
        String msg;

        switch (v.getId()) {
            case R.id.view_button:
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
                return;

            case R.id.insert_button:
                mDatabaseHelper.addData(generateRandStudent());
                msg = "Record Added Successfully";
                break;

            case R.id.update_button:
                mDatabaseHelper.updateLastAddedName("Ivanov", "Ivan","Ivanovich");
                msg = "Record Updated Successfully";
                break;

            case R.id.upgrade_button:
                DatabaseHelper.upgrade();
                insertBtn.setEnabled(true);
                updateBtn.setEnabled(true);
                return;

            default:
                return;
        }

        // Extra Unnecessary Beautification
        Cursor cursor = mDatabaseHelper.getLastID();
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            msg+= " @:" + id;
        }
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
