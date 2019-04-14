package com.example.lab2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private SwipeRefreshLayout mSwipeRefreshLayout;
    DataFetcher dataFetcherTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create the list fragment's content view by calling the super method
        final View listFragmentView = super.onCreateView(inflater, container, savedInstanceState);

        // Now create a SwipeRefreshLayout to wrap the fragment's content view
        mSwipeRefreshLayout = new ListFragmentSwipeRefreshLayout(container.getContext());

        // Add the list fragment's content view to the SwipeRefreshLayout, making sure that it fills the SwipeRefreshLayout
        mSwipeRefreshLayout.addView(listFragmentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Make sure that the SwipeRefreshLayout will fill the fragment
        mSwipeRefreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();

                OnTaskCompleted localListener = new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(Boolean success) {
                        if(success) {
                            Toast.makeText(getActivity(), "Refresh Completed", Toast.LENGTH_SHORT).show();
                            populateData();
                        } else {
                            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                };

                dataFetcherTask = new DataFetcher(getActivity(), localListener);
                dataFetcherTask.execute(getResources().getString(R.string.jsonTextURL));
            }
        });

        return mSwipeRefreshLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("Error Loading Items");
        getListView().setFastScrollEnabled(true);
        populateData();
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
                .replace(R.id.MainLayout,viewFrag,getResources().getString(R.string.viewFragTag))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        if(dataFetcherTask != null)
            dataFetcherTask.cancel(true);
        super.onDestroyView();
    }

    public void populateData() {
        ArrayList<Item> dataArr = Data.getData();
        setListAdapter(new CustomListAdapter(getActivity(), dataArr));
    }



    private class ListFragmentSwipeRefreshLayout extends SwipeRefreshLayout {
        public ListFragmentSwipeRefreshLayout(Context context) {
            super(context);
        }

        @Override
        public boolean canChildScrollUp() {
            final ListView listView = getListView();
            if (listView.getVisibility() == View.VISIBLE) {
                return canListViewScrollUp(listView);
            } else {
                return false;
            }
        }

    }
    private static boolean canListViewScrollUp(ListView listView) {
        return listView.canScrollVertically(-1);
    }
}

class CustomListAdapter extends ArrayAdapter<Item> {
    private static class ViewHolder {
        private final View rootView;
        private final TextView Tv;
        private final ImageView Iv;

        private ViewHolder(View rootView, TextView Tv, ImageView Iv) {
            this.rootView = rootView;
            this.Tv = Tv;
            this.Iv = Iv;
        }

        private static ViewHolder create(View rootView) {
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
        Glide.with(parent)
                .load(getContext().getResources().getString(R.string.jsonImageURLPrefix) + item.getImageURLSuffix())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .placeholder(R.drawable.loading)
                .error(R.drawable.not_found)
                .into(vh.Iv);

//        // Background Shading
//        if (position % 2 == 1) {
//            vh.rootView.setBackgroundResource(R.drawable.ripple_dark);
//        } else {
//            vh.rootView.setBackgroundResource(R.drawable.ripple_light);
//        }

        //Scrolling Animation
        Animation fadeAnim = AnimationUtils.loadAnimation(parent.getContext(), R.anim.fade_in_fast);
        vh.rootView.startAnimation(fadeAnim);

        return vh.rootView;
    }
}
