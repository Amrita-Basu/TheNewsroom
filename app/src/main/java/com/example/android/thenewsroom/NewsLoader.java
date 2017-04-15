package com.example.android.thenewsroom;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * Created by AMRITA BASU on 14-04-2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>>{

    private static final String LOG_TAG = NewsLoader.class.getSimpleName();
    private String mUrl;


    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;

    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG , "onStartLoading" );
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if(mUrl == null)
            return null;

        else {

            ArrayList<News> data = QueryUtils.fetchNewsData(mUrl);
            return data;
        }


    }
}
