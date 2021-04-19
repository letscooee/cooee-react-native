package com.cooeereactnative;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.google.gson.Gson;
import com.letscooee.CooeeSDK;
import com.letscooee.utils.CooeeSDKConstants;
import com.letscooee.utils.PropertyNameException;

import java.util.Map;

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

  @ReactMethod
  public void sendEvent(String name, ReadableMap event) {
    try {
      Map map = new Gson().fromJson(event.toString(), Map.class);
      cooeeSDK.sendEvent(name, ((Map) map.get("NativeMap")));
      Log.i(CooeeSDKConstants.LOG_PREFIX, "sendEvent: Event Sent");
    } catch (PropertyNameException e) {
      Log.e(CooeeSDKConstants.LOG_PREFIX, "sendEvent: " + e.getMessage(), e);
    }
  }

  @ReactMethod
  public void getUUID(Promise promise) {
    promise.resolve(cooeeSDK.getUUID());
  }

  @ReactMethod
  public void sendUserProperty(ReadableMap userProperties) {
    try {
      Log.i(CooeeSDKConstants.LOG_PREFIX, "sendUserProperty: " + userProperties.toString());
      Map map = new Gson().fromJson(userProperties.toString(), Map.class);

      cooeeSDK.updateUserProperties(map);
      Log.i(CooeeSDKConstants.LOG_PREFIX, "sendUserProperty: Property Sent");
    } catch (PropertyNameException e) {
      Log.e(CooeeSDKConstants.LOG_PREFIX, "sendUserProperty: " + e.getMessage(), e);
    }
  }

  @ReactMethod
  public void updateUserData(ReadableMap userData) {
    try {
      Log.i(CooeeSDKConstants.LOG_PREFIX, "updateUserData: " + userData.toString());
      Map map = new Gson().fromJson(userData.toString(), Map.class);
      cooeeSDK.updateUserData(((Map) map.get("NativeMap")));
      Log.i(CooeeSDKConstants.LOG_PREFIX, "updateUserData: Property Sent");
    } catch (PropertyNameException e) {
      Log.e(CooeeSDKConstants.LOG_PREFIX, "updateUserData: " + e.getMessage(), e);
    }
  }

  @ReactMethod
  public void updateScreenName(String screenName) {
    cooeeSDK.setCurrentScreen(screenName);
    Log.i(CooeeSDKConstants.LOG_PREFIX, "updateScreenName: Screen Name Set");

  }
}
