package com.empsoft.safadog;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.appevents.AppEventsLogger;
import com.parse.ParseObject;

/**
 * MainActivity that is performed right after the login
 */

public class MainActivity extends AppCompatActivity {

    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        return true;
    }

    /**
     * On the start the activity calls the findTodayHistoric, that will make the server queries, to
     * populate the content of the fragments.
     */
    @Override
    public void onStart(){
        super.onStart();
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
