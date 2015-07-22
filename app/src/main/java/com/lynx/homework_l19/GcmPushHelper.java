package com.lynx.homework_l19;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.lynx.homework_l19.global.Constants;

import java.io.IOException;

/**
 * created by work on 21.07.2015.
 */

/*Util class for serving GCM and store settings info to SharedPreferences*/
public final class GcmPushHelper {

    /*Check if GooglePlayServices is available on current device and offer user download it is possible*/
    public final boolean checkPlayServices(Activity _activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(_activity);
        if(resultCode != ConnectionResult.SUCCESS) {
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                GooglePlayServicesUtil.getErrorDialog(resultCode, _activity, Constants.PLAY_SERVICES_RESOLUTION_REQUEST);
            else Toast.makeText(_activity, _activity.getString(R.string.toast_err_device_not_supported), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*Get registration ID for current device from SharedPreferences
    * return empty string if there no one or app version changed*/
    public final String getRegistrationId(Context _context) {
        final SharedPreferences pref = getGcmPreferences(_context);
        String regID = pref.getString(Constants.PROPERTY_REG_ID, Constants.EMPTY_STRING);
        if(regID.isEmpty()) return Constants.EMPTY_STRING;

        int registeredVersion = pref.getInt(Constants.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(_context);
        if(registeredVersion != currentVersion) return Constants.EMPTY_STRING;
        return regID;
    }

    /*Get GCM SharedPreferences from passed context*/
    public final SharedPreferences getGcmPreferences(Context _context) {
        return _context.getSharedPreferences(Constants.GCM_PREFERENCES, Context.MODE_PRIVATE);
    }

    /*Get app version from package*/
    public final int getAppVersion(Context _context) {
        try {
            PackageInfo packageInfo = _context.getPackageManager()
                    .getPackageInfo(_context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /*Asynchronously register this device for get push messages from server*/
    public final void registerGcmAsync(final Context _context, final GoogleCloudMessaging _gcm) {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String regId = _gcm.register(Constants.SENDER_ID);
                    storeRegistrationId(_context, regId);   // save registration data & app version to shared preferences
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(null, null, null);
    }

    /*Save registration ID and app version to shared preferences of passed context*/
    public final void storeRegistrationId(Context _context, String _regId) {
        final SharedPreferences prefs = getGcmPreferences(_context);
        int appVersion = getAppVersion(_context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PROPERTY_REG_ID, _regId);
        editor.putInt(Constants.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

}
