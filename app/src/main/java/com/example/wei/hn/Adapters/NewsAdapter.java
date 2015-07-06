package com.example.wei.hn.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.wei.hn.Containers.News;
import com.example.wei.hn.R;

import java.util.List;

/**
 * Created by wei on 2015/7/3.
 */
public class NewsAdapter extends ArrayAdapter<News>{
    private Context context;
    private List<News> newsList;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue queue;
    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource,objects);
        this.context = context;
        this.newsList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_news, parent, false);
        final News news = newsList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(news.gettitle());
        return view;
    }

}
