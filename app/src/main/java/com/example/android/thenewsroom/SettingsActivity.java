package com.example.android.thenewsroom;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.prefs.PreferenceChangeListener;

import static android.R.attr.value;
import static android.R.attr.x;

/**
 * Created by AMRITA BASU on 14-04-2017.
 */

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }


    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference source = findPreference(getString(R.string.settings_source_key));
            bindSummaryToValue(source);

            Preference sortBy = findPreference(getString(R.string.settings_sort_by_key));
            bindSummaryToValue(sortBy);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            String storedValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(storedValue);

                if (index >= 0) {
                    CharSequence[] labels =  listPreference.getEntries();
                    listPreference.setSummary(labels[index]);

                }


            } else {

                preference.setSummary(storedValue);
            }

            return true;
        }


        private void bindSummaryToValue(Preference pref) {
            pref.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(pref.getContext());
            String value = sharedPrefs.getString(pref.getKey(), "");
            onPreferenceChange(pref, value);

        }


    }


}
