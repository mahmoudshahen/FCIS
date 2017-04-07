package com.example.mahmoudshahen.fcis;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mahmoud shahen on 07/04/2017.
 */

public class Start extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
