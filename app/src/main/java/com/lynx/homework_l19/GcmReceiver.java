package com.lynx.homework_l19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by WORK on 21.07.2015.
 */

/*Broadcast receiver for getting push messages */
public class GcmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.showNotification(context, intent);
    }
}
