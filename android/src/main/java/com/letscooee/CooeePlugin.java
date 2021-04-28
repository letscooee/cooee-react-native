package com.letscooee;

import android.app.Application;

import com.letscooee.init.ActivityLifecycleCallback;

/**
 * Will handle application state.
 *
 * @author Ashish Gaikwad
 */
public class CooeePlugin extends Application {

    public CooeePlugin() {
    }

    public void onCreate() {
        super.onCreate();
        new ActivityLifecycleCallback().register(this);
    }
}
