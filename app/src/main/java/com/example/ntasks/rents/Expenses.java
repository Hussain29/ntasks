package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Expenses implements Parcelable {
    private String property;
    private String particular;
    private String amount;
    private String date;

    // Required default constructor for Firebase
    public Expenses() {
    }

    public Expenses(String property, String particular, String amount, String date) {
        this.property = property;
        this.particular = particular;
        this.amount = amount;
        this.date = date;
    }

    protected Expenses(Parcel in) {
        property = in.readString();
        particular = in.readString();
        amount = in.readString();
        date = in.readString();
    }

    public static final Creator<Expenses> CREATOR = new Creator<Expenses>() {
        @Override
        public Expenses createFromParcel(Parcel in) {
            return new Expenses(in);
        }

        @Override
        public Expenses[] newArray(int size) {
            return new Expenses[size];
        }
    };

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(property);
        dest.writeString(particular);
        dest.writeString(amount);
        dest.writeString(date);
    }
}

