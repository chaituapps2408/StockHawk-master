package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.SparseArray;

import com.sam_chordas.android.stockhawk.models.HistoricalQuoteModel;
import com.sam_chordas.android.stockhawk.models.HistoricalQuoteModelConverter;
import com.sam_chordas.android.stockhawk.models.StockDataModel;
import com.sam_chordas.android.stockhawk.models.StockDataModelConverter;
import com.sam_chordas.android.stockhawk.models.StockDetailsModel;
import com.sam_chordas.android.stockhawk.ui.DataInterval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

/**
 * Created by mallakr on 8/9/2016.
 */
public class StockDetailsLoaderManager extends AsyncTaskLoader<StockDetailsModel> {


    private final String stockSymbol;

    public StockDetailsLoaderManager(Context context, String stockSymbol) {
        super(context);
        this.stockSymbol = stockSymbol;
    }


    @Override
    public StockDetailsModel loadInBackground() {

        SparseArray<ArrayList<HistoricalQuoteModel>> histQuotesSeq = new SparseArray<>();
        SparseArray<ArrayList<StockDataModel>> stockData = new SparseArray<>();

        StockDetailsModel stockDetailsModel = new StockDetailsModel(histQuotesSeq, stockData);

        if (TextUtils.isEmpty(stockSymbol)) {
            return stockDetailsModel;
        }
        try {
            Stock stock = YahooFinance.get(stockSymbol);

            for (int i = 0; i < DataInterval.intervalTypeSequence.length; i++) {

                @DataInterval.IntervalType
                int intervalType = DataInterval.intervalTypeSequence[i];
                Calendar to = Calendar.getInstance();
                Calendar from = DataInterval.getFrom(intervalType);

                ArrayList<HistoricalQuote> historicalQuotesList = (ArrayList<HistoricalQuote>) stock.getHistory(from, to, DataInterval.getInterval(intervalType));
                histQuotesSeq.put(i, HistoricalQuoteModelConverter.convert(historicalQuotesList));

            }

            stockData = StockDataModelConverter.toDataModel(stock, getContext());

            stockDetailsModel = new StockDetailsModel(histQuotesSeq, stockData);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockDetailsModel;
    }


}


