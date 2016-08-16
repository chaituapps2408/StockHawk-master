package com.sam_chordas.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by mallakr on 8/12/2016.
 */
public class StockViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = StockViewsFactory.class.getSimpleName();
    Cursor rows;
    private Context ctxt = null;
    private int appWidgetId;

    public StockViewsFactory(Context ctxt, Intent intent) {
        this.ctxt = ctxt;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "in widget onCreate");

        rows = ctxt.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.NAME, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);

    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        if (rows == null) {
            return 0;
        }

        return (rows.getCount());
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.d(TAG, "in getViewAt :" + position);

        RemoteViews row = new RemoteViews(ctxt.getPackageName(),
                R.layout.list_item_quote_widget);
        rows.moveToPosition(position);
        row.setTextViewText(R.id.stock_symbol, rows.getString(rows.getColumnIndex(QuoteColumns.SYMBOL)));
        row.setTextViewText(R.id.bid_price, rows.getString(rows.getColumnIndex(QuoteColumns.BIDPRICE)));
        row.setTextViewText(R.id.changePercentage, rows.getString(rows.getColumnIndex(QuoteColumns
                .PERCENT_CHANGE)));
        row.setTextViewText(R.id.change, rows.getString(rows.getColumnIndex(QuoteColumns
                .CHANGE)));
        if (rows.getInt(rows.getColumnIndex("is_up")) == 1) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                row.setTextColor(R.id.changePercentage, ctxt.getResources().getColor(R.color
                        .material_green_700));
                row.setTextColor(R.id.change, ctxt.getResources().getColor(R.color
                        .material_green_700));

            } else {
                row.setTextColor(R.id.changePercentage, ctxt.getResources().getColor(R.color
                        .material_green_700, null));
                row.setTextColor(R.id.change, ctxt.getResources().getColor(R.color
                        .material_green_700, null));
            }
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                row.setTextColor(R.id.changePercentage, ctxt.getResources().getColor(R.color
                        .material_red_700));
                row.setTextColor(R.id.change, ctxt.getResources().getColor(R.color
                        .material_red_700));

            } else {
                row.setTextColor(R.id.changePercentage, ctxt.getResources().getColor(R.color
                        .material_red_700, null));
                row.setTextColor(R.id.change, ctxt.getResources().getColor(R.color
                        .material_red_700, null));
            }
        }

        Intent i = new Intent();
        //Bundle extras = new Bundle();

        //extras.putString(WidgetProvider.EXTRA_WORD, rows.getString(rows.getColumnIndex
        //       (QuoteColumns.SYMBOL)));
        //i.putExtras(extras);
        row.setOnClickFillInIntent(R.id.widget_row, i);

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return (null);
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return (true);
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        if (rows != null) {
            rows.close();
        }
        Log.d(TAG, "in widget onDataSetChanged");
        rows = ctxt.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.NAME, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);

        Binder.restoreCallingIdentity(identityToken);
    }
}
