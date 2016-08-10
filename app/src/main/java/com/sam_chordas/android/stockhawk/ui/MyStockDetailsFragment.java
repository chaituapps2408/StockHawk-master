package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam_chordas.android.stockhawk.R;

public class MyStockDetailsFragment extends Fragment implements ViewPager.OnPageChangeListener {

    public static final String STOCK_SYMBOL = "stock_symbol";


    ViewPager chartsViewPager;
    TabLayout chartsTabLayout;

    ChartFragmentsPagerAdapter adapter;
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

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_details, container, false);

        if (getArguments() != null) {
            stockSymbol = getArguments().getString(STOCK_SYMBOL);
        }

        chartsViewPager = (ViewPager) view.findViewById(R.id.chartViewPager);
        chartsTabLayout = (TabLayout) view.findViewById(R.id.chartTabs);

        adapter = new ChartFragmentsPagerAdapter(getActivity().getSupportFragmentManager(), stockSymbol);
        chartsViewPager.setAdapter(adapter);
        chartsTabLayout.setupWithViewPager(chartsViewPager);

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

