package com.sam_chordas.android.stockhawk.models;

import android.content.Context;
import android.util.SparseArray;

import com.sam_chordas.android.stockhawk.rest.Utils;
import com.sam_chordas.android.stockhawk.ui.DetailsType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockDividend;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockStats;

/**
 * Created by mallakr on 8/10/2016.
 */
public class StockDataModelConverter {


    public static SparseArray<ArrayList<StockDataModel>> toDataModel(Stock stock, Context context) {

        SparseArray<ArrayList<StockDataModel>> sparseArray = new SparseArray<>();

        for (int i = 0; i < DetailsType.detailTypeSequence.length; i++) {
            switch (DetailsType.detailTypeSequence[i]) {
                case DetailsType.DETAIL_TYPE_QUOTE:
                    sparseArray.put(i, convert(stock.getQuote(), DetailsType.getDetailNames
                            (DetailsType.detailTypeSequence[i], context)));
                    break;
                case DetailsType.DETAIL_TYPE_STATS:
                    sparseArray.put(i, convert(stock.getStats(), DetailsType.getDetailNames
                            (DetailsType.detailTypeSequence[i], context)));
                    break;
                case DetailsType.DETAIL_TYPE_DIVIDEND:
                    sparseArray.put(i, convert(stock.getDividend(), DetailsType.getDetailNames
                            (DetailsType.detailTypeSequence[i], context)));
                    break;
                default:
                    sparseArray.put(i, convert(stock.getQuote(), DetailsType.getDetailNames
                            (DetailsType.detailTypeSequence[i], context)));
                    break;

            }
        }


        return sparseArray;

    }

    public static ArrayList<StockDataModel> convert(StockQuote stockQuote, String[] detailNames) {

        ArrayList<StockDataModel> list = new ArrayList<>();

        list.add(new StockDataModel(detailNames[0], toString(stockQuote.getAsk())));
        list.add(new StockDataModel(detailNames[1], toString(stockQuote.getAskSize())));
        list.add(new StockDataModel(detailNames[2], toString(stockQuote.getBid())));
        list.add(new StockDataModel(detailNames[3], toString(stockQuote.getBidSize())));
        list.add(new StockDataModel(detailNames[4], toString(stockQuote.getPrice())));
        list.add(new StockDataModel(detailNames[5], toString(stockQuote.getLastTradeSize())));
        list.add(new StockDataModel(detailNames[6], toString(stockQuote.getLastTradeDateStr())));
        list.add(new StockDataModel(detailNames[7], toString(stockQuote.getLastTradeTimeStr())));
        list.add(new StockDataModel(detailNames[8], toString(stockQuote.getLastTradeTime())));
        list.add(new StockDataModel(detailNames[9], toString(stockQuote.getOpen())));
        list.add(new StockDataModel(detailNames[10], toString(stockQuote.getPreviousClose())));
        list.add(new StockDataModel(detailNames[11], toString(stockQuote.getDayLow())));
        list.add(new StockDataModel(detailNames[12], toString(stockQuote.getDayHigh())));
        list.add(new StockDataModel(detailNames[13], toString(stockQuote.getYearLow())));
        list.add(new StockDataModel(detailNames[14], toString(stockQuote.getYearHigh())));
        list.add(new StockDataModel(detailNames[15], toString(stockQuote.getPriceAvg50())));
        list.add(new StockDataModel(detailNames[16], toString(stockQuote.getPriceAvg200())));
        list.add(new StockDataModel(detailNames[17], toString(stockQuote.getVolume())));
        list.add(new StockDataModel(detailNames[18], toString(stockQuote.getAvgVolume())));


        return list;
    }

    public static ArrayList<StockDataModel> convert(StockStats stockStats, String[] detailNames) {

        ArrayList<StockDataModel> list = new ArrayList<>();

        list.add(new StockDataModel(detailNames[0], toString(stockStats.getMarketCap())));
        list.add(new StockDataModel(detailNames[1], toString(stockStats.getSharesFloat())));
        list.add(new StockDataModel(detailNames[2], toString(stockStats.getSharesOutstanding())));
        list.add(new StockDataModel(detailNames[3], toString(stockStats.getSharesOwned())));
        list.add(new StockDataModel(detailNames[4], toString(stockStats.getEps())));
        list.add(new StockDataModel(detailNames[5], toString(stockStats.getPe())));
        list.add(new StockDataModel(detailNames[6], toString(stockStats.getPeg())));
        list.add(new StockDataModel(detailNames[7], toString(stockStats.getEpsEstimateCurrentYear())));
        list.add(new StockDataModel(detailNames[8], toString(stockStats.getEpsEstimateNextQuarter())));
        list.add(new StockDataModel(detailNames[9], toString(stockStats.getEpsEstimateNextYear())));
        list.add(new StockDataModel(detailNames[10], toString(stockStats.getPriceBook())));
        list.add(new StockDataModel(detailNames[11], toString(stockStats.getPriceSales())));
        list.add(new StockDataModel(detailNames[12], toString(stockStats.getBookValuePerShare())));
        list.add(new StockDataModel(detailNames[13], toString(stockStats.getRevenue())));
        list.add(new StockDataModel(detailNames[14], toString(stockStats.getEBITDA())));
        list.add(new StockDataModel(detailNames[15], toString(stockStats.getOneYearTargetPrice())));
        list.add(new StockDataModel(detailNames[16], toString(stockStats.getShortRatio())));

        return list;
    }

    public static ArrayList<StockDataModel> convert(StockDividend stockDividend, String[] detailNames) {

        ArrayList<StockDataModel> list = new ArrayList<>();

        list.add(new StockDataModel(detailNames[0], toString(stockDividend.getPayDate())));
        list.add(new StockDataModel(detailNames[1], toString(stockDividend.getExDate())));
        list.add(new StockDataModel(detailNames[2], toString(stockDividend.getAnnualYield())));
        list.add(new StockDataModel(detailNames[3], toString(stockDividend.getAnnualYieldPercent())));

        return list;
    }


    private static String toString(Calendar object) {
        String val = "";

        if (object == null) {
            return val;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");
        val = formatter.format(object.getTime());
        return val;
    }

    private static String toString(BigDecimal object) {
        String val = "";

        if (object == null) {
            return val;
        }
        return Utils.round(object, Utils.DECIMAL_PLACE).toPlainString();
    }


    private static String toString(Long object) {
        String val = "";

        if (object == null) {
            return val;
        }
        return object.toString();
    }

    private static String toString(String object) {
        String val = "";

        if (object == null) {
            return val;
        }
        return object;
    }
}
