package com.sam_chordas.android.stockhawk.models;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by mallakr on 8/11/2016.
 */
public class StockDetailsModel {

    private final SparseArray<ArrayList<HistoricalQuoteModel>> histQuotesSeq;
    private final SparseArray<ArrayList<StockDataModel>> stockData;

    public StockDetailsModel(SparseArray<ArrayList<HistoricalQuoteModel>> histQuotesSeq, SparseArray<ArrayList<StockDataModel>> stockData) {
        this.histQuotesSeq = histQuotesSeq;
        this.stockData = stockData;
    }

    public SparseArray<ArrayList<HistoricalQuoteModel>> getHistQuotesSeq() {
        return histQuotesSeq;
    }

    public SparseArray<ArrayList<StockDataModel>> getStockData() {
        return stockData;
    }
}
