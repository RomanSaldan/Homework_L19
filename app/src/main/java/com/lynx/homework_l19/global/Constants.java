package com.lynx.homework_l19.global;

import com.lynx.homework_l19.R;

/**
 * Created by WORK on 21.07.2015.
 */
public final class Constants {

    public static final int     PLAY_SERVICES_RESOLUTION_REQUEST    = 9000;
    public static final String  PROPERTY_REG_ID                     = "registration_id";
    public static final String  PROPERTY_APP_VERSION                = "appVersion";
    public static final String  SENDER_ID                           = "687608994921";
    public static final String  GCM_PREFERENCES                     = "GCM_preferences";
    public static final int     NOTIFICATION_ID                     = 1234;
    public static final long[]  VIBRATION                           = {100, 200, 300, 500};
    public static final String  EMPTY_STRING                        = "";

    public static final int     CONTEXT_MENU_OPTION_DELETE          = 1;
    public static final int     CONTEXT_MENU_OPTION_DELETE_ALL      = 2;
    public static final String  CONTEXT_MENU_DELETE                 = "Delete";
    public static final String  CONTEXT_MENU_DELETE_ALL             = "Delete all";

    public static final String  EXTRA_KEY_MESSAGE       = "message";
    public static final String  EXTRA_KEY_TITLE         = "title";
    public static final String  EXTRA_KEY_SUBTITLE      = "subtitle";
    public static final String  EXTRA_KEY_TICKER        = "tickerText";
    public static final String  EXTRA_KEY_SOUND         = "sound";
    public static final String  EXTRA_KEY_VIBRATION     = "vibration";

    public static final String  SOUND_ON                = "on";
    public static final String  VIBRATION_ON            = "on";

    /*DB constants*/
    public static final String DB_NAME          = "myDB";
    public static final String DB_TABLE         = "tableNotifications";
    public static final int DB_VERSION          = 1;

    public static final String COLUMN_ID        = "_id";
    public static final String COLUMN_TITLE     = "title";
    public static final String COLUMN_SUBTITLE  = "subtitle";
    public static final String COLUMN_MESSAGE   = "message";
    public static final String COLUMN_TICKER    = "ticker";
    public static final String COLUMN_SOUND     = "sound";
    public static final String COLUMN_VIBRATE   = "vibrate";

    public static final String DB_CREATE = "CREATE TABLE " + DB_TABLE
            + "("   + COLUMN_ID         + " integer primary key autoincrement, "
                    + COLUMN_TITLE      + " text, "
                    + COLUMN_SUBTITLE   + " text, "
                    + COLUMN_MESSAGE    + " text, "
                    + COLUMN_TICKER     + " text, "
                    + COLUMN_SOUND      + " text, "
                    + COLUMN_VIBRATE    + " text"
            + ");";

    public static final String[] DB_FROM = {
            COLUMN_TICKER,
            COLUMN_TITLE,
            COLUMN_SUBTITLE,
            COLUMN_MESSAGE
    };

    public static final int[] DB_TO = {
            R.id.tvTicker_NLI,
            R.id.tvTitle_NLI,
            R.id.tvSubtitle_NLI,
            R.id.tvMessage_NLI
    };

}
