package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.Bundle;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PHONE_STATE_PERMISSION = 1;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*// Check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE_PERMISSION);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.MODIFY_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MODIFY_PHONE_STATE}, REQUEST_PHONE_STATE_PERMISSION);
        }

        // Set up the phone state listener
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new CustomPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
*/
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                try {
                    for (MediaController mediaController : ((MediaSessionManager) getApplicationContext().getSystemService("media_session")).getActiveSessions(new ComponentName(getApplicationContext(), NotificationCall.class))) {
                        if ("com.android.server.telecom".equals(mediaController.getPackageName())) {
                            mediaController.dispatchMediaButtonEvent(new KeyEvent(1, 79));
                            return;
                        }
                    }
                } catch (SecurityException e2) {
                    e2.printStackTrace();
                }
            }
        });

        if (Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
        {} else {
            Intent i = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        //Start
        handler.postDelayed(runnable, 2000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                for (MediaController mediaController : ((MediaSessionManager) getApplicationContext().getSystemService("media_session")).getActiveSessions(new ComponentName(getApplicationContext(), NotificationCall.class))) {
                    if ("com.android.server.telecom".equals(mediaController.getPackageName())) {
                        mediaController.dispatchMediaButtonEvent(new KeyEvent(1, 79));
                        return;
                    }
                }
            } catch (SecurityException e2) {
                e2.printStackTrace();
            }
            handler.postDelayed(this, 2000);
        }
    };



/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_STATE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Proceed with your app logic here
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied. App cannot function properly.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CustomPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    answerCall();
                    break;
                default:
                    // Do nothing
                    break;
            }
        }

        private void answerCall() {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                Class<?> telephonyClass = Class.forName(telephonyManager.getClass().getName());
                java.lang.reflect.Method method = telephonyClass.getDeclaredMethod("answerRingingCall");
                method.invoke(telephonyManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/


}
