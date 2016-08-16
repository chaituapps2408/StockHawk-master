package com.sam_chordas.android.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Utils;
import com.sam_chordas.android.stockhawk.service.StockIntentService;
import com.sam_chordas.android.stockhawk.service.StockTaskService;

public class MyStocksActivity extends AppCompatActivity implements MyStockListFragment
        .OnItemSelectedListener, AttachViewToFabListener {

    private static final String DETAIL_FRAGMENT_TAG = "DetailFragment";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */


    private Intent mServiceIntent;
    private Context mContext;

    private boolean isTowPane;

    FrameLayout stockDetailsContainer;
    Space spaceView;
    TextView stockSymbol;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_my_stocks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        stockSymbol = (TextView) toolbar.findViewById(R.id.stockSymbol);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        stockDetailsContainer = (FrameLayout) findViewById(R.id.stockDetailsContainer);
        spaceView = (Space) findViewById(R.id.spaceView);
        if (stockDetailsContainer != null) {
            isTowPane = true;
            if (savedInstanceState == null) {
                loadDetailsContainer(null);
            }
        } else {
            isTowPane = false;
            getSupportActionBar().setElevation(0f);
        }
        mServiceIntent = new Intent(this, StockIntentService.class);

        if (Utils.isNetworkAvailable(this)) {
            long period = 1000L;
            long flex = 10L;
            String periodicTag = "periodic";

            // create a periodic task to pull stocks once every hour after the app has been opened. This
            // is so Widget data stays up to date.
            PeriodicTask periodicTask = new PeriodicTask.Builder()
                    .setService(StockTaskService.class)
                    .setPeriod(period)
                    .setFlex(flex)
                    .setTag(periodicTag)
                    .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                    .setRequiresCharging(false)
                    .build();
            // Schedule task with tag "periodic." This ensure that only the stocks present in the DB
            // are updated.
            GcmNetworkManager.getInstance(this).schedule(periodicTask);
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(MyStocksActivity.this)) {
                    new MaterialDialog.Builder(mContext).title(R.string.symbol_search)
                            .content(R.string.content_test)
                            .inputType(InputType.TYPE_CLASS_TEXT)
                            .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    // On FAB click, receive user input. Make sure the stock doesn't already exist
                                    // in the DB and proceed accordingly
                                    if (TextUtils.isEmpty(input)) {
                                        return;
                                    }
                                    Cursor c = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                                            new String[]{QuoteColumns.SYMBOL}, QuoteColumns.SYMBOL + "= ?",
                                            new String[]{input.toString().toUpperCase()}, null);
                                    if (c.getCount() != 0) {
                                        Toast toast =
                                                Toast.makeText(MyStocksActivity.this, R.string.stock_exists_msg,
                                                        Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, Gravity.CENTER, 0);
                                        toast.show();
                                        return;
                                    } else {
                                        // Add the stock to DB
                                        mServiceIntent.putExtra("tag", "add");
                                        mServiceIntent.putExtra("symbol", input.toString());
                                        startService(mServiceIntent);
                                    }
                                }
                            })
                            .show();
                } else {
                    networkToast();
                }

            }
        });


    }

    public void networkToast() {
        Toast.makeText(mContext, getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(String stockSymbol) {
        if (isTowPane) {

            loadDetailsContainer(stockSymbol);
        } else {
            Intent intent = new Intent(this, MyStockDetailsActivity.class);
            intent.putExtra(MyStockDetailsActivity.STOCK_SYMBOL, stockSymbol);
            startActivity(intent);
        }
    }

    private void loadDetailsContainer(String symbol) {
        if (TextUtils.isEmpty(symbol)) {
            stockDetailsContainer.setVisibility(View.GONE);
            spaceView.setVisibility(View.GONE);
        } else {
            stockDetailsContainer.setVisibility(View.VISIBLE);
            spaceView.setVisibility(View.VISIBLE);
            if (stockSymbol != null) {
                stockSymbol.setText(symbol);
            }
        }

        MyStockDetailsFragment fragment = MyStockDetailsFragment.newInstance(symbol);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.stockDetailsContainer, fragment, DETAIL_FRAGMENT_TAG)
                .commit();
    }


    @Override
    public void onAttachViewToFabListener(View scrollView) {
        if (isTowPane) {

            if (scrollView instanceof ScrollView && fab != null) {

                fab.attachToScrollView((ObservableScrollView) scrollView);
            }

        } else {
            if (scrollView instanceof RecyclerView && fab != null) {
                fab.attachToRecyclerView((RecyclerView) scrollView);
            }
        }
    }
}

interface AttachViewToFabListener {
    void onAttachViewToFabListener(View scrollView);
}