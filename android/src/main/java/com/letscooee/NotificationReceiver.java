package com.letscooee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import com.letscooee.services.CooeeFirebaseMessagingService;
import com.letscooee.utils.Constants;

import java.util.concurrent.Executors;

/**
 * Handle received push notification from firebase and set flag in {@link CooeeFirebaseMessagingService}
 * to prevent displaying same notification twice.
 *
 * @author Ashish Gaikwad 13/07/22
 * @since 1.3.2
 */
public class NotificationReceiver extends BroadcastReceiver {

    public NotificationReceiver() {
        CooeeFirebaseMessagingService.setMessageDelivered();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteMessage remoteMessage = new RemoteMessage(intent.getExtras());
        Log.d(Constants.TAG, "Notification received");

        Executors.newSingleThreadExecutor().execute(() -> {
            new CooeeFirebaseMessagingService(context).handleRemoteMessage(remoteMessage);
        });
    }
}
