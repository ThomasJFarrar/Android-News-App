package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * The settings activity.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Sets up the activity.
     * @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}