package com.example.lab2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        setContentView(R.layout.activity_main);

        //request write permission\
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        startListFrag();
    }

    private void startListFrag() {
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

//Snippet for Later: Displaying GIF error image (using Glide)
//final Handler handler = new Handler();
//..
//.listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Glide.with(parent).load(R.drawable.loading2).into(vh.Iv);
//                            }
//                        });
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })