package com.lynx.homework_l19;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.lynx.homework_l19.global.Constants;


public class MainActivity extends Activity {

    private GoogleCloudMessaging mGcm;
    private String mRegId;
    private GcmPushHelper mGcmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGcmHelper = new GcmPushHelper();

        if (!mGcmHelper.checkPlayServices(this)) return;    // check availability of GoogleServices

        mGcm = GoogleCloudMessaging.getInstance(this);
        mRegId = mGcmHelper.getRegistrationId(this);    // get device register ID
        if (mRegId.isEmpty()) {
            mGcmHelper.registerGcmAsync(this, mGcm);    // or register it
        }
    }

    /*Build and display notification from passed intent*/
    public static void showNotification(Context context, final Intent intent) {
        String message      = intent.getStringExtra("message");
        String title        = intent.getStringExtra("title");
        String subtitle     = intent.getStringExtra("subtitle");
        String tickerText   = intent.getStringExtra("tickerText");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
    }

    /*Get device ID (for test)*/
    public void getDeviceId(View v) {
        Toast.makeText(this, "DEVICE ID: " + mGcmHelper.getRegistrationId(this), Toast.LENGTH_LONG).show();
        Log.d("myLogs", "Device registration ID: \n" + mGcmHelper.getRegistrationId(this));
    }
}
