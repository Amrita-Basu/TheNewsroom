package com.example.android.thenewsroom;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.y;
import static android.R.id.empty;
import static com.example.android.thenewsroom.R.id.emptyView;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = NewsActivity.class.getName();
    private static final String NEWS_API_REQUEST_URL = "https://newsapi.org/v1/articles";
    public static final int NEWS_LOADER_ID = 1;
    private static NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }



    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_news);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (!isConnected) {
            ProgressBar pb1 = (ProgressBar) findViewById(R.id.progress_indicator);
            pb1.setVisibility(View.GONE);
            TextView emptyView = (TextView) findViewById(R.id.emptyView);
            emptyView.setText("No internet connection.");

        } else {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        getLoaderManager().destroyLoader(NEWS_LOADER_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.preferences) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        if (id == R.id.about) {
            Intent settingsIntent = new Intent(this, AboutActivity.class);
            startActivity(settingsIntent);
            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortByStoredValue = sharedPrefs.getString(getString(R.string.settings_sort_by_key) , getString(R.string.settings_sort_by_top_value));

        String sourceNameStoredValue = sharedPrefs.getString(getString(R.string.settings_source_key), getString(R.string.source_ars_technica_value));
        Uri uri = Uri.parse(NEWS_API_REQUEST_URL);
        Uri.Builder builder = uri.buildUpon();
        builder.appendQueryParameter("source" , sourceNameStoredValue);
        builder.appendQueryParameter("sortBy" , sortByStoredValue);
        builder.appendQueryParameter("apiKey", "01c1ada412354476829465afddc34424");

        return new NewsLoader(this, builder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        ProgressBar pb = (ProgressBar) findViewById(R.id.progress_indicator);
        pb.setVisibility(View.GONE);

        TextView emptyView = (TextView) findViewById(R.id.emptyView);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(emptyView);
        emptyView.setText("No news at this point. Stay tuned!");

        if (data != null) {

            mAdapter = new NewsAdapter(this, data);
            listView.setAdapter(mAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    News newsObj = mAdapter.getItem(position);
                    Uri uri = Uri.parse(newsObj.getUrl());
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);

                    startActivity(i);

                }
            });


        }


    }


    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        if (mAdapter != null) {

            mAdapter.clear();
        }


    }
}


