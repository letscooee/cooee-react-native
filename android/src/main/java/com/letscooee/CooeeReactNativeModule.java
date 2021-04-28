package com.letscooee;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.google.gson.Gson;
import com.letscooee.utils.CooeeSDKConstants;
import com.letscooee.utils.PropertyNameException;

import java.util.Map;

/**
 * @author Ashish Gaikwad Created on 19/Apr/2021
 * <p>
 * Will handle call from fornd end
 */
@ReactModule(name = CooeeReactNativeModule.NAME)
public class CooeeReactNativeModule extends ReactContextBaseJavaModule {
    public static final String NAME = "CooeeReactNative";
    CooeeSDK cooeeSDK;

    public CooeeReactNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        cooeeSDK = CooeeSDK.getDefaultInstance(reactContext.getBaseContext());
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
        promise.resolve(a * b);
    }

    public static native int nativeMultiply(int a, int b);

    /**
     * Send event using Cooee sendEvent Method
     *
     * @param event will be ReadableMap
     * @param name  will be event name
     */
    @ReactMethod
    public void sendEvent(String name, ReadableMap event) {
        try {
            Map map = new Gson().fromJson(event.toString(), Map.class);
            cooeeSDK.sendEvent(name, ((Map) map.get("NativeMap")));
        } catch (PropertyNameException e) {
            Log.e(CooeeSDKConstants.LOG_PREFIX, "sendEvent: " + e.getMessage(), e);
        }
    }

    /**
     * will access UUID from Cooee SDK
     *
     * @param promise will be instance of Promise and will send UUID back
     */
    @ReactMethod
    public void getUUID(Promise promise) {
        promise.resolve(cooeeSDK.getUUID());
    }

    /**
     * Will use to send custom user properties
     *
     * @param userProperties will be ReadableMap
     */
    @ReactMethod
    public void sendUserProperty(ReadableMap userProperties) {
        try {
            Log.i(CooeeSDKConstants.LOG_PREFIX, "sendUserProperty: " + userProperties.toString());
            Map map = new Gson().fromJson(userProperties.toString(), Map.class);
            cooeeSDK.updateUserProperties(map);
        } catch (PropertyNameException e) {
            Log.e(CooeeSDKConstants.LOG_PREFIX, "sendUserProperty: " + e.getMessage(), e);
        }
    }

    /**
     * will send user data
     *
     * @param userData will be ReadableMap
     */
    @ReactMethod
    public void updateUserData(ReadableMap userData) {
        try {
            Map map = new Gson().fromJson(userData.toString(), Map.class);
            cooeeSDK.updateUserData(((Map) map.get("NativeMap")));
        } catch (PropertyNameException e) {
            Log.e(CooeeSDKConstants.LOG_PREFIX, "updateUserData: " + e.getMessage(), e);
        }
    }

    /**
     * will use to set user current screen
     *
     * @param screenName will be screen name
     */
    @ReactMethod
    public void updateScreenName(String screenName) {
        cooeeSDK.setCurrentScreen(screenName);
        Log.i(CooeeSDKConstants.LOG_PREFIX, "updateScreenName: Screen Name Set");

    }
}
