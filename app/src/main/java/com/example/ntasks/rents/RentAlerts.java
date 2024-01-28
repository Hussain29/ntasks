package com.example.ntasks.rents;


import android.os.Parcel;
import android.os.Parcelable;

public class RentAlerts implements Parcelable {

    private String tenantName;
    private String apartment;
    private String dateOfPayment;
    private String rentAmount;

    public RentAlerts() {
        // Default constructor required for calls to DataSnapshot.getValue(RentAlerts.class)
    }

    protected RentAlerts(Parcel in) {
        tenantName = in.readString();
        apartment = in.readString();
        dateOfPayment = in.readString();
        rentAmount = in.readString();
    }

    public static final Creator<RentAlerts> CREATOR = new Creator<RentAlerts>() {
        @Override
        public RentAlerts createFromParcel(Parcel in) {
            return new RentAlerts(in);
        }

        @Override
        public RentAlerts[] newArray(int size) {
            return new RentAlerts[size];
        }
    };

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tenantName);
        dest.writeString(apartment);
        dest.writeString(dateOfPayment);
        dest.writeString(rentAmount);
    }
}
