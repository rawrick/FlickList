package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.tools.SettingsManager.getLoginStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if (getLoginStatus(this.getApplicationContext())) {
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}