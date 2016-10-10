package com.empsoft.safadog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;
import com.parse.ParseUser;


/**
 * Launch Activity, basically checks if there is as logged user or not
 * If logged launch the mainActivity, else launch the welcome activity.
 */
public class DispatchActivity extends AppCompatActivity {


    public DispatchActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
