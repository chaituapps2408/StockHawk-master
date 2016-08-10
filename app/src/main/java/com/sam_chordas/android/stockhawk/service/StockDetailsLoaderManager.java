package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.sam_chordas.android.stockhawk.ui.DataInterval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

/**
 * Created by mallakr on 8/9/2016.
 */
public class StockDetailsLoaderManager extends AsyncTaskLoader<List<HistoricalQuote>> {

    @DataInterval.IntervalType
    private final int intervalType;
    private final String stockSymbol;

    public StockDetailsLoaderManager(Context context, int intervalType, String stockSymbol) {
        super(context);
        this.intervalType = intervalType;
        this.stockSymbol = stockSymbol;
    }


    @Override
    public List<HistoricalQuote> loadInBackground() {
        if (TextUtils.isEmpty(stockSymbol)) {
            return new ArrayList<HistoricalQuote>();
        }

        List<HistoricalQuote> googleHistQuotes = new ArrayList<>();
        Calendar to = Calendar.getInstance();
        Calendar from = DataInterval.getFrom(intervalType);

        try {
            Stock google = YahooFinance.get(stockSymbol);
            googleHistQuotes = google.getHistory(from, to, DataInterval.getInterval(intervalType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleHistQuotes;
    }


}


