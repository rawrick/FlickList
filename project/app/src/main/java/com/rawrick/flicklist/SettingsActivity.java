package com.rawrick.flicklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.rawrick.flicklist.ui.settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        MainActivity.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        setupNavigationToolbar();
        setupSettingsFragment();
    }

    // sets up Navigation Toolbar
    private void setupNavigationToolbar() {
        //replaces Actionbar with custom Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // sets title in Toolbar
        setTitle(getIntent().getExtras().getString("activity_title"));
        // always display backwards-arrow icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupSettingsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_fragment_container_view, new SettingsFragment())
                .commit();
    }

}