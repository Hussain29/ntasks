package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Expenses implements Parcelable {

    private String propertyName;
    private String particular;
    private String expenseAmount;
    private String selectedDate;
    private String currentDate;

    public Expenses() {
        // Default constructor required for calls to DataSnapshot.getValue(Expenses.class)
    }

    public Expenses(String propertyName, String particular, String expenseAmount, String selectedDate, String currentDate) {
        this.propertyName = propertyName;
        this.particular = particular;
        this.expenseAmount = expenseAmount;
        this.selectedDate = selectedDate;
        this.currentDate = currentDate;
    }

    protected Expenses(Parcel in) {
        propertyName = in.readString();
        particular = in.readString();
        expenseAmount = in.readString();
        selectedDate = in.readString();
        currentDate = in.readString();
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(propertyName);
        dest.writeString(particular);
        dest.writeString(expenseAmount);
        dest.writeString(selectedDate);
        dest.writeString(currentDate);
    }
}
