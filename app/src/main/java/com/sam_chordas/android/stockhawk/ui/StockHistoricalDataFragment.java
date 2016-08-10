package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.StockDetailsLoaderManager;

import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;
import yahoofinance.histquotes.HistoricalQuote;

public class StockHistoricalDataFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<List<HistoricalQuote>> {

    public static final String STOCK_SYMBOL = "stock_symbol";
    public static final String INTERVAL_TYPE = "interval_type";

    LineChartView chart;

    String stockSymbol;
    int intervalType;

    public static StockHistoricalDataFragment newInstance(String stockSymbol, int position) {

        StockHistoricalDataFragment fragment = new StockHistoricalDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCK_SYMBOL, stockSymbol);
        bundle.putInt(INTERVAL_TYPE, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(1, null, this).forceLoad();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            stockSymbol = getArguments().getString(STOCK_SYMBOL);
            intervalType = getArguments().getInt(INTERVAL_TYPE);
        }

        View view = inflater.inflate(R.layout.fragment_line_graph, container, false);
        chart = (LineChartView) view.findViewById(R.id.chart);
        chart.setVisibility(View.INVISIBLE);
        getLoaderManager().restartLoader(1,null,this).forceLoad();
        return view;
    }




    @Override
    public Loader<List<HistoricalQuote>> onCreateLoader(int id, Bundle args) {

        return new StockDetailsLoaderManager(getContext(), intervalType, stockSymbol);
    }


    @Override
    public void onLoadFinished(Loader<List<HistoricalQuote>> loader, List<HistoricalQuote> data) {

        HelloChartUIHelper.initChart(chart, data);

    }


    @Override
    public void onLoaderReset(Loader<List<HistoricalQuote>> loader) {

    }
}

