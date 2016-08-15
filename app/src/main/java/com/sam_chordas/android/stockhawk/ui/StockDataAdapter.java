package com.sam_chordas.android.stockhawk.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.StockDataModel;

import java.util.ArrayList;

/**
 * Created by mallakr on 8/10/2016.
 */
public class StockDataAdapter extends RecyclerView.Adapter<StockDataAdapter.StockDataViewHolder> {


    ArrayList<StockDataModel> stockDataList = new ArrayList<>();

    public StockDataAdapter() {
    }

    public void updateData(ArrayList<StockDataModel> stockDataList) {
        this.stockDataList = stockDataList;
        notifyDataSetChanged();
    }


    @Override
    public StockDataAdapter.StockDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_details,
                parent, false);


        return new StockDataViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StockDataAdapter.StockDataViewHolder holder, int position) {

        StockDataModel model = stockDataList.get(position);

        holder.detailTitle.setText(model.getName());
        holder.detailValue.setText(model.getValue());

    }


    @Override
    public int getItemCount() {
        return stockDataList.size();
    }

    class StockDataViewHolder extends RecyclerView.ViewHolder {

        TextView detailTitle;
        TextView detailValue;

        public StockDataViewHolder(View itemView) {
            super(itemView);
            detailTitle = (TextView) itemView.findViewById(R.id.detail_title);
            detailValue = (TextView) itemView.findViewById(R.id.detail_value);
        }
    }
}
