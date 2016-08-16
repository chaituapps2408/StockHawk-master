package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.sam_chordas.android.stockhawk.models.StockDataModel;

import java.util.ArrayList;

/**
 * Created by mallakr on 8/10/2016.
 */
public class StockDataViewPagerAdapter extends FragmentStatePagerAdapter {


    SparseArray<ArrayList<StockDataModel>> stockData = new SparseArray<>();
    Context context;

    public StockDataViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    public void updateData(SparseArray<ArrayList<StockDataModel>> stockData) {
        this.stockData = stockData;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return StockDataFragment.newInstance(stockData.get(position));
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return DetailsType.getStockDetailTitle(context, DetailsType.detailTypeSequence[position]);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return DetailsType.detailTypeSequence.length;
    }
}
