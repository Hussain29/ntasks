package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Collection implements Parcelable , AmountCalculable{

    @Override
    public double getAmount() {
        // Return the amount for collections
        return Double.parseDouble(rentAmount);
    }
    private String propertyName;
    private String tenantName;
    private String rentAmount;
    private String submissionDate;

    private String selectedDate; // New field

    public Collection() {
        // Default constructor required for calls to DataSnapshot.getValue(Collection.class)
    }

    public Collection(String propertyName, String tenantName, String rentAmount, String submissionDate, String selectedDate) {
        this.propertyName = propertyName;
        this.tenantName = tenantName;
        this.rentAmount = rentAmount;
        this.submissionDate = submissionDate;
        this.selectedDate = selectedDate; // Initialize the new field
    }

    protected Collection(Parcel in) {
        propertyName = in.readString();
        tenantName = in.readString();
        rentAmount = in.readString();
        submissionDate = in.readString();
        selectedDate = in.readString();
    }

    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(propertyName);
        dest.writeString(tenantName);
        dest.writeString(rentAmount);
        dest.writeString(submissionDate);
        dest.writeString(selectedDate);
    }

  /*  @Override
    public String toString() {
        return ">" +
                 submissionDate +"\t"+ '\'' +
                  propertyName+"\t" + '\'' +
                 tenantName+"\t" + '\'' +
                  rentAmount +"\t"+ '\'' ;
    }*/

    @Override
    public String toString() {
        String paddedPropertyName = padString(propertyName, 25); // Adjust the width as needed
        String paddedtenantName = padString(tenantName, 25); // Adjust the width as needed
        String paddedrentAmount = padString(rentAmount, 15); // Adjust the width as needed

        return ">" +
                selectedDate + "\t-" +
                paddedPropertyName + "\t-" +
                paddedtenantName + "\t" +
                paddedrentAmount + "\t";
    }

    private String padString(String input, int width) {
        if (input.length() >= width) {
            return input.substring(0, width);
        } else {
            return String.format("%-" + width + "s", input);
        }
    }


}
