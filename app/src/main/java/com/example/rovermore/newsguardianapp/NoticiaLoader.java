package com.example.rovermore.newsguardianapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by robertomoreno on 11/12/17.
 */



public class NoticiaLoader extends AsyncTaskLoader{

    String userQuery = "";

    ArrayList<Noticia> noticias = new ArrayList<>();

    public NoticiaLoader(Context context, String str){
        super(context);

        this.userQuery=str;

    }


    @Override
    public ArrayList<Noticia> loadInBackground() {

        //This try and catch makes the loader wait for two seconds to star working
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String stringUrl = QueryUtils.createUrlWithQuery(String.valueOf(userQuery));

        URL urlObject = QueryUtils.createURL(stringUrl);

        String jSonResponse ="";
        try {
            jSonResponse = QueryUtils.makeHttpRequest(urlObject);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            noticias = QueryUtils.extractNoticia(jSonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return noticias;
    }
}
