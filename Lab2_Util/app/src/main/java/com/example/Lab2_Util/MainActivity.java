package com.example.Lab2_Util;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final Uri CONTENT_URI = Uri.parse("content://com.example.lab2.TechnologyProvider/cptechnologies");

    TextView tv;
    Button refresh, x;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ContentResolver App");

        sp = (Spinner)findViewById(R.id.spinner);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("name");
        spinnerArray.add("helptext");
        spinnerArray.add("graphic");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        tv = (TextView)findViewById(R.id.textView);
        tv.setMovementMethod(new ScrollingMovementMethod());

        refresh = (Button)findViewById(R.id.buttonRefresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(getData(sp.getSelectedItem().toString()));
            }
        });

        x = (Button)findViewById(R.id.buttonX);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(CONTENT_URI, null, null);
                Toast.makeText(getBaseContext(), "Content Provider Data Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getData(String colName) {
        String[] projection = new String[]{"name","helptext","graphic"};

        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null, null);

        String list = "";
        int counter = 1;

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(colName));
                list = list + counter++ + ". " + name + "\n";
            } while (cursor.moveToNext());
        }

        if (list.isEmpty()) list="....";
        return list;
    }
}
