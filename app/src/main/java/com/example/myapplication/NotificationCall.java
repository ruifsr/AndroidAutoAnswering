package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationCall extends NotificationListenerService {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    static StatusBarNotification mysbn;
    Context context;

    public StatusBarNotification[] getActiveNotifications() {
        return super.getActiveNotifications();
    }

    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        mysbn = sbn;
        try {

            String packageName = sbn.getPackageName();
            Intent intent = new Intent("Msg");
            intent.putExtra("package", packageName);
            LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}