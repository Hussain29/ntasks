package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Plot implements Parcelable {

    private String pltId;
    private String pltName;
    private String pltAddress;
    private String pltArea;
    private String pltFloor;
    private String pltShops;
    private String pltNotes;
    private String userId;
    private String vendorName;
    private String ownerName;
    private String docType;
    private String docUrl;
    private String imgUrl;
    private String coordinates;

    public Plot() {
        // Default constructor required for Firebase
    }

    public Plot(String pltId, String pltName, String pltAddress, String pltArea,
                String pltFloor, String pltShops, String pltNotes, String userId, String vendorName, String ownerName,
                String docType, String docUrl, String imgUrl, String coordinates) {
        this.pltId = pltId;
        this.pltName = pltName;
        this.pltAddress = pltAddress;
        this.pltArea = pltArea;
        this.pltFloor = pltFloor;
        this.pltShops = pltShops;
        this.pltNotes = pltNotes;
        this.userId = userId;
        this.vendorName = vendorName;
        this.ownerName = ownerName;
        this.docType = docType;
        this.docUrl = docUrl;
        this.imgUrl = imgUrl;
        this.coordinates = coordinates;
    }

    protected Plot(Parcel in) {
        pltId = in.readString();
        pltName = in.readString();
        pltAddress = in.readString();
        pltArea = in.readString();
        pltFloor = in.readString();
        pltShops = in.readString();
        pltNotes = in.readString();
        userId = in.readString();
        vendorName = in.readString();
        ownerName = in.readString();
        docType = in.readString();
        docUrl = in.readString();
        imgUrl = in.readString();
        coordinates = in.readString();
    }

    public static final Parcelable.Creator<Plot> CREATOR = new Parcelable.Creator<Plot>() {
        @Override
        public Plot createFromParcel(Parcel in) {
            return new Plot(in);
        }

        @Override
        public Plot[] newArray(int size) {
            return new Plot[size];
        }
    };

    public String getPltId() {
        return pltId;
    }

    public String getPltName() {
        return pltName;
    }

    public String getPltAddress() {
        return pltAddress;
    }

    public String getPltArea() {
        return pltArea;
    }

    public String getPltFloor() {
        return pltFloor;
    }

    public String getPltShops() {
        return pltShops;
    }

    public String getPltNotes() {
        return pltNotes;
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

    public String getDocType() {
        return docType;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setPltId(String pltId) {
        this.pltId = pltId;
    }

    public void setPltName(String pltName) {
        this.pltName = pltName;
    }

    public void setPltAddress(String pltAddress) {
        this.pltAddress = pltAddress;
    }

    public void setPltArea(String pltArea) {
        this.pltArea = pltArea;
    }

    public void setPltFloor(String pltFloor) {
        this.pltFloor = pltFloor;
    }

    public void setPltShops(String pltShops) {
        this.pltShops = pltShops;
    }

    public void setPltNotes(String pltNotes) {
        this.pltNotes = pltNotes;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pltId);
        dest.writeString(pltName);
        dest.writeString(pltAddress);
        dest.writeString(pltArea);
        dest.writeString(pltFloor);
        dest.writeString(pltShops);
        dest.writeString(pltNotes);
        dest.writeString(userId);
        dest.writeString(vendorName);
        dest.writeString(ownerName);
        dest.writeString(docType);
        dest.writeString(docUrl);
        dest.writeString(imgUrl);
        dest.writeString(coordinates);
    }
}
