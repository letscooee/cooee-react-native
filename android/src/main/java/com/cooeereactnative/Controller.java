package com.cooeereactnative;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.letscooee.CooeeSDK;
import com.letscooee.init.AppController;
import com.letscooee.init.PostLaunchActivity;
import com.letscooee.models.TriggerData;
import com.letscooee.retrofit.HttpCallsHelper;
import com.letscooee.trigger.EngagementTriggerActivity;
import com.letscooee.utils.Closure;
import com.letscooee.utils.LocalStorageHelper;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Controller  extends Application implements LifecycleObserver, Application.ActivityLifecycleCallbacks{
  public static String currentScreen;
  public static boolean isBackground;
  private String packageName;
  private Date lastEnterForeground;
  private Date lastEnterBackground;
  private Handler handler = new Handler();
  private Runnable runnable;

  public Controller() {
  }
  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  public void onEnterForeground() {
    Log.d("CooeeSDK", "AppController : Foreground");
    isBackground = false;
    this.keepSessionAlive();
    this.lastEnterForeground = new Date();
    if (this.lastEnterBackground != null) {
      long backgroundDuration = (new Date()).getTime() - this.lastEnterBackground.getTime();
      if (backgroundDuration > 1800000L) {
        int duration = (int)(this.lastEnterBackground.getTime() - PostLaunchActivity.currentSessionStartTime.getTime()) / 1000;
        Map<String, String> sessionProperties = new HashMap();
        sessionProperties.put("CE Duration", duration + "");
        HttpCallsHelper.sendSessionConcludedEvent(duration);
        new PostLaunchActivity(this.getApplicationContext());
        Log.d("CooeeSDK", "After 30 min of App Background Session Concluded");
      } else {
        Map<String, String> sessionProperties = new HashMap();
        sessionProperties.put("CE Duration", String.valueOf(backgroundDuration / 1000L));
        com.letscooee.models.Event session = new com.letscooee.models.Event("CE App Foreground", sessionProperties);
        HttpCallsHelper.sendEvent(this.getApplicationContext(), session, (data) -> {
          PostLaunchActivity.createTrigger(this.getApplicationContext(), data);
        });
      }
      getFirebaseToken();

    }
  }

  private void getFirebaseToken() {
    FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
      @Override
      public void onSuccess(String s) {
        HttpCallsHelper.setFirebaseToken(s);
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
      }
    });
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  public void onEnterBackground() {
    Log.d("CooeeSDK", "AppController : Background");
    isBackground = true;
    this.handler.removeCallbacks(this.runnable);
    if (this.getApplicationContext() != null) {
      PostLaunchActivity.onSDKStateDecided.subscribe((ignored) -> {
        this.lastEnterBackground = new Date();
        String duration = (this.lastEnterBackground.getTime() - this.lastEnterForeground.getTime()) / 1000L + "";
        Map<String, String> sessionProperties = new HashMap();
        sessionProperties.put("CE Duration", duration);
        com.letscooee.models.Event session = new com.letscooee.models.Event("CE App Background", sessionProperties);
        HttpCallsHelper.sendEvent(this.getApplicationContext(), session, (Closure)null);
      });
    }
  }
  private void keepSessionAlive() {
    this.handler.postDelayed(this.runnable = new Runnable() {
      public void run() {
        Controller.this.handler.postDelayed(Controller.this.runnable, 300000L);
        HttpCallsHelper.keepAlive();
        Log.d("CooeeSDK", "Sent keep alive call");
      }
    }, 300000L);
  }

  public void onCreate() {
    super.onCreate();
    this.registerActivityLifecycleCallbacks(this);
    ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    int version = LocalStorageHelper.getInt(this.getApplicationContext(), "sdk_version", 0);
    if (version == 0) {
      this.migrate();
    }

  }

  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
    this.packageName = activity.getClass().getPackage().getName();
    currentScreen = activity.getLocalClassName();
    if (!activity.getLocalClassName().endsWith("EngagementTriggerActivity")) {
      EngagementTriggerActivity.setWindow(activity.getWindow());
    }
    getFirebaseToken();
    final TriggerData triggerData = (TriggerData)activity.getIntent().getParcelableExtra("triggerData");
    if (triggerData != null && triggerData.getId() != null) {
      (new Timer()).schedule(new TimerTask() {
        public void run() {
          PostLaunchActivity.createTrigger(Controller.this.getApplicationContext(), triggerData);
          HttpCallsHelper.sendEvent(Controller.this.getApplicationContext(), new com.letscooee.models.Event("CE Notification Clicked", new HashMap()), (Closure)null);
        }
      }, 4000L);
    }

  }

  public void onActivityStarted(@NonNull Activity activity) {
    String manualScreenName = CooeeSDK.getDefaultInstance(this.getApplicationContext()).getCurrentScreenName();
    currentScreen = manualScreenName != null && !manualScreenName.isEmpty() ? manualScreenName : activity.getLocalClassName();
  }

  public void onActivityResumed(@NonNull Activity activity) {
  }

  public void onActivityPaused(@NonNull Activity activity) {
  }

  public void onActivityStopped(@NonNull Activity activity) {
  }

  public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
  }

  public void onActivityDestroyed(@NonNull Activity activity) {
  }

  private void migrate() {
    boolean appLaunchFromOldVersion = this.getApplicationContext().getSharedPreferences("is_app_first_time_launch", 0).getBoolean("is_app_first_time_launch", true);
    String sdkTokenFromOldVersion = this.getApplicationContext().getSharedPreferences("com.letscooee.tester", 0).getString("com.letscooee.tester", "");
    Log.d("CooeeSDK", "Old value of is app launch : " + appLaunchFromOldVersion);
    Log.d("CooeeSDK", "Old value of SDK Token : " + sdkTokenFromOldVersion);
    LocalStorageHelper.putBooleanImmediately(this.getApplicationContext(), "is_first_launch", appLaunchFromOldVersion);
    LocalStorageHelper.putStringImmediately(this.getApplicationContext(), "sdk_token", sdkTokenFromOldVersion);
    LocalStorageHelper.putIntImmediately(this.getApplicationContext(), "sdk_version", 206);

    try {
      this.getApplicationContext().getSharedPreferences("is_app_first_time_launch", 0).edit().clear().commit();
      this.getApplicationContext().getSharedPreferences("com.letscooee.tester", 0).edit().clear().commit();
      PackageInfo packageInfo = this.getApplicationContext().getPackageManager().getPackageInfo(this.getApplicationContext().getPackageName(), 0);
      File dir = new File(this.getApplicationContext().getFilesDir().getPath() + "/data/" + packageInfo.packageName + "/shared_prefs/");
      boolean isAppLaunchFileDeleted = (new File(dir + "/is_app_first_time_launch.xml")).delete();
      boolean isSDKFileDeleted = (new File(dir + "/com.letscooee.tester.xml")).delete();
      Log.d("CooeeSDK", "App Launch deleted : " + isAppLaunchFileDeleted);
      Log.d("CooeeSDK", "SDK deleted : " + isSDKFileDeleted);
    } catch (Exception var7) {
      Log.e("CooeeSDK", "Could not delete the file locally");
    }

  }
}
