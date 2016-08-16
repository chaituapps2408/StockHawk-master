package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.support.annotation.IntDef;

import com.sam_chordas.android.stockhawk.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

import yahoofinance.histquotes.Interval;

/**
 * Created by Chaiy on 8/9/2016.
 */
public class DataInterval {


    public static final int INTERVAL_TYPE_5_DAYS = 0;
    public static final int INTERVAL_TYPE_3_MONTHS = 1;
    public static final int INTERVAL_TYPE_6_MONTHS = 2;
    public static final int INTERVAL_TYPE_1_YEARS = 3;
    public static final int INTERVAL_TYPE_2_YEARS = 4;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INTERVAL_TYPE_5_DAYS, INTERVAL_TYPE_3_MONTHS, INTERVAL_TYPE_6_MONTHS, INTERVAL_TYPE_1_YEARS, INTERVAL_TYPE_2_YEARS})
    public @interface IntervalType {
    }

    @DataInterval.IntervalType
    public static final int[] intervalTypeSequence = new int[]{DataInterval.INTERVAL_TYPE_5_DAYS,
            DataInterval.INTERVAL_TYPE_3_MONTHS,
            DataInterval.INTERVAL_TYPE_6_MONTHS,
            DataInterval.INTERVAL_TYPE_1_YEARS,
            DataInterval.INTERVAL_TYPE_2_YEARS};


    public static Calendar getFrom(int intervalType) {
        Calendar to = Calendar.getInstance();
        switch (intervalType) {
            case INTERVAL_TYPE_5_DAYS:
                to.add(Calendar.DAY_OF_MONTH, -5);
                break;
            case INTERVAL_TYPE_3_MONTHS:
                to.add(Calendar.MONTH, -3);
                break;
            case INTERVAL_TYPE_6_MONTHS:
                to.add(Calendar.MONTH, -6);
                break;
            case INTERVAL_TYPE_1_YEARS:
                to.add(Calendar.YEAR, -1);
                break;
            case INTERVAL_TYPE_2_YEARS:
                to.add(Calendar.YEAR, -2);
                break;
            default:
                to.add(Calendar.DAY_OF_MONTH, -5);
                break;
        }

        return to;
    }


    public static Interval getInterval(int intervalType) {
        Interval interval;
        switch (intervalType) {
            case INTERVAL_TYPE_5_DAYS:
                interval = Interval.DAILY;
                break;
            case INTERVAL_TYPE_3_MONTHS:
            case INTERVAL_TYPE_6_MONTHS:
                interval = Interval.WEEKLY;
                break;
            case INTERVAL_TYPE_1_YEARS:
            case INTERVAL_TYPE_2_YEARS:
                interval = Interval.MONTHLY;
                break;
            default:
                interval = Interval.MONTHLY;
                break;
        }

        return interval;
    }

    public static String getIntervalTitle(Context context, int intervalType) {
        String title;
        switch (intervalType) {
            case INTERVAL_TYPE_5_DAYS:
                title = context.getString(R.string.chart_interval_title_5d);
                break;
            case INTERVAL_TYPE_3_MONTHS:
                title = context.getString(R.string.chart_interval_title_3m);
                break;
            case INTERVAL_TYPE_6_MONTHS:
                title = context.getString(R.string.chart_interval_title_6m);
                break;
            case INTERVAL_TYPE_1_YEARS:
                title = context.getString(R.string.chart_interval_title_1y);
                break;
            case INTERVAL_TYPE_2_YEARS:
                title = context.getString(R.string.chart_interval_title_2y);
                break;
            default:
                title = context.getString(R.string.chart_interval_title_5d);
                break;
        }

        return title;
    }

    public static String getIntervalContentDescription(Context context, int intervalType) {
        String title;
        switch (intervalType) {
            case INTERVAL_TYPE_5_DAYS:
                title = context.getString(R.string.chart_interval_content_desc_5d);
                break;
            case INTERVAL_TYPE_3_MONTHS:
                title = context.getString(R.string.chart_interval_content_desc_3m);
                break;
            case INTERVAL_TYPE_6_MONTHS:
                title = context.getString(R.string.chart_interval_content_desc_6m);
                break;
            case INTERVAL_TYPE_1_YEARS:
                title = context.getString(R.string.chart_interval_content_desc_1y);
                break;
            case INTERVAL_TYPE_2_YEARS:
                title = context.getString(R.string.chart_interval_content_desc_2y);
                break;
            default:
                title = context.getString(R.string.chart_interval_content_desc_5d);
                break;
        }

        return title;
    }

    public static String getXAxisName(Context context,int intervalType) {
        String title;
        switch (intervalType) {
            case INTERVAL_TYPE_5_DAYS:
                title = context.getString(R.string.axis_title_days);
                break;
            case INTERVAL_TYPE_3_MONTHS:
            case INTERVAL_TYPE_6_MONTHS:
                title = context.getString(R.string.axis_title_weeks);
                break;
            case INTERVAL_TYPE_1_YEARS:
            case INTERVAL_TYPE_2_YEARS:
                title = context.getString(R.string.axis_title_months);
                break;
            default:
                title = context.getString(R.string.axis_title_days);
                break;
        }

        return title;
    }

}
