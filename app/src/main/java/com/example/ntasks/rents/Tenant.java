package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Tenant implements Parcelable {

    private String tenantId;
    private String tenantName;
    private String tenantFatherName;
    private String tenantPerAddress;
    private String tenantPrevAddress;
    private String tenantOccupation;
    private String tenantWorkAddress;
    private String noOfPeople;
    private String tenantPhoneNumber;
    private String tenantPhoneNumber2;
    private String tenantPhoneNumber3;
    private String propertyName;
    private String tenantRent;
    private String advanceAmount;
    private String admissionDate;
    private String docType;
    private String tenantNotes;
    private String imgUrl;
    private String docUrl;
    private String payday; // New field for payday
    private String age; // New field for payday

    public Tenant() {
        // Default constructor required for calls to DataSnapshot.getValue(Tenant.class)
    }

    public Tenant(String tenantId, String tenantName, String tenantFatherName, String tenantPerAddress,
                  String tenantPrevAddress, String tenantOccupation, String tenantWorkAddress,
                  String noOfPeople, String tenantPhoneNumber, String tenantPhoneNumber2,
                  String tenantPhoneNumber3, String propertyName, String tenantRent,
                  String advanceAmount, String admissionDate, String docType, String tenantNotes,
                  String imgUrl, String docUrl, String payday, String age) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantFatherName = tenantFatherName;
        this.tenantPerAddress = tenantPerAddress;
        this.tenantPrevAddress = tenantPrevAddress;
        this.tenantOccupation = tenantOccupation;
        this.tenantWorkAddress = tenantWorkAddress;
        this.noOfPeople = noOfPeople;
        this.tenantPhoneNumber = tenantPhoneNumber;
        this.tenantPhoneNumber2 = tenantPhoneNumber2;
        this.tenantPhoneNumber3 = tenantPhoneNumber3;
        this.propertyName = propertyName;
        this.tenantRent = tenantRent;
        this.advanceAmount = advanceAmount;
        this.admissionDate = admissionDate;
        this.docType = docType;
        this.tenantNotes = tenantNotes;
        this.imgUrl = imgUrl;
        this.docUrl = docUrl;
        this.payday = payday;
        this.age = age;
    }

    protected Tenant(Parcel in) {
        tenantId = in.readString();
        tenantName = in.readString();
        tenantFatherName = in.readString();
        tenantPerAddress = in.readString();
        tenantPrevAddress = in.readString();
        tenantOccupation = in.readString();
        tenantWorkAddress = in.readString();
        noOfPeople = in.readString();
        tenantPhoneNumber = in.readString();
        tenantPhoneNumber2 = in.readString();
        tenantPhoneNumber3 = in.readString();
        propertyName = in.readString();
        tenantRent = in.readString();
        advanceAmount = in.readString();
        admissionDate = in.readString();
        docType = in.readString();
        tenantNotes = in.readString();
        imgUrl = in.readString();
        docUrl = in.readString();
        payday = in.readString();
        age = in.readString();// Read payday from parcel
    }

    public static final Creator<Tenant> CREATOR = new Creator<Tenant>() {
        @Override
        public Tenant createFromParcel(Parcel in) {
            return new Tenant(in);
        }

        @Override
        public Tenant[] newArray(int size) {
            return new Tenant[size];
        }
    };

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantFatherName() {
        return tenantFatherName;
    }

    public void setTenantFatherName(String tenantFatherName) {
        this.tenantFatherName = tenantFatherName;
    }

    public String getTenantPerAddress() {
        return tenantPerAddress;
    }

    public void setTenantPerAddress(String tenantPerAddress) {
        this.tenantPerAddress = tenantPerAddress;
    }

    public String getTenantPrevAddress() {
        return tenantPrevAddress;
    }

    public void setTenantPrevAddress(String tenantPrevAddress) {
        this.tenantPrevAddress = tenantPrevAddress;
    }

    public String getTenantOccupation() {
        return tenantOccupation;
    }

    public void setTenantOccupation(String tenantOccupation) {
        this.tenantOccupation = tenantOccupation;
    }

    public String getTenantWorkAddress() {
        return tenantWorkAddress;
    }

    public void setTenantWorkAddress(String tenantWorkAddress) {
        this.tenantWorkAddress = tenantWorkAddress;
    }

    public String getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(String noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public String getTenantPhoneNumber() {
        return tenantPhoneNumber;
    }

    public void setTenantPhoneNumber(String tenantPhoneNumber) {
        this.tenantPhoneNumber = tenantPhoneNumber;
    }

    public String getTenantPhoneNumber2() {
        return tenantPhoneNumber2;
    }

    public void setTenantPhoneNumber2(String tenantPhoneNumber2) {
        this.tenantPhoneNumber2 = tenantPhoneNumber2;
    }

    public String getTenantPhoneNumber3() {
        return tenantPhoneNumber3;
    }

    public void setTenantPhoneNumber3(String tenantPhoneNumber3) {
        this.tenantPhoneNumber3 = tenantPhoneNumber3;
    }

    public String getTenantRent() {
        return tenantRent;
    }

    public void setTenantRent(String tenantRent) {
        this.tenantRent = tenantRent;
    }

    public String getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(String advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTenantNotes() {
        return tenantNotes;
    }

    public void setTenantNotes(String tenantNotes) {
        this.tenantNotes = tenantNotes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getPayday() {
        return payday;
    }

    public void setPayday(String payday) {
        this.payday = payday;
    }

    // ... (existing writeToParcel method)

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tenantId);
        dest.writeString(tenantName);
        dest.writeString(tenantFatherName);
        dest.writeString(tenantPerAddress);
        dest.writeString(tenantPrevAddress);
        dest.writeString(tenantOccupation);
        dest.writeString(tenantWorkAddress);
        dest.writeString(noOfPeople);
        dest.writeString(tenantPhoneNumber);
        dest.writeString(tenantPhoneNumber2);
        dest.writeString(tenantPhoneNumber3);
        dest.writeString(propertyName);
        dest.writeString(tenantRent);
        dest.writeString(advanceAmount);
        dest.writeString(admissionDate);
        dest.writeString(docType);
        dest.writeString(tenantNotes);
        dest.writeString(imgUrl);
        dest.writeString(docUrl);
        dest.writeString(payday);// Write payday to parcel
        dest.writeString(age);// Write payday to parcel
    }
}
