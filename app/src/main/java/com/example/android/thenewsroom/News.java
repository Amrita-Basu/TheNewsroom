package com.example.android.thenewsroom;

/**
 * Created by DELL on 14-04-2017.
 */

public class News {

    private String title;
    private String author;
    private String time;
    private String description;
    private String url;

    public News(String t, String a, String tm, String d, String u) {
        this.title = t;
        this.author = a;
        this.time = tm;
        this.description = d;
        this.url = u;
    }


    public String getTitle() {
        return title;

    }

    public String getAuthor() {
        return author;

    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

}
