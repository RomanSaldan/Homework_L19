package com.lynx.homework_l19;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lynx.homework_l19.global.Constants;

/**
 * Created by WORK on 21.07.2015.
 */
public final class DB {

    private final Context   mCtx;
    private DBHelper        mDBHelper;
    private SQLiteDatabase  mDB;

    public DB(Context mCtx) {
        this.mCtx = mCtx;
    }

    /*Open DB connection*/
    public void openDB() {
        mDBHelper = new DBHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
    }

    /*Close DB connection*/
    public void closeDB() {
        if(mDBHelper != null) mDBHelper.close();
    }

    /*Retrieve all data from DB*/
    public Cursor getAllData() {
        return mDB.query(Constants.DB_TABLE, null, null, null, null, null, null);
    }

    /*Get cursor with notification information by ID*/
    public Cursor getNotificationById(int _id) {
        String selection = Constants.COLUMN_ID + " = " + _id;
        return mDB.query(
                Constants.DB_TABLE,
                null,
                selection,
                null, null, null, null
        );
    }

    /*Add new notification to DB*/
    public void addContact(String _title, String _subtitle, String _message, String _ticker, String _sound, String _vibrate) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_TITLE, _title);
        cv.put(Constants.COLUMN_SUBTITLE, _subtitle);
        cv.put(Constants.COLUMN_MESSAGE, _message);
        cv.put(Constants.COLUMN_TICKER, _ticker);
        cv.put(Constants.COLUMN_SOUND, _sound);
        cv.put(Constants.COLUMN_VIBRATE, _vibrate);
        mDB.insert(Constants.DB_TABLE, null, cv);
    }

    /*Drop existing DB*/
    public void dropDB() {
        mDB.delete(Constants.DB_TABLE, null, null);
    }

    /*Util class for serving DB*/
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Constants.DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
