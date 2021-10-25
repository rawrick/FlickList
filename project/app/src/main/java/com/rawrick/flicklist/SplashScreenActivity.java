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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // data
        accountManager = new AccountManager(this, this);
        accountManager.getAccountDataFromAPI();
        //  ui
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
        //getWindow().setNavigationBarColor(Color.TRANSPARENT);

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
        Log.d("accid", "id: " + id);
        Intent intent;
        if (getLoginStatus(this.getApplicationContext())) {
            intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            intent.putExtra("name", accountManager.getAccountData().getName());
            intent.putExtra("avatar", accountManager.getAccountData().getAvatar());
        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();


    }

}