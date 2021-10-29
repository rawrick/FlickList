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

import com.rawrick.flicklist.data.login.LoginManager;

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
        if (!token.equals("null")) {
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
        Intent intent = new Intent(this, SplashScreenActivity.class);
        devLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSessionID(getApplicationContext(), getSessionID(getApplicationContext()));
                setLoginStatus(getApplicationContext(), true);
                startActivity(intent);
                finish();
            }
        });
        EditText apiEditText = findViewById(R.id.api_edit_text);
        apiEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setPreferenceAPIkey(getApplicationContext(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditText sessionidEditText = findViewById(R.id.sessionid_edit_text);
        sessionidEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setSessionID(getApplicationContext(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
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