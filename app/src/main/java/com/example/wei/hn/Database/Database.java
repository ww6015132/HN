package com.example.wei.hn.Database;

/**
 * Created by wei on 2015/7/3.
 */
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wei.hn.R;

public class Database extends Activity implements AdapterView.OnItemClickListener {
    private Myfavorite mNewsDB;
    private Cursor mCursor;
    private EditText Title;

    private ListView NewsList;

    private int ID = 0;
    protected final static int MENU_ADD = Menu.FIRST;
    protected final static int MENU_DELETE = Menu.FIRST + 1;
    protected final static int MENU_UPDATE = Menu.FIRST + 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfavorite);
        setUpViews();
    }

    public void setUpViews(){
        mNewsDB = new Myfavorite(this);
        mCursor = mNewsDB.select();

        Title = (EditText)findViewById(R.id.title);

        NewsList = (ListView)findViewById(R.id.newslist);

        NewsList.setAdapter(new NewsListAdapter(this, mCursor));
        NewsList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.delete, menu);
        //menu.add(Menu.NONE, MENU_ADD, 0, "ADD");
        //menu.add(Menu.NONE, MENU_DELETE, 0, "DELETE");
        //menu.add(Menu.NONE, MENU_DELETE, 0, "UPDATE");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
//            case R.id.MENU_ADD:
//                add();
//                break;
            case R.id.MENU_DELETE:
                delete();
                break;
//            case R.id.MENU_UPDATE:
//                update();
//                break;
        }
        return true;
    }

    public void add(){
        String title = Title.getText().toString();

        //title cant be null
        if (title.equals("")){
            return;
        }
        mNewsDB.insert(title);
        mCursor.requery();
        NewsList.invalidateViews();
        Title.setText("");

        Toast.makeText(this, "Add Successed!", Toast.LENGTH_SHORT).show();
    }

    public void delete(){
        if (ID == 0) {
            return;
        }
        mNewsDB.delete(ID);
        mCursor.requery();
        NewsList.invalidateViews();
        Title.setText("");

        Toast.makeText(this, "Delete Successed!", Toast.LENGTH_SHORT).show();
    }

    public void update(){
        String title = Title.getText().toString();


        if (title.equals("") ){
            return;
        }
        mNewsDB.update(ID, title);
        mCursor.requery();
        NewsList.invalidateViews();
        Title.setText("");

        Toast.makeText(this, "Update Successed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mCursor.moveToPosition(position);
        ID = mCursor.getInt(0);
        //Title.setText(mCursor.getString(1));
         //view.setBackgroundColor(Color.parseColor("#F5F5DC"));
        //change color
        for(int i=0;i<parent.getCount();i++){
            View v=parent.getChildAt(i);
            if (position == i) {
                view.setBackgroundColor(Color.YELLOW);
            } else {
              v.setBackgroundColor(Color.TRANSPARENT);
            }

            //view.setBackgroundColor(Color.YELLOW);

        }
    }

    public class NewsListAdapter extends BaseAdapter{
        private Context mContext;
        private Cursor mCursor;
        public NewsListAdapter(Context context,Cursor cursor) {

            mContext = context;
            mCursor = cursor;
        }
        @Override
        public int getCount() {
            return mCursor.getCount();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView mTextView = new TextView(mContext);
            mCursor.moveToPosition(position);
            mTextView.setText(mCursor.getString(1) );
            return mTextView;
        }

    }
}