package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Independent implements Parcelable {

    private String indpId;
    private String indpName;
    private String indpAddress;
    private String indpArea;
    private String indpUnits;
    private String indpFloor;
    private String indpShops;
    private String indpNotes;
    private String userId;
    private String vendorName;
    private String ownerName;

    public Independent() {
        // Default constructor required for Firebase
    }

    public Independent(String indpId, String indpName, String indpAddress, String indpArea, String indpUnits,
                       String indpFloor, String indpShops, String indpNotes, String userId, String vendorName, String ownerName) {
        this.indpId = indpId;
        this.indpName = indpName;
        this.indpAddress = indpAddress;
        this.indpArea = indpArea;
        this.indpUnits = indpUnits;
        this.indpFloor = indpFloor;
        this.indpShops = indpShops;
        this.indpNotes = indpNotes;
        this.userId = userId;
        this.vendorName = vendorName;
        this.ownerName = ownerName;
    }

    protected Independent(Parcel in) {
        indpId = in.readString();
        indpName = in.readString();
        indpAddress = in.readString();
        indpArea = in.readString();
        indpUnits = in.readString();
        indpFloor = in.readString();
        indpShops = in.readString();
        indpNotes = in.readString();
        userId = in.readString();
        vendorName = in.readString();
        ownerName = in.readString();
    }

    public static final Parcelable.Creator<Independent> CREATOR = new Creator<Independent>() {
        @Override
        public Independent createFromParcel(Parcel in) {
            return new Independent(in);
        }

        @Override
        public Independent[] newArray(int size) {
            return new Independent[size];
        }
    };

    public String getIndpId() {
        return indpId;
    }

    public String getIndpName() {
        return indpName;
    }

    public String getIndpAddress() {
        return indpAddress;
    }

    public String getIndpArea() {
        return indpArea;
    }

    public String getIndpUnits() {
        return indpUnits;
    }

    public String getIndpFloor() {
        return indpFloor;
    }

    public String getIndpShops() {
        return indpShops;
    }

    public String getIndpNotes() {
        return indpNotes;
    }

    public String getUserId() {
        return userId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(indpId);
        dest.writeString(indpName);
        dest.writeString(indpAddress);
        dest.writeString(indpArea);
        dest.writeString(indpUnits);
        dest.writeString(indpFloor);
        dest.writeString(indpShops);
        dest.writeString(indpNotes);
        dest.writeString(userId);
        dest.writeString(vendorName);
        dest.writeString(ownerName);
    }
}
