package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.HistoricalQuoteModel;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.LineChartView;

public class StockHistoricalDataFragment extends Fragment {

    public static final String STOCK_HISTORICAL_DATA = "STOCK_HISTORICAL_DATA";
    public static final String INTERVAL_TYPE = "INTERVAL_TYPE";

    LineChartView chart;

    ArrayList<HistoricalQuoteModel> historicalQuotes;

    @DataInterval.IntervalType
    int intervalType;

    @DataInterval.IntervalType
    public static StockHistoricalDataFragment newInstance(ArrayList<HistoricalQuoteModel>
                                                                  historicalQuotes, int intervalType) {

        StockHistoricalDataFragment fragment = new StockHistoricalDataFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STOCK_HISTORICAL_DATA, historicalQuotes);
        bundle.putInt(INTERVAL_TYPE, intervalType);

        fragment.setArguments(bundle);

        return fragment;
    }

    @DataInterval.IntervalType
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            historicalQuotes = getArguments().getParcelableArrayList(STOCK_HISTORICAL_DATA);
            @DataInterval.IntervalType
            int type = getArguments().getInt(INTERVAL_TYPE);
            intervalType = type;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_line_graph, container, false);
        chart = (LineChartView) view.findViewById(R.id.chart);
        chart.setVisibility(View.INVISIBLE);

        HelloChartUIHelper.initChart(chart, historicalQuotes, intervalType);

        return view;
    }


}

