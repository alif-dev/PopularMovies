package com.example.alif.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alif on 01/03/18.
 */

public class
MovieAdapter extends ArrayAdapter<MovieData> {
    Context context;
    int resource;
    ArrayList<MovieData> movieData;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MovieData> movieData) {
        super(context, resource, movieData);
        this.context = context;
        this.resource = resource;
        this.movieData = movieData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parentViewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.grid_content, parentViewGroup, false);
        }

        MovieData movieData = getItem(position);
        ImageView movieImageView = convertView.findViewById(R.id.movie_image);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342" + movieData.getMovie_poster()).into(movieImageView);

        return convertView;
    }
}
