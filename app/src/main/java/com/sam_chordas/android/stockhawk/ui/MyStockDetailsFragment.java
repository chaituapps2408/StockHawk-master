package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.StockDetailsModel;
import com.sam_chordas.android.stockhawk.service.StockDetailsLoaderManager;

public class MyStockDetailsFragment extends Fragment implements LoaderManager
        .LoaderCallbacks<StockDetailsModel> {

    public static final String STOCK_SYMBOL = "stock_symbol";


    ViewPager chartsViewPager, stockDataViewPager;
    TabLayout chartsTabLayout, stockDataTabs;

    ChartFragmentsPagerAdapter adapter;
    StockDataViewPagerAdapter stockDataAdapter;

    String stockSymbol;

    public static MyStockDetailsFragment newInstance(String symbol) {

        MyStockDetailsFragment fragment = new MyStockDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCK_SYMBOL, symbol);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            stockSymbol = getArguments().getString(STOCK_SYMBOL);
        }
        adapter = new ChartFragmentsPagerAdapter(getActivity().getSupportFragmentManager());
        stockDataAdapter = new StockDataViewPagerAdapter(getActivity().getSupportFragmentManager());

        getLoaderManager().initLoader(1, null, this).forceLoad();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_details, container, false);


        chartsViewPager = (ViewPager) view.findViewById(R.id.chartViewPager);
        chartsTabLayout = (TabLayout) view.findViewById(R.id.chartTabs);
        chartsViewPager.setAdapter(adapter);
        chartsTabLayout.setupWithViewPager(chartsViewPager);

        stockDataViewPager = (ViewPager) view.findViewById(R.id.stockDataViewPager);
        stockDataTabs = (TabLayout) view.findViewById(R.id.stockDataTabs);
        stockDataViewPager.setAdapter(stockDataAdapter);
        stockDataTabs.setupWithViewPager(stockDataViewPager);


        return view;
    }


    @Override
    public Loader<StockDetailsModel> onCreateLoader(int id, Bundle args) {
        return new StockDetailsLoaderManager(getContext(), stockSymbol);
    }

    @Override
    public void onLoadFinished(Loader<StockDetailsModel> loader, StockDetailsModel data) {

        if (adapter != null)
            adapter.updateData(data.getHistQuotesSeq());
        if (stockDataAdapter != null)
            stockDataAdapter.updateData(data.getStockData());

    }


    @Override
    public void onLoaderReset(Loader<StockDetailsModel> loader) {

    }


}

