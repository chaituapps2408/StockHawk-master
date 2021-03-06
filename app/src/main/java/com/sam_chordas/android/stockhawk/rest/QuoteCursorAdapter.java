package com.sam_chordas.android.stockhawk.rest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.service.StockIntentService;
import com.sam_chordas.android.stockhawk.touch_helper.ItemTouchHelperAdapter;
import com.sam_chordas.android.stockhawk.touch_helper.ItemTouchHelperViewHolder;

/**
 * Created by sam_chordas on 10/6/15.
 * Credit to skyfishjy gist:
 * https://gist.github.com/skyfishjy/443b7448f59be978bc59
 * for the code structure
 */
public class QuoteCursorAdapter extends CursorRecyclerViewAdapter<QuoteCursorAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private Context mContext;
    private static Typeface robotoLight;
    private boolean isPercent;
    View emptyView;
    UpdateEmptyViewListener updateEmptyViewListener;

    public QuoteCursorAdapter(Context context, Cursor cursor, View emptyView, UpdateEmptyViewListener updateEmptyViewListener) {
        super(context, cursor);
        mContext = context;
        this.emptyView = emptyView;
        this.updateEmptyViewListener = updateEmptyViewListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        robotoLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_quote, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
        viewHolder.symbol.setText(cursor.getString(cursor.getColumnIndex("symbol")));
        viewHolder.symbol.setContentDescription(cursor.getString(cursor.getColumnIndex("name")));
        viewHolder.bidPrice.setText(cursor.getString(cursor.getColumnIndex("bid_price")));
        int sdk = Build.VERSION.SDK_INT;
        if (Utils.showPercent) {
            viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("percent_change")));
        } else {
            viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("change")));
        }
        int isUp = cursor.getInt(cursor.getColumnIndex("is_up"));
        if (isUp == 1) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            } else {
                viewHolder.change.setBackground(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));

            }

            viewHolder.change.setContentDescription(mContext.getString(R.string.up_by_string) + viewHolder.change.getText());

        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            } else {
                viewHolder.change.setBackground(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            }
            viewHolder.change.setContentDescription(mContext.getString(R.string.down_by_string) + viewHolder.change.getText());

        }

    }

    @Override
    public void onItemDismiss(int position) {
        Cursor c = getCursor();
        c.moveToPosition(position);
        String symbol = c.getString(c.getColumnIndex(QuoteColumns.SYMBOL));
        mContext.getContentResolver().delete(QuoteProvider.Quotes.withSymbol(symbol), null, null);
        notifyItemRemoved(position);
        Utils.updateWidget(mContext);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        Cursor cursor = super.swapCursor(newCursor);
        emptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
        if (newCursor != null && updateEmptyViewListener != null && emptyView.getVisibility() == View.VISIBLE) {
            updateEmptyViewListener.updateEmptyView();
        }
        return cursor;
    }

    public void selectItem(int selectedPosition) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder, View.OnClickListener {
        public final TextView symbol;
        public final TextView bidPrice;
        public final TextView change;

        Drawable itemBG;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBG = itemView.getBackground();
            symbol = (TextView) itemView.findViewById(R.id.stock_symbol);
            symbol.setTypeface(robotoLight);
            bidPrice = (TextView) itemView.findViewById(R.id.bid_price);
            change = (TextView) itemView.findViewById(R.id.change);
        }


        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                itemView.setBackgroundDrawable(itemBG);
            } else {
                itemView.setBackground(itemBG);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface UpdateEmptyViewListener {
        void updateEmptyView();
    }
}
