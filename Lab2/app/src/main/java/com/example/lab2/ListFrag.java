package com.example.lab2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class ListFrag extends ListFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Item> dataArr = Data.getData();
        setEmptyText("Error Loading Items");
        setListAdapter(new CustomListAdapter(getActivity(), dataArr));
        getListView().setFastScrollEnabled(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getActivity(), "ID: " + position, Toast.LENGTH_SHORT).show();

        ViewFrag viewFrag = new ViewFrag();

        Bundle bundle=new Bundle();
        bundle.putInt("pos", position);
        viewFrag.setArguments(bundle);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down)
//                .addSharedElement((TextView)v.findViewById(R.id.Tv),getString(R.string.TvTrans))
//                .addSharedElement((ImageView)v.findViewById(R.id.Iv),getString(R.string.IvTrans))
                .replace(R.id.MainLayout,viewFrag,getResources().getString(R.string.viewFragTag))
                .addToBackStack(null)
                .commit();
    }
}

class CustomListAdapter extends ArrayAdapter<Item> {
    private static class ViewHolder {
        public final View rootView;
        public final TextView Tv;
        public final ImageView Iv;

        private ViewHolder(View rootView, TextView Tv, ImageView Iv) {
            this.rootView = rootView;
            this.Tv = Tv;
            this.Iv = Iv;
        }

        public static ViewHolder create(View rootView) {
            TextView Tv = (TextView)rootView.findViewById( R.id.Tv );
            ImageView Iv = (ImageView)rootView.findViewById( R.id.Iv );
            return new ViewHolder( rootView, Tv, Iv );
        }
    }

    public CustomListAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder vh;
        Item item = getItem(position);

        if (convertView == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_list, parent, false);
            vh = ViewHolder.create(view);
            view.setTag(vh);

        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        vh.Tv.setText(item.getName());
//        vh.Iv.setContentDescription(item.getImageURLSuffix());

        Glide.with(parent)
                .load(getContext().getResources().getString(R.string.jsonImageURLPrefix) + item.getImageURLSuffix())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(vh.Iv);

        // Background Shading
        if (position % 2 == 1) {
            vh.rootView.setBackgroundResource(R.drawable.ripple_dark);
        } else {
            vh.rootView.setBackgroundResource(R.drawable.ripple_light);
        }

        //Scrolling Animation
        Animation fadeAnim = AnimationUtils.loadAnimation(parent.getContext(), R.anim.fade_in_fast);
        vh.rootView.startAnimation(fadeAnim);

        return vh.rootView;
    }
}
