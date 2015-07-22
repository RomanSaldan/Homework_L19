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

/*Custom SimpleCursorAdapter that bond notifications data from cursor to views of contact list*/

public final class CustomCursorAdapter extends SimpleCursorAdapter {

    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    private static class ViewHolder {
        TextView tvTicker_NLI;
        TextView tvTitle_NLI;
        TextView tvSubtitle_NLI;
        TextView tvMessage_NLI;
    }

    /*Inflate new view*/
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.notification_list_item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.tvTicker_NLI       = (TextView) rowView.findViewById(R.id.tvTicker_NLI);
        holder.tvTitle_NLI        = (TextView) rowView.findViewById(R.id.tvTitle_NLI);
        holder.tvSubtitle_NLI     = (TextView) rowView.findViewById(R.id.tvSubtitle_NLI);
        holder.tvMessage_NLI      = (TextView) rowView.findViewById(R.id.tvMessage_NLI);
        rowView.setTag(holder);
        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String ticker       = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TICKER));
        String title        = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TITLE));
        String subtitle     = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_SUBTITLE));
        String message      = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_MESSAGE));

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.tvTicker_NLI    .setText(ticker);
        holder.tvTitle_NLI     .setText(title);
        holder.tvSubtitle_NLI  .setText(subtitle);
        holder.tvMessage_NLI   .setText(message);
    }
}
