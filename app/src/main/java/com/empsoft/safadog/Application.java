package com.empsoft.safadog;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class reprensenting the Application, the Application establishes the connection with the Parse,
 * which is our server.
 */
public class Application extends android.app.Application {

    public static final boolean APPDEBUG = true;
    private static SharedPreferences preferences;
    private static ConfigHelper configHelper;
    public static final String APPTAG = "Safadog";
    public static String singleton_date;

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseFacebookUtils.initialize(this);

        preferences = getSharedPreferences("com.parse.les142", Context.MODE_PRIVATE);
        configHelper = new ConfigHelper();
        configHelper.fetchConfigIfNeeded();
        //Security of data
        ParseACL defaultACL = new ParseACL();
        // If you would like objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseInstallation pi = ParseInstallation.getCurrentInstallation();
        FacebookSdk.sdkInitialize(getApplicationContext());

//        if(ParseUser.getCurrentUser() != null){
//            if(singleton_date == null || singleton_date != findToday()){
//                singleton_date = findToday();
//            }
//        }
    }

    public static ConfigHelper getConfigHelper() {
        return configHelper;
    }

    //Find the current day, necessary for showing the main screen and another features
    //of the app
    public String findToday(){
        Calendar day = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(day.getTime());
    }
}
