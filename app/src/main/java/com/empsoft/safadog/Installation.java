package com.empsoft.safadog;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.AlarmManager;
import android.app.Notification;
import android.util.Log;
import com.parse.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigofarias on 10/17/15.
 */

/**
 * Installation is created by Parse, and informs to the server that the application is already
 * installed.
 * Subscribe the user in the general broadcast channel, that in the future can be used to send
 * push notifications.
 */
public class Installation {
    public Installation(){

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

    public void install(){
        ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
    }

}
