package com.rawrick.flicklist;

import static com.rawrick.flicklist.data.tools.SettingsManager.getLoginStatus;
import static com.rawrick.flicklist.data.tools.SettingsManager.setAccountID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.rawrick.flicklist.data.account.AccountManager;

public class SplashScreenActivity extends AppCompatActivity implements AccountManager.AccountManagerListener {

    private AccountManager accountManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  ui
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
        //getWindow().setNavigationBarColor(Color.TRANSPARENT);
        // data
        if (getLoginStatus(this.getApplicationContext())) {
            accountManager = new AccountManager(this, this);
            accountManager.getAccountDataFromAPI();
        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onAccountDataUpdated() {
        String id = accountManager.getAccountData().getId();
        setAccountID(this.getApplicationContext(), id);
        intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.putExtra("name", accountManager.getAccountData().getName());
        intent.putExtra("avatar", accountManager.getAccountData().getAvatar());
        startActivity(intent);
        finish();
    }
}