package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.R;

public class MyStockDetailsActivity extends AppCompatActivity {

    public static final String STOCK_SYMBOL = "stock_symbol";
    String stockSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_line_graph);

        stockSymbol = getIntent().getStringExtra(STOCK_SYMBOL);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,
                MyStockDetailsFragment.newInstance(stockSymbol),
                StockHistoricalDataFragment.class.getSimpleName()).commit();


    }


}

