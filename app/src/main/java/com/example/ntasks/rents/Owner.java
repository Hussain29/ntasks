package com.example.ntasks.rents;

import android.os.Parcel;

public class Owner {
        private String ownerId;
        private String ownerName;
        private String ownerAddress;
        private String ownerEmail;
        private String ownerPhone1;
        private String ownerPhone2;
        private String ownerPhone3; // New field
        private String ownerNotes;
        private String userId;

        public Owner() {
            // Default constructor required for Firebase
        }

        public Owner(String ownerId, String ownerName, String ownerAddress, String ownerEmail, String ownerPhone1, String ownerPhone2, String ownerPhone3, String ownerNotes, String userId) {
            this.ownerId = ownerId;
            this.ownerName = ownerName;
            this.ownerAddress = ownerAddress;
            this.ownerEmail = ownerEmail;
            this.ownerPhone1 = ownerPhone1;
            this.ownerPhone2 = ownerPhone2;
            this.ownerPhone3 = ownerPhone3;
            this.ownerNotes = ownerNotes;
            this.userId = userId;
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
        }

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
    }



}
