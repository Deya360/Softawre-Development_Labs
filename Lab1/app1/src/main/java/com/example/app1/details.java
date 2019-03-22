package com.example.app1;

import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class details extends AppCompatActivity {

    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView textView = (TextView)findViewById(R.id.textView);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);


        imgPath = getIntent().getStringExtra("IV_Image_Path");
        String wordifiedString = getIntent().getStringExtra("TV_Text");

        if (!getIntent().getBooleanExtra("isLocal",false)) {
            supportPostponeEnterTransition();

            Glide.with(this)
                    .as(PictureDrawable.class)
                    .fitCenter()
                    .listener(new SvgSoftwareLayerSetter())
                    .dontAnimate()
                    .placeholder(R.drawable.loading)
                    .listener(new RequestListener<PictureDrawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<PictureDrawable> target, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(PictureDrawable resource, Object model, Target<PictureDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }
                    })
                    .load(imgPath).into(imageView);

        } else {
            imageView.setImageResource(getResources().getIdentifier(imgPath,"drawable",getPackageName()));
        }

        textView.setText(wordifiedString);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Toast.makeText(getBaseContext(), imgPath, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
