package com.example.wei.hn.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class Myfavorite extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "NEWS.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "news_table";
    public final static String ID = "id";
    public final static String Title = "title";


    public Myfavorite(Context context) {
// TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID
                + " INTEGER primary key autoincrement, " + Title + " text  )";
        db.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db
                .query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
    //insert
    public long insert(String title)
    {
//        String where = Title + " = ?";
//        String[] whereValue = { title };

        SQLiteDatabase db = this.getWritableDatabase();
        /* ContentValues */
       ContentValues cv = new ContentValues();
       cv.put(Title, title);
//        db.replace(TABLE_NAME, cv, where, whereValue);

        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //delete
    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ID + " = ?";
        String[] whereValue ={ Integer.toString(id) };
        db.delete(TABLE_NAME, where, whereValue);
    }
    //update
    public void update(int id, String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ID + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(Title, title);

        db.update(TABLE_NAME, cv, where, whereValue);
    }
}
