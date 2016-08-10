package com.sam_chordas.android.stockhawk.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Chaiy on 8/10/2016.
 */
public class ChartFragmentsPagerAdapter extends FragmentStatePagerAdapter {


    String stockSymbol;

    public ChartFragmentsPagerAdapter(FragmentManager fm, String stockSymbol) {
        super(fm);
        this.stockSymbol = stockSymbol;
    }

    @Override
    public Fragment getItem(int position) {
        return StockHistoricalDataFragment.newInstance(stockSymbol, DataInterval.intervalTypeSequence[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DataInterval.getIntervalTitle(position);
    }

    @Override
    public int getCount() {
        return DataInterval.intervalTypeSequence.length;
    }
}
