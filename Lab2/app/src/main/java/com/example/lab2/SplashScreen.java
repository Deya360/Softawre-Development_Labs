package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity{
    DataFetcher dataFetcherTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        setContentView(R.layout.activity_splash_screen);

        ImageView iv = (ImageView)findViewById(R.id.iv1);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
        animationSet.addAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up));
        animationSet.addAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_down));
        iv.startAnimation(animationSet);

        OnTaskCompleted localListener = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Boolean success) {
                if(!success) {
                    Toast.makeText(getApplicationContext(), "Load Failed", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        };

        dataFetcherTask = new DataFetcher(this.getApplicationContext(), localListener);
        dataFetcherTask.execute();
    }

    @Override
    protected void onDestroy() {
        if(dataFetcherTask != null)
            dataFetcherTask.cancel(true);
        super.onDestroy();
    }

}
