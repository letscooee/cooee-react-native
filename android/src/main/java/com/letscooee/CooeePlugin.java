package com.letscooee;

import android.app.Application;

import com.letscooee.init.ActivityLifecycleCallback;

/**
 * @author Ashish Gaikwad Created on 19/Apr/2021
 * <p>
 * Will handle application state
 */
public class CooeePlugin extends Application {

    public CooeePlugin() {
    }

    public void onCreate() {
        super.onCreate();
        new ActivityLifecycleCallback().register(this);
    }

}
