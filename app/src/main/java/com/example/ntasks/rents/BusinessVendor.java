package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class BusinessVendor implements Parcelable {

    private String vendorId;
    private String vendorName;
    private String contactPerson;
    private String mobileNumber;
    private String landlineNumber;
    private String address;
    private String emailAddress;
    private String alternateContact1;
    private String alternateContact2;
    private String products;
    private String coordinates;
    private String notes;
    private String docUrl;
    private String userId;
    private String photoUrl;
    // Add any other fields as needed

    public BusinessVendor() {
        // Default constructor required for calls to DataSnapshot.getValue(BusinessVendor.class)
    }

    public BusinessVendor(String vendorId, String vendorName, String contactPerson, String mobileNumber, String landlineNumber, String address,
                          String emailAddress, String alternateContact1, String alternateContact2, String products,
                          String coordinates, String notes, String docUrl, String userId, String photoUrl) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.contactPerson = contactPerson;
        this.mobileNumber = mobileNumber;
        this.landlineNumber = landlineNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.alternateContact1 = alternateContact1;
        this.alternateContact2 = alternateContact2;
        this.products = products;
        this.coordinates = coordinates;
        this.notes = notes;
        this.docUrl = docUrl;
        this.userId = userId;
        this.photoUrl = photoUrl;
        // Initialize other fields as needed
    }

    protected BusinessVendor(Parcel in) {
        vendorId = in.readString();
        vendorName = in.readString();
        contactPerson = in.readString();
        mobileNumber = in.readString();
        landlineNumber = in.readString();
        address = in.readString();
        emailAddress = in.readString();
        alternateContact1 = in.readString();
        alternateContact2 = in.readString();
        products = in.readString();
        coordinates = in.readString();
        notes = in.readString();
        docUrl = in.readString();
        userId = in.readString();
        photoUrl = in.readString();
        // Read other fields as needed
    }

    public static final Creator<BusinessVendor> CREATOR = new Creator<BusinessVendor>() {
        @Override
        public BusinessVendor createFromParcel(Parcel in) {
            return new BusinessVendor(in);
        }

        @Override
        public BusinessVendor[] newArray(int size) {
            return new BusinessVendor[size];
        }
    };

    // Getter and setter methods for all fields

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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAlternateContact1() {
        return alternateContact1;
    }

    public void setAlternateContact1(String alternateContact1) {
        this.alternateContact1 = alternateContact1;
    }

    public String getAlternateContact2() {
        return alternateContact2;
    }

    public void setAlternateContact2(String alternateContact2) {
        this.alternateContact2 = alternateContact2;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vendorId);
        dest.writeString(vendorName);
        dest.writeString(contactPerson);
        dest.writeString(mobileNumber);
        dest.writeString(landlineNumber);
        dest.writeString(address);
        dest.writeString(emailAddress);
        dest.writeString(alternateContact1);
        dest.writeString(alternateContact2);
        dest.writeString(products);
        dest.writeString(coordinates);
        dest.writeString(notes);
        dest.writeString(docUrl);
        dest.writeString(userId);
        dest.writeString(photoUrl);
        // Write other fields as needed
    }
}
