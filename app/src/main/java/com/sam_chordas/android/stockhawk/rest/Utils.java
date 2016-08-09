package com.sam_chordas.android.stockhawk.rest;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Map;

import yahoofinance.Stock;

/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    private static String LOG_TAG = Utils.class.getSimpleName();

    public static boolean showPercent = true;

    /**
     * Returns true if the network is available or about to become available.
     *
     * @param c Context used to get the ConnectivityManager
     * @return true if the network is available
     */
    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static ArrayList quoteStockToContentVals(Map<String, Stock> stocks, String[] symbols) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();

        for (int i = 0; i < symbols.length; i++) {

            Stock quote = stocks.get(symbols[i]);
            if (quote != null && !TextUtils.isEmpty(quote.getName())) {
                ContentProviderOperation contentProviderOperation = buildBatchOperation(quote);
                if (contentProviderOperation != null)
                    batchOperations.add(contentProviderOperation);

            }

        }
        return batchOperations;
    }

    public static ArrayList quoteJsonToContentVals(String JSON) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray resultsArray = null;
        try {
            jsonObject = new JSONObject(JSON);
            if (jsonObject != null && jsonObject.length() != 0) {
                jsonObject = jsonObject.getJSONObject("query");
                int count = Integer.parseInt(jsonObject.getString("count"));
                if (count == 1) {
                    jsonObject = jsonObject.getJSONObject("results")
                            .getJSONObject("quote");
                    ContentProviderOperation contentProviderOperation = buildBatchOperation(jsonObject);
                    if (contentProviderOperation != null)
                        batchOperations.add(contentProviderOperation);
                } else {
                    resultsArray = jsonObject.getJSONObject("results").getJSONArray("quote");

                    if (resultsArray != null && resultsArray.length() != 0) {
                        for (int i = 0; i < resultsArray.length(); i++) {
                            jsonObject = resultsArray.getJSONObject(i);
                            ContentProviderOperation contentProviderOperation = buildBatchOperation(jsonObject);
                            if (contentProviderOperation != null)
                                batchOperations.add(contentProviderOperation);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "String to JSON failed: " + e);
        }
        return batchOperations;
    }

    public static String truncateBidPrice(String bidPrice) {
        if (TextUtils.isEmpty(bidPrice) || bidPrice.equalsIgnoreCase("null")) {
            bidPrice = "0.00";
        }
        try {
            bidPrice = String.format("%.2f", Float.parseFloat(bidPrice));
        } catch (NumberFormatException | NullPointerException | IllegalFormatException e) {
            e.printStackTrace();
        }
        return bidPrice;
    }

    public static String truncateChange(String change, boolean isPercentChange) throws ArrayIndexOutOfBoundsException, NullPointerException, NumberFormatException, IllegalFormatException {
        String weight = change.substring(0, 1);
        String ampersand = "";
        if (isPercentChange) {
            ampersand = change.substring(change.length() - 1, change.length());
            change = change.substring(0, change.length() - 1);
        }
        change = change.substring(1, change.length());
        double round = (double) Math.round(Double.parseDouble(change) * 100) / 100;
        change = String.format("%.2f", round);
        StringBuffer changeBuffer = new StringBuffer(change);
        changeBuffer.insert(0, weight);
        changeBuffer.append(ampersand);
        change = changeBuffer.toString();
        return change;
    }

    public static ContentProviderOperation buildBatchOperation(Stock stock) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);
        try {
            String name = stock.getName();
            BigDecimal change = stock.getQuote().getChange();
            String changeString = change.toPlainString();
            if (changeString != null && !changeString.contains("-")) {
                changeString = "+" + changeString;
            }
            BigDecimal changeInPercent = stock.getQuote().getChangeInPercent();
            builder.withValue(QuoteColumns.SYMBOL, stock.getSymbol().toUpperCase());
            builder.withValue(QuoteColumns.NAME, name);
            builder.withValue(QuoteColumns.BIDPRICE, stock.getQuote().getBid().toPlainString());
            builder.withValue(QuoteColumns.PERCENT_CHANGE, "%" + changeInPercent.toPlainString());
            builder.withValue(QuoteColumns.CHANGE, changeString);
            builder.withValue(QuoteColumns.ISCURRENT, 1);
            if (change.doubleValue() < 0) {
                builder.withValue(QuoteColumns.ISUP, 0);
            } else {
                builder.withValue(QuoteColumns.ISUP, 1);
            }

        } catch (IllegalFormatException | NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    public static ContentProviderOperation buildBatchOperation(JSONObject jsonObject) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);
        try {
            String name = jsonObject.getString("Name");
            if (TextUtils.isEmpty(name) || name.equalsIgnoreCase("null")) {
                return null;
            }
            String change = jsonObject.getString("Change");
            builder.withValue(QuoteColumns.SYMBOL, jsonObject.getString("symbol"));
            builder.withValue(QuoteColumns.NAME, name);
            builder.withValue(QuoteColumns.BIDPRICE, truncateBidPrice(jsonObject.getString("Bid")));
            builder.withValue(QuoteColumns.PERCENT_CHANGE, truncateChange(
                    jsonObject.getString("ChangeinPercent"), true));
            builder.withValue(QuoteColumns.CHANGE, truncateChange(change, false));
            builder.withValue(QuoteColumns.ISCURRENT, 1);
            if (change.charAt(0) == '-') {
                builder.withValue(QuoteColumns.ISUP, 0);
            } else {
                builder.withValue(QuoteColumns.ISUP, 1);
            }

        } catch (JSONException | IllegalFormatException | NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
