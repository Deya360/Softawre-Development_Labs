package com.example.lab2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        setContentView(R.layout.activity_main);

        String tag = getResources().getString(R.string.listFragTag);

        FragmentManager manager = getSupportFragmentManager();
        Fragment listFrag = manager.findFragmentByTag(tag);
        if (listFrag == null) {
            listFrag = new ListFrag();
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down)
                    .replace(R.id.MainLayout,listFrag,tag)
                    .commit();
        }
    }

}

