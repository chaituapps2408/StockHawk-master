package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.StockDataModel;

import java.util.ArrayList;

public class StockDataFragment extends Fragment {

    public static final String STOCK_DATA = "STOCK_DATA";

    ArrayList<StockDataModel> stockDataList;
    RecyclerView list;
    StockDataAdapter adapter;

    public static StockDataFragment newInstance(ArrayList<StockDataModel> stockDataList) {

        StockDataFragment fragment = new StockDataFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STOCK_DATA, stockDataList);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stockDataList = getArguments().getParcelableArrayList(STOCK_DATA);
        }
        adapter = new StockDataAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock_data, container, false);
        list = (RecyclerView) view.findViewById(R.id.detailsList);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));
        list.addItemDecoration(new DividerItemDecoration(getActivity()));

        list.setAdapter(adapter);

        if (stockDataList != null) {
            adapter.updateData(stockDataList);
        }

        return view;
    }


}

