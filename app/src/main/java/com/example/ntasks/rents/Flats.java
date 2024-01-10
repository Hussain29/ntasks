package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Flats implements Parcelable {

    private String flatId;
    private String area;
    private String flatNo;
    private String flatNotes;
    private String apartmentName;
    private String fType;
    private String ownerName;
    private String vendorName;
    /*private String id;*/
    private String userId;

    public Flats() {
        // Default constructor required for calls to DataSnapshot.getValue(Flats.class)
    }

    public Flats(String flatId, String area, String flatNo, String flatNotes, String apartmentName, String fType, String ownerName, String vendorName, /*String id,*/ String userId) {
        this.flatId = flatId;
        this.area = area;
        this.flatNo = flatNo;
        this.flatNotes = flatNotes;
        this.apartmentName = apartmentName;
        this.fType = fType;
        this.ownerName = ownerName;
        this.vendorName = vendorName;
        /*this.id = id;*/
        this.userId = userId;
    }

    protected Flats(Parcel in) {
        flatId = in.readString();
        area = in.readString();
        flatNo = in.readString();
        flatNotes = in.readString();
        apartmentName = in.readString();
        fType = in.readString();
        ownerName = in.readString();
        vendorName = in.readString();
        /*id = in.readString();*/
        userId = in.readString();
    }

    public static final Creator<Flats> CREATOR = new Creator<Flats>() {
        @Override
        public Flats createFromParcel(Parcel in) {
            return new Flats(in);
        }

        @Override
        public Flats[] newArray(int size) {
            return new Flats[size];
        }
    };

    public String getFlatId() {
        return flatId;
    }

    public void setFlatId(String flatId) {
        this.flatId = flatId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getFlatNotes() {
        return flatNotes;
    }

    public void setFlatNotes(String flatNotes) {
        this.flatNotes = flatNotes;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

   /* public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(flatId);
        dest.writeString(area);
        dest.writeString(flatNo);
        dest.writeString(flatNotes);
        dest.writeString(apartmentName);
        dest.writeString(fType);
        dest.writeString(ownerName);
        dest.writeString(vendorName);
        /*dest.writeString(id);*/
        dest.writeString(userId);
    }
}
