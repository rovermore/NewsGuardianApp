package com.example.rovermore.newsguardianapp;

/**
 * Created by robertomoreno on 10/12/17.
 */



public class Noticia {

    String date;
    String section;
    String title;
    String url;


    public Noticia(String date, String section, String title, String url){

        this.date=date;
        this.section=section;
        this.title=title;
        this.url=url;

    }

    public String getNoticiaDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
