package com.example.wei.hn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.wei.hn.Database.Myfavorite;
import com.example.wei.hn.HtmlImgGetter.HtmlImageGetter;


/**
 * Created by wei on 2015/7/3.
 */
public class Details extends Activity {
    private String PhotoUrl;
    private String title;
    private String date;
    private String author;
    private String description;
    private ImageView imageView1;
    private TextView textView;
    private TextView titleView;
    private TextView dateView;
    private TextView authorView;
    private Html.ImageGetter imageGetter;
    private Handler handler;
    private RequestQueue queue;
    private ImageRequest request;
    private Drawable defaultDrawable;
    private ShareActionProvider mShareActionProvider;
    private Intent sharingIntent;
    private Spanned sp;
    private Myfavorite mNewsDB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        //get intent bundle
        Bundle bundle=getIntent().getExtras();
        //sqlite
        android.database.sqlite.SQLiteDatabase DB;
        DB= SQLiteDatabase.openOrCreateDatabase("data/data/com.example.wei.hn/databases/news", null);
        //VOLLEY QUEUE
         queue= Volley.newRequestQueue(this);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        textView= (TextView) findViewById(R.id.textView);
        titleView = (TextView) findViewById(R.id.title);
        dateView = (TextView) findViewById(R.id.date);
        authorView = (TextView) findViewById(R.id.author);
        defaultDrawable = this.getResources().getDrawable(R.drawable.stub);
//        AsyncThread asyncTask = new AsyncThread();
//        asyncTask.execute();
        if(bundle!=null){
            PhotoUrl=bundle.getString("Imageurl");
            if(PhotoUrl == null){
                imageView1.setVisibility(View.GONE);
            }
            title = bundle.getString("title");
            description = bundle.getString("description");
            date = bundle.getString("date");
            author = bundle.getString("author");
        }
        init();
        mNewsDB = new Myfavorite(this);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_menu, menu);
       // Locate MenuItem with ShareActionProvider
       //MenuItem item = menu.findItem(R.id.menu_item_share);
       // Fetch and store ShareActionProvider
       //mShareActionProvider = (ShareActionProvider) item.getActionProvider();

            sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

            sharingIntent.setType("text/plain");
            String shareBody = title;
            Spanned shareContent = sp;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I find a great news on Huffington!!!");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            //sharingIntent.putExtra(Intent.EXTRA_ORIGINATING_URI, "www.baidu.com");
        // Return true to display menu
        return true;

    }
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share) {
            startActivity(sharingIntent);
            Log.d("press","pressed");
        }
        if (item.getItemId() == R.id.save) {

            String savedtitle = titleView.getText().toString();

            mNewsDB.insert(savedtitle);



            Toast.makeText(this, "Add Successed!", Toast.LENGTH_SHORT).show();
        }
        return false;
        // Fetch and store ShareActionProvider

    }



    private void init() {
        //t.start();
        if(description!=null) {
            sp = Html.fromHtml(description, new HtmlImageGetter(textView, "/esun_msg", defaultDrawable), null);
        }

        textView.setText(sp);
        titleView.setText(title);
        dateView.setText(date);
        authorView.setText(author);
            if (PhotoUrl != null) {
                Log.d("URL3", PhotoUrl);
                //REQUEST IMG
                Bitmap bitmap = MainActivity.imageCache.get(title);
                if (bitmap != null) {
                    Log.d("loading IMG from", "Cache");
                    imageView1.setImageBitmap(bitmap);

                } else {
                    request = new ImageRequest(PhotoUrl,
                            new Response.Listener<Bitmap>() {

                                @Override
                                public void onResponse(Bitmap arg0) {
                                    imageView1.setImageBitmap(arg0);

                                    MainActivity.imageCache.put(title, arg0);
                                }
                            },
                            300, 300,
                            Bitmap.Config.ARGB_8888,
                            new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    imageView1.setVisibility(View.GONE);
                                    Log.d("FlowerAdapter", "No Image");
                                }
                            }
                    );
                    queue.add(request);
                }
            }

        }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    }







