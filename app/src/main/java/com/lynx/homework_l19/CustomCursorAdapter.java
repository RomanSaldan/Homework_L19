package com.lynx.homework_l19;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lynx.homework_l19.global.Constants;

/**
 * Created by WORK on 21.07.2015.
 */

/*Custom SimpleCursorAdapter that bond notifications data from cursor to views of contact layout*/

public class CustomCursorAdapter extends SimpleCursorAdapter {

    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    /*Inflate new view*/
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.notification_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvTicker_NLI = (TextView) view.findViewById(R.id.tvTicker_NLI);
        TextView tvTitle_NLI = (TextView) view.findViewById(R.id.tvTitle_NLI);
        TextView tvSubtitle_NLI = (TextView) view.findViewById(R.id.tvSubtitle_NLI);
        TextView tvMessage_NLI = (TextView) view.findViewById(R.id.tvMessage_NLI);

        String ticker = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TICKER));
        String title = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TITLE));
        String subtitle = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_SUBTITLE));
        String message = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MESSAGE));

        tvTicker_NLI.setText(ticker);
        tvTitle_NLI.setText(title);
        tvSubtitle_NLI.setText(subtitle);
        tvMessage_NLI.setText(message);
    }
}
