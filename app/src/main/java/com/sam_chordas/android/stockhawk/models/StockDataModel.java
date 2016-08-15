package com.sam_chordas.android.stockhawk.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mallakr on 8/10/2016.
 */
public class StockDataModel implements Parcelable {

    private String name;
    private String value;

    public StockDataModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    protected StockDataModel(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<StockDataModel> CREATOR = new Creator<StockDataModel>() {
        @Override
        public StockDataModel createFromParcel(Parcel source) {
            return new StockDataModel(source);
        }

        @Override
        public StockDataModel[] newArray(int size) {
            return new StockDataModel[size];
        }
    };
}
