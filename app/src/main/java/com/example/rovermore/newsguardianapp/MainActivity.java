package com.example.rovermore.newsguardianapp;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Noticia>> {

    private TextView emptyStateView;
    private ProgressBar progressBar;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText userQuery;

    ListView noticiaListView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inflates emptyStateView in order to not apperar in screen until loading has finished
        emptyStateView = (TextView) findViewById(R.id.empty_state);

        //Saves ProgresBar view into an object in order to treat it from java
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        //Takes the search form the user and ads it into a String
        userQuery = (EditText) findViewById(R.id.noticia_search_bar);

        Button search = (Button) findViewById(R.id.search_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checks If the user did not write a query Showing a toast in case not
                if (userQuery != null) {

                    progressBar.setVisibility(View.VISIBLE);

                    Log.v("initloader", "The oncreate has runned and loader has started");
                    getLoaderManager().initLoader(0, null, MainActivity.this).forceLoad();


                } else {
                    Toast.makeText(getApplicationContext(), "Introduce a query",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



    }


    private void createUI(final ArrayList<Noticia> noticiaArrayList) {

        //Inflates the adapter with the arraylist fetched from the jason
        NoticiaAdapter adapter = new NoticiaAdapter(this, noticiaArrayList);
        //Connects the ListView with the xml
        noticiaListView = (ListView) findViewById(R.id.list);
        //Sets empty state in case any result is found
        noticiaListView.setEmptyView(emptyStateView);
        //Sets the adapter to the view
        noticiaListView.setAdapter(adapter);

        noticiaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<Noticia> clickNoticiaArraylist = new ArrayList<>(noticiaArrayList);

                Noticia currentNoticia = clickNoticiaArraylist.get(position);

                String urlNoticia = currentNoticia.getUrl();

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

                intent.putExtra(SearchManager.QUERY, urlNoticia);

                startActivity(intent);

            }
        });

    }


    @Override
    public Loader<ArrayList<Noticia>> onCreateLoader(int i, Bundle bundle) {

        final String userSearchText = userQuery.getText().toString();
        Log.v("onCreateLoader", "onCreate loader running loader should be created");

        NoticiaLoader noticiaLoader = new NoticiaLoader(this, userSearchText);

        return noticiaLoader;
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Noticia>> loader, ArrayList<Noticia> noticia) {

        Log.v("onLoadFinished", "update of the UI should start and create de interface");

        //Makes progerss bar disappear when the loader is loaded
        progressBar.setVisibility(View.GONE);

        createUI(noticia);

        getLoaderManager().destroyLoader(0);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Noticia>> loader) {

        Log.v("onLoaderReset", "Create a new arraylist");
        createUI(new ArrayList<Noticia>());


    }


}
