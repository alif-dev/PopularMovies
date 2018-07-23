package com.example.alif.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alif on 01/03/18.
 */

public class MovieData implements Parcelable {
    private String original_title;
    private String movie_poster;
    private String plot_synopsis;
    private String user_rating;
    private String release_date;

    public MovieData(String original_title, String movie_poster, String plot_synopsis, String user_rating, String release_date) {
        this.original_title = original_title;
        this.movie_poster = movie_poster;
        this.plot_synopsis = plot_synopsis;
        this.user_rating = user_rating;
        this.release_date = release_date;
    }

    protected MovieData(Parcel in) {
        original_title = in.readString();
        movie_poster = in.readString();
        plot_synopsis = in.readString();
        user_rating = in.readString();
        release_date = in.readString();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original_title);
        dest.writeString(movie_poster);
        dest.writeString(plot_synopsis);
        dest.writeString(user_rating);
        dest.writeString(release_date);
    }
}
