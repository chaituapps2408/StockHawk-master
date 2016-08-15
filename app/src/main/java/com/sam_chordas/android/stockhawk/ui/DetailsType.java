package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.support.annotation.IntDef;

import com.sam_chordas.android.stockhawk.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Chaiy on 8/9/2016.
 */
public class DetailsType {


    public static final int DETAIL_TYPE_QUOTE = 0;
    public static final int DETAIL_TYPE_STATS = 1;
    public static final int DETAIL_TYPE_DIVIDEND = 2;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DETAIL_TYPE_QUOTE, DETAIL_TYPE_STATS, DETAIL_TYPE_DIVIDEND})
    public @interface StockDetailType {
    }

    public static final int[] detailTypeSequence = new int[]{DetailsType.DETAIL_TYPE_QUOTE,
            DetailsType.DETAIL_TYPE_STATS,
            DetailsType.DETAIL_TYPE_DIVIDEND};


    public static String getStockDetailTitle(int detailType) {

        String title;
        switch (detailType) {
            case DETAIL_TYPE_QUOTE:
                title = "Quote";
                break;
            case DETAIL_TYPE_STATS:
                title = "Stats";
                break;
            case DETAIL_TYPE_DIVIDEND:
                title = "Dividend";
                break;
            default:
                title = "Quote";
                break;
        }

        return title;
    }

    public static String[] getDetailNames(int detailType, Context context) {

        String[] names;
        switch (detailType) {
            case DETAIL_TYPE_QUOTE:
                names = context.getResources().getStringArray(R.array.stock_quote_details);
                break;
            case DETAIL_TYPE_STATS:
                names = context.getResources().getStringArray(R.array.stock_stats_details);
                break;
            case DETAIL_TYPE_DIVIDEND:
                names = context.getResources().getStringArray(R.array.stock_dividend_details);
                break;
            default:
                names = context.getResources().getStringArray(R.array.stock_quote_details);
                break;
        }

        return names;
    }


}
