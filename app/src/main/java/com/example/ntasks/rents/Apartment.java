package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Apartment implements Parcelable {

    private String aptId;
    private String aptName;
    private String aptAddress;
    private String aptArea;
    private String aptUnits;
    private String aptFloor;
    private String aptShops;
    private String aptNotes;
    private String userId;
    private String vendorName; // New field
    private String ownerName;  // New field

    public Apartment() {
        // Default constructor required for calls to DataSnapshot.getValue(Apartment.class)
    }

    public Apartment(String aptId, String aptName, String aptAddress, String aptArea, String aptUnits, String aptFloor, String aptShops, String aptNotes, String userId, String vendorName, String ownerName) {
        this.aptId = aptId;
        this.aptName = aptName;
        this.aptAddress = aptAddress;
        this.aptArea = aptArea;
        this.aptUnits = aptUnits;
        this.aptFloor = aptFloor;
        this.aptShops = aptShops;
        this.aptNotes = aptNotes;
        this.userId = userId;
        this.vendorName = vendorName;
        this.ownerName = ownerName;
    }

    protected Apartment(Parcel in) {
        aptId = in.readString();
        aptName = in.readString();
        aptAddress = in.readString();
        aptArea = in.readString();
        aptUnits = in.readString();
        aptFloor = in.readString();
        aptShops = in.readString();
        aptNotes = in.readString();
        userId = in.readString();
        vendorName = in.readString();
        ownerName = in.readString();
    }

    public static final Creator<Apartment> CREATOR = new Creator<Apartment>() {
        @Override
        public Apartment createFromParcel(Parcel in) {
            return new Apartment(in);
        }

        @Override
        public Apartment[] newArray(int size) {
            return new Apartment[size];
        }
    };

    public String getAptId() {
        return aptId;
    }

    public void setAptId(String aptId) {
        this.aptId = aptId;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public String getAptAddress() {
        return aptAddress;
    }

    public void setAptAddress(String aptAddress) {
        this.aptAddress = aptAddress;
    }

    public String getAptArea() {
        return aptArea;
    }

    public void setAptArea(String aptArea) {
        this.aptArea = aptArea;
    }

    public String getAptUnits() {
        return aptUnits;
    }

    public void setAptUnits(String aptUnits) {
        this.aptUnits = aptUnits;
    }

    public String getAptFloor() {
        return aptFloor;
    }

    public void setAptFloor(String aptFloor) {
        this.aptFloor = aptFloor;
    }

    public String getAptShops() {
        return aptShops;
    }

    public void setAptShops(String aptShops) {
        this.aptShops = aptShops;
    }

    public String getAptNotes() {
        return aptNotes;
    }

    public void setAptNotes(String aptNotes) {
        this.aptNotes = aptNotes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aptId);
        dest.writeString(aptName);
        dest.writeString(aptAddress);
        dest.writeString(aptArea);
        dest.writeString(aptUnits);
        dest.writeString(aptFloor);
        dest.writeString(aptShops);
        dest.writeString(aptNotes);
        dest.writeString(userId);
        dest.writeString(vendorName);
        dest.writeString(ownerName);
    }
}
