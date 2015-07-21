package com.lynx.homework_l19;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.lynx.homework_l19.global.Constants;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView                lvNotifications_AM;
    private static CustomCursorAdapter     ccAdapter;
    private static DB               myDB;

    private GoogleCloudMessaging    mGcm;
    private String                  mRegId;
    private GcmPushHelper           mGcmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(myDB == null) {
            myDB = new DB(this);
            myDB.openDB();
        }

        lvNotifications_AM = (ListView) findViewById(R.id.lvNotifications_AM);
        ccAdapter = new CustomCursorAdapter(
                this,
                R.layout.notification_list_item,
                myDB.getAllData(),
                Constants.DB_FROM,
                Constants.DB_TO,
                0
        );
        lvNotifications_AM.setAdapter(ccAdapter);
        lvNotifications_AM.setOnItemClickListener(this);

        mGcmHelper = new GcmPushHelper();
        if (!mGcmHelper.checkPlayServices(this)) return;    // check availability of GoogleServices
        mGcm = GoogleCloudMessaging.getInstance(this);
        mRegId = mGcmHelper.getRegistrationId(this);    // get device register ID
        if (mRegId.isEmpty()) {
            mGcmHelper.registerGcmAsync(this, mGcm);    // or register it
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.closeDB();
    }

    /*Build and display notification from passed intent*/
    public static void showNotification(Context context, final Intent intent) {
        String message      = intent.getStringExtra("message");
        String title        = intent.getStringExtra("title");
        String subtitle     = intent.getStringExtra("subtitle");
        String tickerText   = intent.getStringExtra("tickerText");
        String sound        = intent.getStringExtra("sound");
        String vibration    = intent.getStringExtra("vibration");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_cloud)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);

        if(sound.equalsIgnoreCase("on")) builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        if(vibration.equalsIgnoreCase("on")) builder.setVibrate(new long[] {100, 200, 300, 500});

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
        myDB.addContact(title, subtitle, message, tickerText, sound, vibration);
        ccAdapter.swapCursor(myDB.getAllData());
        ccAdapter.notifyDataSetChanged();
    }

    /*Overloaded*/
    public void showNotification(String _ticker, String _title, String _subtitle, String _message, String _sound, String _vibration) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_cloud)
                .setContentTitle(_title)
                .setSubText(_subtitle)
                .setContentText(_message)
                .setTicker(_ticker)
                .setAutoCancel(true);

        if(_sound.equalsIgnoreCase("on")) builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        if(_vibration.equalsIgnoreCase("on")) builder.setVibrate(new long[] {100, 200, 300, 500});

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor notifCursor = myDB.getNotificationById((int)id);
        notifCursor.moveToFirst();
        String ticker = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_TICKER));
        String title = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_TITLE));
        String subtitle = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_SUBTITLE));
        String message = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_MESSAGE));
        String sound = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_SOUND));
        String vibration = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_VIBRATE));
        showNotification(ticker, title, subtitle, message, sound, vibration);
    }
}
