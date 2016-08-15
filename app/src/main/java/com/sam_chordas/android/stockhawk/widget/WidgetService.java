package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by mallakr on 8/12/2016.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new StockViewsFactory(this.getApplicationContext(),
                intent));
    }
}