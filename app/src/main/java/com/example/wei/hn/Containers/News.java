package com.example.wei.hn.Containers;

import android.graphics.Bitmap;

/**
 * Created by wei on 2015/7/2.
 */
public class News {
    private String title;
    private String description;
    private String image;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private  Bitmap bitmap;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }


}
