package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class BusinessVendor implements Parcelable {

    private String companyId;
    private String companyName;
    private String companyShortName;
    private String companyMailingAddress;
    private String companyWebsite;
    private String companyCity;
    private String companyCountry;
    private String companyFax;
    private String companyTelephone;
    private String companyEmail;
    private String companyPocName;
    private String companyPocEmail;
    private String companyAltContact1;
    private String companyAltContact2;

    private String companyProducts;
    private String companyCrNumber;
    private String companyVatNumber;
    private String additionalInfo;
    private String googleLocationLink;

    private String bankName;
    private String beneficiaryName;
    private String accountNumber;
    private String bankAddress;
    private String ibanNumber;

    private String notes;

    // New fields added
    private String shopPicURL;
    private String bCardURL;

    public BusinessVendor() {
        // Default constructor required for calls to DataSnapshot.getValue(BusinessVendor.class)
    }

    public BusinessVendor(String companyId, String companyName, String companyShortName, String companyMailingAddress,
                          String companyWebsite, String companyCity, String companyCountry, String companyFax,
                          String companyTelephone, String companyEmail, String companyPocName, String companyPocEmail,
                          String companyAltContact1, String companyAltContact2, String companyProducts,
                          String companyCrNumber, String companyVatNumber, String additionalInfo, String googleLocationLink,
                          String bankName, String beneficiaryName, String accountNumber, String bankAddress,
                          String ibanNumber, String notes, String shopPicURL, String bCardURL) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyShortName = companyShortName;
        this.companyMailingAddress = companyMailingAddress;
        this.companyWebsite = companyWebsite;
        this.companyCity = companyCity;
        this.companyCountry = companyCountry;
        this.companyFax = companyFax;
        this.companyTelephone = companyTelephone;
        this.companyEmail = companyEmail;
        this.companyPocName = companyPocName;
        this.companyPocEmail = companyPocEmail;
        this.companyAltContact1 = companyAltContact1;
        this.companyAltContact2 = companyAltContact2;
        this.companyProducts = companyProducts;
        this.companyCrNumber = companyCrNumber;
        this.companyVatNumber = companyVatNumber;
        this.additionalInfo = additionalInfo;
        this.googleLocationLink = googleLocationLink;
        this.bankName = bankName;
        this.beneficiaryName = beneficiaryName;
        this.accountNumber = accountNumber;
        this.bankAddress = bankAddress;
        this.ibanNumber = ibanNumber;
        this.notes = notes;
        this.shopPicURL = shopPicURL;
        this.bCardURL = bCardURL;
    }

    protected BusinessVendor(Parcel in) {
        companyId = in.readString();
        companyName = in.readString();
        companyShortName = in.readString();
        companyMailingAddress = in.readString();
        companyWebsite = in.readString();
        companyCity = in.readString();
        companyCountry = in.readString();
        companyFax = in.readString();
        companyTelephone = in.readString();
        companyEmail = in.readString();
        companyPocName = in.readString();
        companyPocEmail = in.readString();
        companyAltContact1 = in.readString();
        companyAltContact2 = in.readString();
        companyProducts = in.readString();
        companyCrNumber = in.readString();
        companyVatNumber = in.readString();
        additionalInfo = in.readString();
        googleLocationLink = in.readString();
        bankName = in.readString();
        beneficiaryName = in.readString();
        accountNumber = in.readString();
        bankAddress = in.readString();
        ibanNumber = in.readString();
        notes = in.readString();
        shopPicURL = in.readString();
        bCardURL = in.readString();
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyMailingAddress() {
        return companyMailingAddress;
    }

    public void setCompanyMailingAddress(String companyMailingAddress) {
        this.companyMailingAddress = companyMailingAddress;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPocName() {
        return companyPocName;
    }

    public void setCompanyPocName(String companyPocName) {
        this.companyPocName = companyPocName;
    }

    public String getCompanyPocEmail() {
        return companyPocEmail;
    }

    public void setCompanyPocEmail(String companyPocEmail) {
        this.companyPocEmail = companyPocEmail;
    }

    public String getCompanyAltContact1() {
        return companyAltContact1;
    }

    public void setCompanyAltContact1(String companyAltContact1) {
        this.companyAltContact1 = companyAltContact1;
    }

    public String getCompanyAltContact2() {
        return companyAltContact2;
    }

    public void setCompanyAltContact2(String companyAltContact2) {
        this.companyAltContact2 = companyAltContact2;
    }

    public String getCompanyProducts() {
        return companyProducts;
    }

    public void setCompanyProducts(String companyProducts) {
        this.companyProducts = companyProducts;
    }

    public String getCompanyCrNumber() {
        return companyCrNumber;
    }

    public void setCompanyCrNumber(String companyCrNumber) {
        this.companyCrNumber = companyCrNumber;
    }

    public String getCompanyVatNumber() {
        return companyVatNumber;
    }

    public void setCompanyVatNumber(String companyVatNumber) {
        this.companyVatNumber = companyVatNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getGoogleLocationLink() {
        return googleLocationLink;
    }

    public void setGoogleLocationLink(String googleLocationLink) {
        this.googleLocationLink = googleLocationLink;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getIbanNumber() {
        return ibanNumber;
    }

    public void setIbanNumber(String ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getShopPicURL() {
        return shopPicURL;
    }

    public void setShopPicURL(String shopPicURL) {
        this.shopPicURL = shopPicURL;
    }

    public String getBCardURL() {
        return bCardURL;
    }

    public void setBCardURL(String bCardURL) {
        this.bCardURL = bCardURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyId);
        dest.writeString(companyName);
        dest.writeString(companyShortName);
        dest.writeString(companyMailingAddress);
        dest.writeString(companyWebsite);
        dest.writeString(companyCity);
        dest.writeString(companyCountry);
        dest.writeString(companyFax);
        dest.writeString(companyTelephone);
        dest.writeString(companyEmail);
        dest.writeString(companyPocName);
        dest.writeString(companyPocEmail);
        dest.writeString(companyAltContact1);
        dest.writeString(companyAltContact2);
        dest.writeString(companyProducts);
        dest.writeString(companyCrNumber);
        dest.writeString(companyVatNumber);
        dest.writeString(additionalInfo);
        dest.writeString(googleLocationLink);
        dest.writeString(bankName);
        dest.writeString(beneficiaryName);
        dest.writeString(accountNumber);
        dest.writeString(bankAddress);
        dest.writeString(ibanNumber);
        dest.writeString(notes);
        dest.writeString(shopPicURL);
        dest.writeString(bCardURL);
    }
}