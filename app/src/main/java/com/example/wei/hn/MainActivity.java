package com.example.wei.hn;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wei.hn.Adapters.NewsAdapter;
import com.example.wei.hn.Containers.News;
import com.example.wei.hn.Database.Database;
import com.example.wei.hn.Parsers.JSONParse;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
/**
 * Created by wei on 2015/7/2.
 */

public class MainActivity extends ListActivity implements OnItemClickListener {
    List<News> newsList;
    ListView listView = null;
    ImageView imageView1;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
       static LruCache<String, Bitmap> imageCache;
//    final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
//    final int cacheSize = maxMemory / 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        init();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager
                .beginTransaction();
        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();
        listView=this.getListView();
        listView.setOnItemClickListener(this);
        //CREAT IMG CACHE
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent db = new Intent(this, Database.class);
        if (item.getItemId() == R.id.db) {
            startActivity(db);
            Log.d("press","pressed");
        }
        return false;
        // Fetch and store ShareActionProvider

    }
    private void init() {
        //Request Json data
       if (isOnline()) {
            requestData("http://feeds.contenthub.aol.com/syndication/2.0/feed/557ef73a1f117");
       } else {
           Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
       }
    }


    private void requestData(String uri) {
        //request Json DATA process
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        newsList = JSONParse.parseFeed(response);
                        updateDisplay();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void updateDisplay() {
        //SetAdapter
        NewsAdapter adapter = new NewsAdapter(this, R.layout.item_news, newsList);
        setListAdapter(adapter);

    }

//Check Network connection
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
   }

    //after click then load Img
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        listView=this.getListView();
        listView.setOnItemClickListener(this);
        String imageUrl = newsList.get(position).getImage();
        String title = newsList.get(position).gettitle();
        String date = newsList.get(position).getDate();
        String description = newsList.get(position).getdescription();
        String author = newsList.get(position).getAuthor();
        Intent intent=new Intent(this, Details.class);
        intent.putExtra("title", title);
        intent.putExtra("description",description);
        intent.putExtra("date",date);
        intent.putExtra("author",author);
        if (imageUrl!=null) {

         Log.d("URL1",imageUrl);
            intent.putExtra("Imageurl", imageUrl);

            //FragmentwithIMG fragment1 = new FragmentwithIMG();
            // android.R.id.content refers to the content
            // view of the activity
           // fragmentTransaction.replace(android.R.id.content, fragment1);
        }else {
            Log.d("URL2", "null");

        }startActivity(intent);

    }
}


