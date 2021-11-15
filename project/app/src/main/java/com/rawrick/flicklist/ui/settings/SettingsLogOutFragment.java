package com.rawrick.flicklist.ui.settings;


import static com.rawrick.flicklist.data.util.SettingsManager.setLoginStatus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.rawrick.flicklist.LoginActivity;


// Fragment that asks for confirmation when user tries to reset saved data
public class SettingsLogOutFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Log Out")
                .setMessage("Do you want to log out?")
                // not sure if necessary
                .setCancelable(true)
                // creates "Yes" button and clears preferences when clicked
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // resets preferences
                        //SettingsManager.resetSharedPreferences(getActivity());
                        // clears database tables
                        // TODO delete session
                        setLoginStatus(getActivity(), false);
                        // redirects user to home screen
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        // clears backstack
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                // creates "Cancel" button and closes alertbox when clicked
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.show();
    }
}