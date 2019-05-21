package com.basharkablan.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

public class PrefsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_save)));
        findPreference(getString(R.string.pref_key_clear)).setOnPreferenceClickListener(this);
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getBoolean(preference.getKey(), false));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_key_clear))) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            boolean save = sharedPref.getBoolean(getString(R.string.pref_key_save), false);
            sharedPref.edit().clear().putBoolean(getString(R.string.pref_key_save), save).apply();
            Toast.makeText(preference.getContext(), " Cleared Data", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}


