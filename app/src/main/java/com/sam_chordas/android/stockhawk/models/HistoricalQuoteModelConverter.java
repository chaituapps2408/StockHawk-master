package com.sam_chordas.android.stockhawk.models;

import java.util.ArrayList;

import yahoofinance.histquotes.HistoricalQuote;

/**
 * Created by mallakr on 8/10/2016.
 */
public class HistoricalQuoteModelConverter {


    public static ArrayList<HistoricalQuoteModel> convert(ArrayList<HistoricalQuote> historicalQuoteList) {

        ArrayList<HistoricalQuoteModel> listModel = new ArrayList<>();

        if (historicalQuoteList != null) {

            for (int i = 0; i < historicalQuoteList.size(); i++) {
                HistoricalQuote quote = historicalQuoteList.get(i);
                HistoricalQuoteModel model = new HistoricalQuoteModel();
                model.setSymbol(quote.getSymbol());
                model.setAdjClose(quote.getAdjClose());
                model.setClose(quote.getClose());
                model.setDate(quote.getDate());
                model.setHigh(quote.getHigh());
                model.setLow(quote.getLow());
                model.setOpen(quote.getOpen());
                model.setVolume(quote.getVolume());

                listModel.add(model);
            }
        }

        return listModel;
    }

}
