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

    public CustomCursorAdapter(Context _context, int _layout, Cursor _c, String[] _from, int[] _to, int _flags) {
        super(_context, _layout, _c, _from, _to, _flags);
    }

    /*ViewHolder nested class*/
    private static class ViewHolder {
        TextView    tvTicker_NLI;
        TextView    tvTitle_NLI;
        TextView    tvSubtitle_NLI;
        TextView    tvMessage_NLI;
    }

    /*Inflate new view, initialize ViewHolder and set it to this view*/
    @Override
    public View newView(Context _context, Cursor _cursor, ViewGroup _parent) {
        View rowView = LayoutInflater.from(_context).inflate(R.layout.notification_list_item, _parent, false);
        ViewHolder holder = new ViewHolder();
        holder.tvTicker_NLI       = (TextView) rowView.findViewById(R.id.tvTicker_NLI);
        holder.tvTitle_NLI        = (TextView) rowView.findViewById(R.id.tvTitle_NLI);
        holder.tvSubtitle_NLI     = (TextView) rowView.findViewById(R.id.tvSubtitle_NLI);
        holder.tvMessage_NLI      = (TextView) rowView.findViewById(R.id.tvMessage_NLI);
        rowView.setTag(holder);
        return rowView;
    }

    /*Set data retrieved from DB using ViewHolder*/
    @Override
    public void bindView(View _view, Context _context, Cursor _cursor) {

        String ticker       = _cursor.getString(_cursor.getColumnIndex(Constants.COLUMN_TICKER));
        String title        = _cursor.getString(_cursor.getColumnIndex(Constants.COLUMN_TITLE));
        String subtitle     = _cursor.getString(_cursor.getColumnIndex(Constants.COLUMN_SUBTITLE));
        String message      = _cursor.getString(_cursor.getColumnIndex(Constants.COLUMN_MESSAGE));

        ViewHolder holder = (ViewHolder) _view.getTag();

        holder.tvTicker_NLI    .setText(ticker);
        holder.tvTitle_NLI     .setText(title);
        holder.tvSubtitle_NLI  .setText(subtitle);
        holder.tvMessage_NLI   .setText(message);
    }
}
