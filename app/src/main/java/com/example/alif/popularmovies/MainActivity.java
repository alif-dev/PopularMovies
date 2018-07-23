package com.example.alif.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    String sortBy;
    ArrayList<MovieData> movieDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movieDetails")) {

        } else {
            movieDetails = savedInstanceState.getParcelableArrayList("movieDetails");
        }

        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridview);

        sortBy = loadPreferredSortBy();
        loadMovieData(sortBy);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("movieDetails", movieDetails);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private String loadPreferredSortBy() {
        SharedPreferences sharedPreferences = getSharedPreferences("movieOrder", 0);
        String SortBy = sharedPreferences.getString("currentOrderMode", "popular");
        return SortBy;
    }

    public void loadMovieData(String sortBy) {
        new MovieTask().execute(sortBy);
    }

    class MovieTask extends AsyncTask<String, Void, ArrayList<MovieData>> {

        @Override
        protected ArrayList<MovieData> doInBackground(String... strings) {
            String sortBy = strings[0];
            URL url = Networking.buildURL(sortBy);
            ArrayList<MovieData> movieDataArrList = new ArrayList<>();
            String jsonData = null;
            try {
                jsonData = Networking.getHttpResponse(url);
                if (jsonData != null && !jsonData.equals("")) {
                    movieDataArrList = setArrayListData(jsonData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movieDataArrList;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieDataArrList) {
            super.onPostExecute(movieDataArrList);
            if (!movieDataArrList.isEmpty()) {
                final MovieAdapter adapter = new MovieAdapter(
                        getApplicationContext(), R.layout.grid_content, movieDataArrList);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MovieData movieData = adapter.getItem(position);
                        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                        intent.putExtra("movieDetails", movieData);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public ArrayList<MovieData> setArrayListData(String jsonData) throws JSONException {
        // I will use my own way later to set movie data
        ArrayList<MovieData> arrayListData = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieJsonObject = jsonArray.getJSONObject(i);
            arrayListData.add(new MovieData(
                    movieJsonObject.getString("original_title"),
                    movieJsonObject.getString("poster_path"),
                    movieJsonObject.getString("overview"),
                    movieJsonObject.getString("vote_average"),
                    movieJsonObject.getString("release_date")
            ));
        }
        // set value for ArrayList global variable of movieDetails
        movieDetails = arrayListData;
        return arrayListData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
