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
import com.letscooee.utils.CooeeSDKConstants;
import com.letscooee.utils.InAppNotificationClickListener;
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
    private ReactApplicationContext reactContext;

    public CooeeReactNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);

        this.reactContext = reactContext;
        cooeeSDK = CooeeSDK.getDefaultInstance(reactContext.getBaseContext());
        cooeeSDK.setInAppNotificationButtonListener(listener);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

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

    private void sendInAppClicked(ReactContext reactContext, String eventName, @Nullable WritableMap payload) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, payload);
    }

    private InAppNotificationClickListener listener = new InAppNotificationClickListener() {
        @Override
        public void onInAppButtonClick(HashMap<String, Object> payload) {
            WritableMap writableMap = Arguments.makeNativeMap(payload);
            sendInAppClicked(reactContext, "onInAppButtonClick", writableMap);
        }
    };
}
