package com.sam_chordas.android.stockhawk.ui;

import android.support.annotation.IntDef;

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

    public static String getIntervalTitle(int intervalType) {
        String title;
        switch (intervalType) {
            case INTERVAL_TYPE_5_DAYS:
                title = "5 days";
                break;
            case INTERVAL_TYPE_3_MONTHS:
                title = "3 months";
                break;
            case INTERVAL_TYPE_6_MONTHS:
                title = "6 months";
                break;
            case INTERVAL_TYPE_1_YEARS:
                title = "1 year";
                break;
            case INTERVAL_TYPE_2_YEARS:
                title = "2 years";
                break;
            default:
                title = "5 days";
                break;
        }

        return title;
    }
}
