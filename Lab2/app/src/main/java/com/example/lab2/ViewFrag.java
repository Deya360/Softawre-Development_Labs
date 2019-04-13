package com.example.lab2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class  ViewFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int a = getArguments().getInt("pos");


        ViewPager vp = (ViewPager)getActivity().findViewById(R.id.Vp);
        vp.setAdapter(new CustomViewPagerAdapter(getActivity(), Data.getSize()));
        vp.setCurrentItem(getArguments().getInt("pos"));
    }
}

class CustomViewPagerAdapter extends PagerAdapter {
    private Context context;
    private int size;

    public CustomViewPagerAdapter(Context context, int size) {
        this.context = context;
        this.size = size;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.detailed_view, container, false);
        ImageView iv = (ImageView)view.findViewById(R.id.imageView);
        TextView tvN = (TextView)view.findViewById(R.id.textViewN);
        TextView tvH = (TextView)view.findViewById(R.id.textViewH);
        Item it = Data.getItem(position);

        tvN.setText(it.getName());
        tvH.setText(it.getHelptext());
        Glide.with(container)
                .load(context.getResources().getString(R.string.jsonImageURLPrefix) + it.getImageURLSuffix())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(iv);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}