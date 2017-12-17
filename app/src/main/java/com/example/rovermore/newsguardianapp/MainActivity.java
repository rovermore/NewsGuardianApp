package com.example.rovermore.newsguardianapp;

import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView emptyStateView;
    private ProgressBar progressBar;

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private EditText userQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void createUI(final ArrayList<Noticia> bookArrayList){



        NoticiaAdapter adapter = null;

        adapter = new NoticiaAdapter(this, bookArrayList);

        ListView booksListView = (ListView) findViewById(R.id.list_item);

        booksListView.setEmptyView(emptyStateView);

        booksListView.setAdapter(adapter);


    }



    public Loader<ArrayList<Noticia>> onCreateLoader(int i, Bundle bundle){

        final String userSearchText= userQuery.getText().toString();
        Log.v("onCreateLoader","onCreate loader running loader should be created");

        NoticiaLoader noticiaLoader = new NoticiaLoader(this, userSearchText);



        return noticiaLoader;
    }


    public void onLoadFinished(Loader<ArrayList<Noticia>> loader, ArrayList<Noticia> noticia) {

        Log.v("onLoadFinished","update of the UI should start and create de interface");



        //Makes progerss bar disappear when the loader is loaded
        progressBar.setVisibility(View.GONE);

        createUI(noticia);


        getLoaderManager().destroyLoader(0);


    }


    public void onLoaderReset(Loader<ArrayList<Noticia>> loader) {

        Log.v("onLoaderReset","Create a new arraylist");
        createUI(new ArrayList<Noticia>());



    }


}
