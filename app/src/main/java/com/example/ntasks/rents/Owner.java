package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Owner implements Parcelable {

    private String ownerId;
    private String ownerName;
    private String ownerAddress;
    private String ownerEmail;
    private String ownerPhone1;
    private String ownerPhone2;
    private String ownerPhone3;
    private String ownerNotes;
    private String userId;
    private String photoUrl;
    private String docUrl;
    private String docType; // New field for document type

    public Owner() {
        // Default constructor required for calls to DataSnapshot.getValue(Owner.class)
    }

    public Owner(String ownerId, String ownerName, String ownerAddress, String ownerEmail, String ownerPhone1, String ownerPhone2, String ownerPhone3, String ownerNotes, String userId, String photoUrl, String docUrl, String docType) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.ownerEmail = ownerEmail;
        this.ownerPhone1 = ownerPhone1;
        this.ownerPhone2 = ownerPhone2;
        this.ownerPhone3 = ownerPhone3;
        this.ownerNotes = ownerNotes;
        this.userId = userId;
        this.photoUrl = photoUrl;
        this.docUrl = docUrl;
        this.docType = docType;
    }

    protected Owner(Parcel in) {
        ownerId = in.readString();
        ownerName = in.readString();
        ownerAddress = in.readString();
        ownerEmail = in.readString();
        ownerPhone1 = in.readString();
        ownerPhone2 = in.readString();
        ownerPhone3 = in.readString();
        ownerNotes = in.readString();
        userId = in.readString();
        photoUrl = in.readString();
        docUrl = in.readString();
        docType = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPhone1() {
        return ownerPhone1;
    }

    public void setOwnerPhone1(String ownerPhone1) {
        this.ownerPhone1 = ownerPhone1;
    }

    public String getOwnerPhone2() {
        return ownerPhone2;
    }

    public void setOwnerPhone2(String ownerPhone2) {
        this.ownerPhone2 = ownerPhone2;
    }

    public String getOwnerPhone3() {
        return ownerPhone3;
    }

    public void setOwnerPhone3(String ownerPhone3) {
        this.ownerPhone3 = ownerPhone3;
    }

    public String getOwnerNotes() {
        return ownerNotes;
    }

    public void setOwnerNotes(String ownerNotes) {
        this.ownerNotes = ownerNotes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ownerId);
        dest.writeString(ownerName);
        dest.writeString(ownerAddress);
        dest.writeString(ownerEmail);
        dest.writeString(ownerPhone1);
        dest.writeString(ownerPhone2);
        dest.writeString(ownerPhone3);
        dest.writeString(ownerNotes);
        dest.writeString(userId);
        dest.writeString(photoUrl);
        dest.writeString(docUrl);
        dest.writeString(docType);
    }
}
