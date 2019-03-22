package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_DURATION = 2000;

    ImageView iv;
    Animation fade_in, push_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        iv = (ImageView)findViewById(R.id.iv1);

        fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        push_up = AnimationUtils.loadAnimation(this, R.anim.push_up);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(fade_in);
        animationSet.addAnimation(push_up);
        iv.startAnimation(animationSet);

        Thread closeActivity = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_DURATION);
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        });
        closeActivity.start();
    }
}
