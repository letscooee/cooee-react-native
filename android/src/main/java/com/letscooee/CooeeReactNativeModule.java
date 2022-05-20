package com.letscooee;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.letscooee.utils.Constants;
import com.letscooee.utils.CooeeCTAListener;
import com.letscooee.utils.PropertyNameException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ashish Gaikwad
 */
@ReactModule(name = CooeeReactNativeModule.NAME)
public class CooeeReactNativeModule extends ReactContextBaseJavaModule {

    public static final String NAME = "CooeeReactNative";
    CooeeSDK cooeeSDK;
    private final ReactApplicationContext reactContext;

    public CooeeReactNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);

        this.reactContext = reactContext;
        cooeeSDK = CooeeSDK.getDefaultInstance(reactContext.getBaseContext());
        cooeeSDK.setCTAListener(listener);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    /**
     * Send event using Cooee sendEvent Method
     *
     * @param eventProperties will be ReadableMap
     * @param name  will be event name
     */
    @ReactMethod
    public void sendEvent(String name, ReadableMap eventProperties, Promise promise) {
        try {
            if (eventProperties == null || eventProperties.toHashMap().isEmpty()) {
                cooeeSDK.sendEvent(name);
            } else {
                cooeeSDK.sendEvent(name, eventProperties.toHashMap());
            }

            promise.resolve("Event sent successfully");
        } catch (PropertyNameException | NullPointerException e) {
            Log.e(Constants.TAG, "Fail to send event: " + e.getMessage(), e);
            promise.reject(e);
        }
    }

    /**
     * will access UUID from Cooee SDK
     *
     * @param promise will be instance of Promise and will send UUID back
     */
    @ReactMethod
    public void getUserID(Promise promise) {
        promise.resolve(cooeeSDK.getUserID());
    }

    /**
     * Will use to send custom user properties
     *
     * @param userProfile will be ReadableMap
     */
    @ReactMethod
    public void updateUserProfile(ReadableMap userProfile, Promise promise) {
        try {
            if (userProfile == null) {
                promise.reject(new NullPointerException("userProfile is null"));
                return;
            }
            cooeeSDK.updateUserProfile(userProfile.toHashMap());

            promise.resolve("User profile updated successfully");
        } catch (PropertyNameException | NullPointerException e) {
            Log.e(Constants.TAG, "Fail to update user profile: " + e.getMessage(), e);
            promise.reject(e);
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
    }

    /**
     * Show DebugInfo screen
     */
    @ReactMethod
    public void showDebugInfo() {
        cooeeSDK.showDebugInfo();
    }

    /**
     * Sends data to {@code eventName} named listener in JS/TS
     *
     * @param reactContext will be instance of ReactContext
     * @param eventName    will be event name on which payload will be sent
     * @param payload      will be data to send
     */
    private void sendInAppClicked(ReactContext reactContext, String eventName, @Nullable WritableMap payload) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, payload);
    }

    private final CooeeCTAListener listener = new CooeeCTAListener() {
        @Override
        public void onResponse(HashMap<String, Object> payload) {
            WritableMap writableMap = Arguments.makeNativeMap(payload);
            sendInAppClicked(reactContext, "onCooeeCTAListener", writableMap);
        }
    };
}
