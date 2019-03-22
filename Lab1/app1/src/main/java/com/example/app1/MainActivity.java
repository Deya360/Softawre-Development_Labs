package com.example.app1;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Random;

//import android.util.Pair;

public class MainActivity extends AppCompatActivity {
    private static int LIST_ITEM_COUNT = 1000*1000;
    ListView myLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


        TextView textView = (TextView)findViewById(R.id.Tv);
        ImageView imageView = (ImageView)findViewById(R.id.Iv);

        myLv = (ListView)findViewById(R.id.Lv);
        myLv.setFastScrollEnabled(true);

        myLv.setOnItemClickListener(new CustomListener());
        myLv.setAdapter(new CustomAdapter());
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return LIST_ITEM_COUNT;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater vi = LayoutInflater.from(parent.getContext());
                convertView = vi.inflate(R.layout.custom_list, parent, false);
            }

            TextView myTv = (TextView)convertView.findViewById(R.id.Tv);
            ImageView myIv = (ImageView)convertView.findViewById(R.id.Iv);


            //Data Population
            Random r = new Random();
            String avatarLocal = "flags_img_" + (r.nextInt(100));
            String avatarUrl = "https://avatars.dicebear.com/v2/avataaars/"+ position + "r" + (r.nextInt(100)) +".svg";

            RequestBuilder<PictureDrawable> requestBuilder = Glide.with(parent)
                    .as(PictureDrawable.class)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .placeholder(R.drawable.loading)
                    .listener(new SvgSoftwareLayerSetter())
                    .error(getResources().getDrawable(getResources().getIdentifier(avatarLocal, "drawable", getPackageName())));


            requestBuilder.load(Uri.parse(avatarUrl)).into(myIv);
            myIv.setTag(R.string.pathLocal, avatarLocal);
            myTv.setText(Wordify.inwords(position+1));


            //Background Shading
            if (position % 2 == 1) {
                convertView.setBackgroundResource(R.drawable.ripple_dark);
            } else {
                convertView.setBackgroundResource(R.drawable.ripple_light);
            }

            //Scrolling Animation
            Animation fadeAnim = AnimationUtils.loadAnimation(parent.getContext(), R.anim.fade_in_fast);
            convertView.startAnimation(fadeAnim);

            return convertView;
        }
    }

    class CustomListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(view.getContext(), "ID: " + position, Toast.LENGTH_SHORT).show();

            TextView myTv = (TextView) view.findViewById(R.id.Tv);
            ImageView myIv = (ImageView) view.findViewById(R.id.Iv);

            if (myIv.getTag(R.string.pathURL)!= null) {
                ActivityOptionsCompat opt = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        new Pair<View, String>((TextView) view.findViewById(R.id.Tv), getString(R.string.TvTrans)),
                        new Pair<View, String>((ImageView) view.findViewById(R.id.Iv), getString(R.string.IvTrans)));

                Intent intent = new Intent(view.getContext(), details.class);
                intent.putExtra("TV_Text", myTv.getText());

                String out;
                if ((out = myIv.getTag(R.string.pathURL).toString()).isEmpty()) {
                    out = myIv.getTag(R.string.pathLocal).toString();
                    intent.putExtra("isLocal", true);
                }
                intent.putExtra("IV_Image_Path", out);
                startActivity(intent, opt.toBundle());

//                Animation animation1 = new AlphaAnimation(0.5f, 1.2f);
//                animation1.setDuration(500);
//                view.startAnimation(animation1);
            }
        }
    }
}