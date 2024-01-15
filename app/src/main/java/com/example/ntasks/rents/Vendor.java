/*
package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Vendor implements Parcelable {
    private String vendorId;
    private String vendorName;
    private String vendorAddress;
    private String vendorEmail;
    private String vendorPhone1;
    private String vendorPhone2;
    private String vendorNotes;
    private String userId;

    public Vendor() {
        // Default constructor required for Firebase
    }

    public Vendor(String vendorId, String vendorName, String vendorAddress, String vendorEmail, String vendorPhone1, String vendorPhone2, String vendorNotes, String userId) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorAddress = vendorAddress;
        this.vendorEmail = vendorEmail;
        this.vendorPhone1 = vendorPhone1;
        this.vendorPhone2 = vendorPhone2;
        this.vendorNotes = vendorNotes;
        this.userId = userId;
    }

    protected Vendor(Parcel in) {
        vendorId = in.readString();
        vendorName = in.readString();
        vendorAddress = in.readString();
        vendorEmail = in.readString();
        vendorPhone1 = in.readString();
        vendorPhone2 = in.readString();
        vendorNotes = in.readString();
        userId = in.readString();
    }


    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorPhone1() {
        return vendorPhone1;
    }

    public void setVendorPhone1(String vendorPhone1) {
        this.vendorPhone1 = vendorPhone1;
    }

    public String getVendorPhone2() {
        return vendorPhone2;
    }

    public void setVendorPhone2(String vendorPhone2) {
        this.vendorPhone2 = vendorPhone2;
    }


    public String getVendorNotes() {
        return vendorNotes;
    }

    public void setVendorNotes(String vendorNotes) {
        this.vendorNotes = vendorNotes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static final Creator<Vendor> CREATOR = new Creator<Vendor>() {
        @Override
        public Vendor createFromParcel(Parcel in) {
            return new Vendor(in);
        }

        @Override
        public Vendor[] newArray(int size) {
            return new Vendor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vendorId);
        dest.writeString(vendorName);
        dest.writeString(vendorAddress);
        dest.writeString(vendorEmail);
        dest.writeString(vendorPhone1);
        dest.writeString(vendorPhone2);
        dest.writeString(vendorNotes);
        dest.writeString(userId);
    }
}
*/


package com.example.ntasks.rents;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Vendor implements Parcelable {
    private String vendorId;
    private String vendorName;
    private String vendorAddress;
    private String vendorEmail;
    private String vendorPhone1;
    private String vendorPhone2;
    private String vendorNotes;
    private String userId;
    private String docType; // New field for document type
    private String imageUri; // New field for image URI
    private String documentUri; // New field for document URI

    // Default constructor required for Firebase
    public Vendor() {
    }

    // Constructor with additional fields
    public Vendor(String vendorId, String vendorName, String vendorAddress, String vendorEmail, String vendorPhone1, String vendorPhone2, String vendorNotes, String userId, String docType, String imageUri, String documentUri) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorAddress = vendorAddress;
        this.vendorEmail = vendorEmail;
        this.vendorPhone1 = vendorPhone1;
        this.vendorPhone2 = vendorPhone2;
        this.vendorNotes = vendorNotes;
        this.userId = userId;
        this.docType = docType;
        this.imageUri = imageUri;
        this.documentUri = documentUri;
    }

    protected Vendor(Parcel in) {
        vendorId = in.readString();
        vendorName = in.readString();
        vendorAddress = in.readString();
        vendorEmail = in.readString();
        vendorPhone1 = in.readString();
        vendorPhone2 = in.readString();
        vendorNotes = in.readString();
        userId = in.readString();
        docType = in.readString();
        imageUri = in.readString();
        documentUri = in.readString();
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorPhone1() {
        return vendorPhone1;
    }

    public void setVendorPhone1(String vendorPhone1) {
        this.vendorPhone1 = vendorPhone1;
    }

    public String getVendorPhone2() {
        return vendorPhone2;
    }

    public void setVendorPhone2(String vendorPhone2) {
        this.vendorPhone2 = vendorPhone2;
    }

    public String getVendorNotes() {
        return vendorNotes;
    }

    public void setVendorNotes(String vendorNotes) {
        this.vendorNotes = vendorNotes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDocumentUri() {
        return documentUri;
    }

    public void setDocumentUri(String documentUri) {
        this.documentUri = documentUri;
    }

    public static final Creator<Vendor> CREATOR = new Creator<Vendor>() {
        @Override
        public Vendor createFromParcel(Parcel in) {
            return new Vendor(in);
        }

        @Override
        public Vendor[] newArray(int size) {
            return new Vendor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vendorId);
        dest.writeString(vendorName);
        dest.writeString(vendorAddress);
        dest.writeString(vendorEmail);
        dest.writeString(vendorPhone1);
        dest.writeString(vendorPhone2);
        dest.writeString(vendorNotes);
        dest.writeString(userId);
        dest.writeString(docType);
        dest.writeString(imageUri);
        dest.writeString(documentUri);
    }
}
