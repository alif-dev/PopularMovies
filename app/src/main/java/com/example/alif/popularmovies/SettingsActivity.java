package com.example.alif.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {
    String preferredSortBy;
    RadioButton radioBtnPopularity;
    RadioButton radioBtnRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViews();
        checkPreferredSortBy();
    }

    public void findViews() {
        radioBtnPopularity = findViewById(R.id.rbtn_by_popularity);
        radioBtnRating = findViewById(R.id.rbtn_by_rate);
    }

    public void checkPreferredSortBy() {
        preferredSortBy = loadPreferredSortBy();

        switch (preferredSortBy) {
            case "popular":
                radioBtnPopularity.setChecked(true);
                break;
            case "top_rated":
                radioBtnRating.setChecked(true);
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbtn_by_popularity:
                if (checked)
                    preferredSortBy = "popular";
                break;
            case R.id.rbtn_by_rate:
                if (checked)
                    preferredSortBy = "top_rated";
                break;
        }
    }

    private String loadPreferredSortBy() {
        SharedPreferences sharedPreferences = getSharedPreferences("movieOrder", 0);
        String sortBy = sharedPreferences.getString("currentOrderMode", "popular");
        return sortBy;
    }

    private void savePreferredSortBy(String preferredSortBy) {
        SharedPreferences sharedPreferences = getSharedPreferences("movieOrder", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("currentOrderMode", preferredSortBy);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.save) {
            savePreferredSortBy(preferredSortBy);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
