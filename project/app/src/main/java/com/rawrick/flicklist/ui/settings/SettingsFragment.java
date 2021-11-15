package com.rawrick.flicklist.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.rawrick.flicklist.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        showSettingsResetDataConfirmation();
    }

    // displays confirmation dialog when clicked
    public void showSettingsResetDataConfirmation() {
        Preference resetDataPreference = findPreference("log_out");
        resetDataPreference.setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        DialogFragment newFragment = new SettingsLogOutFragment();
                        newFragment.show(getParentFragmentManager(), "logout");
                        return false;
                    }
                }
        );
    }
}