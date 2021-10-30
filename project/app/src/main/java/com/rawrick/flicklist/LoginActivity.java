package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.api.APIRequest.guestSessionID;
import static com.rawrick.flicklist.data.api.APIRequest.sessionID;
import static com.rawrick.flicklist.data.api.APIRequest.token;
import static com.rawrick.flicklist.data.util.SettingsManager.getSessionID;
import static com.rawrick.flicklist.data.util.SettingsManager.getToken;
import static com.rawrick.flicklist.data.util.SettingsManager.setLoginStatus;
import static com.rawrick.flicklist.data.util.SettingsManager.setPreferenceAPIkey;
import static com.rawrick.flicklist.data.util.SettingsManager.setSessionID;
import static com.rawrick.flicklist.data.util.SettingsManager.setToken;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rawrick.flicklist.data.api.login.LoginManager;

public class LoginActivity extends AppCompatActivity implements LoginManager.LoginTokenListener, LoginManager.LoginSessionListener, LoginManager.LoginGuestSessionListener {

    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initUI();
    }

    @Override
    protected void onResume() {
        token = getToken(this);
        Log.d("FlickListApp", "login token: " + token);
        if (!token.equals("")) {
            Log.d("FlickListApp", "getSessionID");
            //loginManager.getSessionIDFromAPI();
        }
        super.onResume();
    }

    private void initData() {
        loginManager = new LoginManager(this, this, this, this);
    }

    private void initUI() {
        setupFAB();
        Button sessionCreation = findViewById(R.id.login_generate_request_token);
        sessionCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginManager.getTokenFromAPI();
            }
        });
        Button guestSessionCreation = findViewById(R.id.login_generate_guest_session);
        guestSessionCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginManager.getGuestSessionFromAPI();
            }
        });
        Button devLogin = findViewById(R.id.login_build_data);
        Intent intentDev = new Intent(LoginActivity.this, SplashScreenActivity.class);
        devLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoginStatus(getApplicationContext(), true);
                startActivity(intentDev);
                finish();
            }
        });
    }

    private void setupFAB() {
        FloatingActionButton shopButton = findViewById(R.id.login_settings);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                intent.putExtra("activity_title", "Settings");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTokenCreated() {
        token = loginManager.getToken();
        setToken(this, token);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(loginManager.getAuthenticationURL(token)));
        startActivity(intent);
    }

    @Override
    public void onSessionCreated() {
        sessionID = loginManager.getSessionID();
        setSessionID(this, sessionID);
        setLoginStatus(this, true);
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onGuestSessionCreated() {
        guestSessionID = loginManager.getGuestSessionID();
        setSessionID(this, guestSessionID);
        setLoginStatus(this, true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}