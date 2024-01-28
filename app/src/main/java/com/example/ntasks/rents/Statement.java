package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Statement implements Parcelable {
    private String month;
    private String link;

    public Statement() {
        // Default constructor required for Firebase
    }

    public Statement(String month, String link) {
        this.month = month;
        this.link = link;
    }

    protected Statement(Parcel in) {
        month = in.readString();
        link = in.readString();
    }

    public static final Creator<Statement> CREATOR = new Creator<Statement>() {
        @Override
        public Statement createFromParcel(Parcel in) {
            return new Statement(in);
        }

        @Override
        public Statement[] newArray(int size) {
            return new Statement[size];
        }
    };

    public String getMonth() {
        return month;
    }

    public String getLink() {
        return link;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(month);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
