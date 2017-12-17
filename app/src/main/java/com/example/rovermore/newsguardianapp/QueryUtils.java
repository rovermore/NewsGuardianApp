package com.example.rovermore.newsguardianapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.rovermore.newsguardianapp.MainActivity.LOG_TAG;

/**
 * Created by robertomoreno on 11/12/17.
 */

public class QueryUtils {


    public static final String URL_NOTICIA_REQUEST ="https://content.guardianapis.com/search?q";
    public static final String API_KEY= "05a3c47e-b7a6-410a-831f-ede407255b16";

    private QueryUtils() {
    }


    //This method generates a url from the query that user inputs
    public static String createUrlWithQuery (String str){

        Uri.Builder uriBuilderQuery = new Uri.Builder();

        uriBuilderQuery.scheme("https")
                        .authority("content.guardianapis.com")
                        .appendPath("search")
                        .appendQueryParameter("q",str)
                        .appendQueryParameter("api-key", API_KEY);

        String urlString = uriBuilderQuery.build().toString();

        return urlString;
    }

    public static URL createURL(String stringUrl){

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Error with the URL ", e);
            return null;
        }

        return url;

    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jSonResponse = "";

        if (url == null) {

            return jSonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = null;
            inputStream = null;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jSonResponse = readFromStream(inputStream);

                Log.v("urlConnection","succesful Http connection");

            } else {

                String stringErrorConection = String.valueOf(urlConnection.getResponseCode());

                Log.e(LOG_TAG, "Error to set Http conection: " + stringErrorConection);

            }

        } catch (IOException e) {

            Log.e(LOG_TAG, "EIOException" + e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

                Log.v("urlConnection", "disconnected");
            }

            if (inputStream != null) {

                inputStream.close();
                Log.v("inputStream", "closed");
            }
        }


        return jSonResponse;
    }


    public static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if(inputStream!=null){

            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while(line!=null){

                output.append(line);
                line = reader.readLine();
            }
        }

        String jSonOutput = String.valueOf(output);

        Log.v("readFromStream","jSon has been readed and added into a String");

        return jSonOutput;


    }




    public static ArrayList<Noticia> extractNoticia (String jSonString) throws JSONException {

        ArrayList<Noticia> noticiaArrayList = new ArrayList<>();


        JSONObject jsonNoticiaList = new JSONObject(jSonString);

        JSONObject jsonNoticiaResponse = jsonNoticiaList.getJSONObject("response");

        JSONArray jsonArrayNoticiaList = jsonNoticiaResponse.getJSONArray("results");

        for(int i=0;i<jsonArrayNoticiaList.length();i++){

            JSONObject item = jsonArrayNoticiaList.getJSONObject(i);

            String webPublicationDate = item.getString("webPublicationDate");

            String sectionName = item.getString("sectionName");

            String webTitle = item.getString("webTitle");

            String webUrl = item.getString("webUrl");

            Noticia noticia = new Noticia(webPublicationDate, sectionName, webTitle, webUrl);

            noticiaArrayList.add(noticia);

        }

        return noticiaArrayList;
    }
}
