package com.lynx.homework_l19;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.lynx.homework_l19.global.Constants;


public final class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private static CustomCursorAdapter      ccAdapter;
    private static DB                       myDB;

    private ListView                        lvNotifications_AM;

    private GoogleCloudMessaging            mGcm;
    private String                          mRegId;
    private GcmPushHelper                   mGcmHelper;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareContent();
        prepareGCM();
    }

    /*Initialize DB and ListView for display DB data*/
    private final void prepareContent() {
        if(myDB == null) {
            myDB = new DB(this);
            myDB.openDB();
        } else
            myDB.openDB();

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
        registerForContextMenu(lvNotifications_AM);
    }

    /*Prepare GCM for use on this device*/
    private final void prepareGCM() {
        mGcmHelper = new GcmPushHelper();
        if (!mGcmHelper.checkPlayServices(this)) return;    // check availability of GoogleServices
        mGcm    = GoogleCloudMessaging.getInstance(this);
        mRegId  = mGcmHelper.getRegistrationId(this);       // get device register ID
        if (mRegId.isEmpty()) {
            mGcmHelper.registerGcmAsync(this, mGcm);        // or register it
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.closeDB();
    }

    /*Build and display notification from passed intent*/
    public static void showNotification(Context _context, final Intent _intent) {
        String message      = _intent.getStringExtra(Constants.EXTRA_KEY_MESSAGE);
        String title        = _intent.getStringExtra(Constants.EXTRA_KEY_TITLE);
        String subtitle     = _intent.getStringExtra(Constants.EXTRA_KEY_SUBTITLE);
        String tickerText   = _intent.getStringExtra(Constants.EXTRA_KEY_TICKER);
        String sound        = _intent.getStringExtra(Constants.EXTRA_KEY_SOUND);
        String vibration    = _intent.getStringExtra(Constants.EXTRA_KEY_VIBRATION);

        final PendingIntent pIntent
                = PendingIntent.getActivity(_context, Constants.PI_REQUEST_CODE, new Intent(_context, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context)
                .setSmallIcon(R.drawable.icon_cloud)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        if(sound.equalsIgnoreCase(Constants.SOUND_ON)) builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        if(vibration.equalsIgnoreCase(Constants.VIBRATION_ON)) builder.setVibrate(Constants.VIBRATION);

        NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());

        if(myDB == null) {  // initialize DB and adapter if application is offline
            myDB = new DB(_context);
            myDB.openDB();
            ccAdapter = new CustomCursorAdapter(
                    _context,
                    R.layout.notification_list_item,
                    myDB.getAllData(),
                    Constants.DB_FROM,
                    Constants.DB_TO,
                    0
            );
        }
        myDB.addContact(title, subtitle, message, tickerText, sound, vibration);
        ccAdapter.swapCursor(myDB.getAllData());
        ccAdapter.notifyDataSetChanged();
    }

    /*Overloaded. For display existing notifications*/
    public void showNotification(String _ticker, String _title, String _subtitle, String _message, String _sound, String _vibration) {

        final PendingIntent pIntent
                = PendingIntent.getActivity(this, Constants.PI_REQUEST_CODE, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_cloud)
                .setContentTitle(_title)
                .setSubText(_subtitle)
                .setContentText(_message)
                .setTicker(_ticker)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        if(_sound.equalsIgnoreCase(Constants.SOUND_ON)) builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        if(_vibration.equalsIgnoreCase(Constants.VIBRATION_ON)) builder.setVibrate(Constants.VIBRATION);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
    }

    /*Display clicked notification*/
    @Override
    public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
        Cursor notifCursor = myDB.getNotificationById((int)_id);
        notifCursor.moveToFirst();

        String ticker       = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_TICKER));
        String title        = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_TITLE));
        String subtitle     = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_SUBTITLE));
        String message      = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_MESSAGE));
        String sound        = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_SOUND));
        String vibration    = notifCursor.getString(notifCursor.getColumnIndex(Constants.COLUMN_VIBRATE));

        showNotification(ticker, title, subtitle, message, sound, vibration);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.lvNotifications_AM) {
            menu.setHeaderTitle(getString(R.string.context_menu_header));
            menu.add(0, Constants.CONTEXT_MENU_OPTION_DELETE, 1, Constants.CONTEXT_MENU_DELETE);
            menu.add(0, Constants.CONTEXT_MENU_OPTION_DELETE_ALL, 2, Constants.CONTEXT_MENU_DELETE_ALL);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case Constants.CONTEXT_MENU_OPTION_DELETE:          // delete
                myDB.deleteNotificationById((int)menuInfo.id);
                ccAdapter.swapCursor(myDB.getAllData());
                ccAdapter.notifyDataSetChanged();
                break;
            case Constants.CONTEXT_MENU_OPTION_DELETE_ALL:      // delete all
                myDB.dropDB();
                ccAdapter.swapCursor(myDB.getAllData());
                ccAdapter.notifyDataSetChanged();
                break;
        }
        return true;
    }
}
