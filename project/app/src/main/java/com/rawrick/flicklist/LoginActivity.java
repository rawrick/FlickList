package com.rawrick.flicklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.rawrick.flicklist.data.util.SettingsManager.setLoginProgress;
import static com.rawrick.flicklist.data.util.SettingsManager.setLoginStatus;
import static com.rawrick.flicklist.data.util.SettingsManager.setSessionID;
import static com.rawrick.flicklist.data.api.APIRequest.token;

import com.rawrick.flicklist.data.login.LoginManager;

public class LoginActivity extends AppCompatActivity implements LoginManager.LoginTokenListener, LoginManager.LoginSessionListener, LoginManager.LoginGuestSessionListener {

    private LoginManager loginManager;
    private String guestSessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initUI();
    }

    @Override
    protected void onResume() {
        //token = loginManager.getToken();
        Log.d("FlickListApp", "login token: " + token);
        if (token != null) {
            loginManager.getSessionIDFromAPI();
        }
        super.onResume();
    }

    private void initData() {
        loginManager = new LoginManager(this, this, this, this);
    }

    private void initUI() {
        Button sessionCreation = findViewById(R.id.login_generate_request_token);
        sessionCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginManager.getTokenFromAPI();
            }
        });
        Button guestSessionCreation = findViewById(R.id.login_generate_guest_session);
        guestSessionCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginManager.getGuestSessionFromAPI();
            }
        });
        Button devLogin = findViewById(R.id.login_build_data);
        Intent intent = new Intent(this, MainActivity.class);
        devLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSessionID(getApplicationContext(), "d6dccfebe0d1bf087a8fd90f3113d1b7383947b0");
                setLoginProgress(getApplicationContext(), false);
                setLoginStatus(getApplicationContext(), true);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onTokenCreated() {
        token = loginManager.getToken();
        Log.d("FlickListApp", "token: " + loginManager.getToken());
        setLoginProgress(this, true);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(loginManager.getAuthenticationURL(token)));
        startActivity(intent);
    }

    @Override
    public void onSessionCreated() {
        Log.d("FlickListApp", "sessionId: " + loginManager.getSessionID());
        setSessionID(this, loginManager.getSessionID());
        setLoginProgress(this, false);
        setLoginStatus(this, true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onGuestSessionCreated() {
        // TODO check if creation successful
        Log.d("FlickListApp", "guest sessionID: " + loginManager.getGuestSessionID());
        setSessionID(this, loginManager.getGuestSessionID());
        setLoginStatus(this, true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}