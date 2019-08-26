package com.example.alif.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    TextView title;
    ImageView movieImage;
    TextView userRating;
    TextView releaseDate;
    TextView overview;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        findViews();

        Bundle movieDataBundle = getIntent().getExtras();
        MovieData movieData = movieDataBundle.getParcelable("movieDetails");

        title.setText(movieData.getOriginal_title());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342" + movieData.getMovie_poster()).into(movieImage);
        userRating.setText(getString(R.string.user_rating_text) + movieData.getUser_rating());
        releaseDate.setText(getString(R.string.release_date_text) + movieData.getRelease_date());
        overview.setText(movieData.getPlot_synopsis());
    }

    private void findViews() {
        title = findViewById(R.id.movie_title);
        movieImage = findViewById(R.id.movie_image_detail);
        userRating = findViewById(R.id.movie_rating);
        releaseDate = findViewById(R.id.movie_release_date);
        overview = findViewById(R.id.movie_overview);
    }
}
