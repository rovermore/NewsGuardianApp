package com.example.rovermore.newsguardianapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by robertomoreno on 10/12/17.
 */

public class NoticiaAdapter extends ArrayAdapter<Noticia> {

    public NoticiaAdapter(Context context, @NonNull ArrayList<Noticia> Noticia) {
        super(context, 0, Noticia);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        Noticia currentNoticia = getItem(position);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date_view);

        dateView.setText(currentNoticia.getNoticiaDate());

        TextView sectionView = (TextView) listItemView.findViewById(R.id.sectionName_view);

        sectionView.setText(currentNoticia.getSection());

        TextView titleView  = (TextView) listItemView.findViewById(R.id.title_view);

        titleView.setText(currentNoticia.getTitle());

        return listItemView;

    }
}
