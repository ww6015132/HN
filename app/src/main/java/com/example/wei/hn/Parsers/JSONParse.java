package com.example.wei.hn.Parsers;

import com.example.wei.hn.Containers.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 2015/7/2.
 */
public class JSONParse {
    public static List<News> parseFeed(JSONObject jsonObject ) {

        try {
            JSONObject  channel=jsonObject.getJSONObject("channel");

            JSONArray ar =channel.getJSONArray("item");

           List<News> newsList = new ArrayList<>();


            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);

                News news = new News();
                JSONArray media_content = obj.getJSONArray("media_content");
                JSONObject content=null;
                String Image=null;
                for(int j = 0; j < media_content.length() ; j++){
                    content = media_content.getJSONObject(j);
                    Image = content.getString("url");
                }

                news.setImage(Image);
                news.settitle(obj.getString("title"));
                news.setDate(obj.getString("modified"));
                news.setdescription(obj.getString("description"));
                news.setAuthor(obj.getString("dc_creator"));
                news.setImage(Image);


                //news.setdescription(obj.getString("description"));



                newsList.add(news);
            }
           return newsList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
