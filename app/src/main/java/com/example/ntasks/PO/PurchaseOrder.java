package com.example.ntasks.PO;

import android.os.Parcel;
import android.os.Parcelable;

public class PurchaseOrder implements Parcelable {

    private String poId;
    private String poSubject;
    private String poRemarks;
    private String assignedUser;
    private String assigner;
    private String client;
    private String selectedDate;
    private String status;
    private String poAttachmentUrl;

    public PurchaseOrder() {
        // Default constructor required for calls to DataSnapshot.getValue(PurchaseOrder.class)
    }

    public PurchaseOrder(String poId, String poSubject, String poRemarks, String client, String selectedDate, String assignedUser, String assigner, String status,
                         String poAttachmentUrl) {
        this.poId = poId;
        this.poSubject = poSubject;
        this.poRemarks = poRemarks;
        this.client = client;
        this.selectedDate = selectedDate;
        this.assignedUser = assignedUser;
        this.assigner = assigner;
        this.status = status;
        this.poAttachmentUrl = poAttachmentUrl;
    }

    protected PurchaseOrder(Parcel in) {
        poId = in.readString();
        poSubject = in.readString();
        poRemarks = in.readString();
        client = in.readString();
        selectedDate = in.readString();
        assignedUser = in.readString();
        assigner = in.readString();
        status = in.readString();
        poAttachmentUrl = in.readString();
    }

    public static final Creator<PurchaseOrder> CREATOR = new Creator<PurchaseOrder>() {
        @Override
        public PurchaseOrder createFromParcel(Parcel in) {
            return new PurchaseOrder(in);
        }

        @Override
        public PurchaseOrder[] newArray(int size) {
            return new PurchaseOrder[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getPoSubject() {
        return poSubject;
    }

    public void setPoSubject(String poSubject) {
        this.poSubject = poSubject;
    }

    public String getPoRemarks() {
        return poRemarks;
    }

    public void setPoRemarks(String poRemarks) {
        this.poRemarks = poRemarks;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser1) {
        this.assignedUser = assignedUser1;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getPoAttachmentUrl() {
        return poAttachmentUrl;
    }

    public void setPoAttachmentUrl(String poAttachmentUrl) {
        this.poAttachmentUrl = poAttachmentUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poId);
        dest.writeString(poSubject);
        dest.writeString(poRemarks);
        dest.writeString(client);
        dest.writeString(selectedDate);
        dest.writeString(assignedUser);
        dest.writeString(assigner);
        dest.writeString(status);
        dest.writeString(poAttachmentUrl);
    }
}
