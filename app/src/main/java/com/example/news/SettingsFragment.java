package com.example.news;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The settings fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat{

    /**
     * Sets up the settings fragment.
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     *                           this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the
     *                           {@link PreferenceScreen} with this key.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference clear = findPreference("clear");
        clear.setOnPreferenceClickListener(this::onPreferenceClick);
    }

    /**
     * Erases all saved articles if that is clicked.
     * @param preference The preference that has been clicked.
     * @return If the click was successful.
     */
    private boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("clear")) {
            try {
                /* Overwrite the saved articles file with nothing */
                FileOutputStream fos = getActivity().openFileOutput("saved.txt", Context.MODE_PRIVATE);
                fos.write("".getBytes());
                fos.close();
                Toast.makeText(getActivity(), "Erased all saved articles", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}