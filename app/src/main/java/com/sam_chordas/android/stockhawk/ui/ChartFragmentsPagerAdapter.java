package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.sam_chordas.android.stockhawk.models.HistoricalQuoteModel;

import java.util.ArrayList;

/**
 * Created by Chaiy on 8/10/2016.
 */
public class ChartFragmentsPagerAdapter extends FragmentStatePagerAdapter {


    SparseArray<ArrayList<HistoricalQuoteModel>> historicQuoteSeq;
    Context context;

    public ChartFragmentsPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }


    public void updateData(SparseArray<ArrayList<HistoricalQuoteModel>> historicQuoteSeq) {
        this.historicQuoteSeq = historicQuoteSeq;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        ArrayList<HistoricalQuoteModel> data = new ArrayList<>();
        if (historicQuoteSeq != null && historicQuoteSeq.get(position) != null) {
            data = historicQuoteSeq.get(position);
        }
        return StockHistoricalDataFragment.newInstance(data, DataInterval.intervalTypeSequence[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DataInterval.getIntervalTitle(context,DataInterval.intervalTypeSequence[position]);
    }



    @Override
    public int getCount() {
        return DataInterval.intervalTypeSequence.length;
    }
}
