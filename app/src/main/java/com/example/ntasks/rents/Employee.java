package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {

    private String empId;
    private String empName;
    private String empIqNo;
    private String empIqExp1;
    private String empIqExp2;
    private String empAddPer;
    private String empEmCon1;
    private String empEmCon2;
    private String empNat;
    private String empOcc;
    private String empBtDt1;
    private String empBtDt2;
    private String empCountry;
    private String empMStat;
    private String empRel;
    private String empIns1;
    private String empIns2;
    private String empIq1;
    private String empIq2;
    private String empDl1;
    private String empDl2;
    private String empPp1;
    private String empPp2;
    private String empDep;
    private String empPpNo;
    private String empPpCity;
    private String empPpDt;
    private String empPpExpDt;
    private String empVisaInfo;
    private String empDlNo;
    private String empDlDt;
    private String empDlExpDt;
    private String empHInsStartDt;
    private String empHInsEndDt;
    private String empNotes;

    private String photoUrl;  // Added field for photo URL
    private String docUrl;  // Added field for document URL

    public Employee() {
        // Default constructor required for calls to DataSnapshot.getValue(Employee.class)
    }

    public Employee(String empId, String empName, String empIqNo, String empIqExp1, String empIqExp2,
                    String empAddPer, String empEmCon1, String empEmCon2, String empNat, String empOcc,
                    String empBtDt1, String empBtDt2, String empCountry, String empMStat, String empRel,
                    String empIns1, String empIns2, String empIq1, String empIq2, String empDl1, String empDl2,
                    String empPp1, String empPp2, String empDep, String empPpNo, String empPpCity,
                    String empPpDt, String empPpExpDt, String empVisaInfo, String empDlNo, String empDlDt,
                    String empDlExpDt, String empHInsStartDt, String empHInsEndDt, String empNotes, String photoUrl, String docUrl) {
        this.empId = empId;
        this.empName = empName;
        this.empIqNo = empIqNo;
        this.empIqExp1 = empIqExp1;
        this.empIqExp2 = empIqExp2;
        this.empAddPer = empAddPer;
        this.empEmCon1 = empEmCon1;
        this.empEmCon2 = empEmCon2;
        this.empNat = empNat;
        this.empOcc = empOcc;
        this.empBtDt1 = empBtDt1;
        this.empBtDt2 = empBtDt2;
        this.empCountry = empCountry;
        this.empMStat = empMStat;
        this.empRel = empRel;
        this.empIns1 = empIns1;
        this.empIns2 = empIns2;
        this.empIq1 = empIq1;
        this.empIq2 = empIq2;
        this.empDl1 = empDl1;
        this.empDl2 = empDl2;
        this.empPp1 = empPp1;
        this.empPp2 = empPp2;
        this.empDep = empDep;
        this.empPpNo = empPpNo;
        this.empPpCity = empPpCity;
        this.empPpDt = empPpDt;
        this.empPpExpDt = empPpExpDt;
        this.empVisaInfo = empVisaInfo;
        this.empDlNo = empDlNo;
        this.empDlDt = empDlDt;
        this.empDlExpDt = empDlExpDt;
        this.empHInsStartDt = empHInsStartDt;
        this.empHInsEndDt = empHInsEndDt;
        this.empNotes = empNotes;
        this.photoUrl = photoUrl;
        this.docUrl = docUrl;

    }

    protected Employee(Parcel in) {
        empId = in.readString();
        empName = in.readString();
        empIqNo = in.readString();
        empIqExp1 = in.readString();
        empIqExp2 = in.readString();
        empAddPer = in.readString();
        empEmCon1 = in.readString();
        empEmCon2 = in.readString();
        empNat = in.readString();
        empOcc = in.readString();
        empBtDt1 = in.readString();
        empBtDt2 = in.readString();
        empCountry = in.readString();
        empMStat = in.readString();
        empRel = in.readString();
        empIns1 = in.readString();
        empIns2 = in.readString();
        empIq1 = in.readString();
        empIq2 = in.readString();
        empDl1 = in.readString();
        empDl2 = in.readString();
        empPp1 = in.readString();
        empPp2 = in.readString();
        empDep = in.readString();
        empPpNo = in.readString();
        empPpCity = in.readString();
        empPpDt = in.readString();
        empPpExpDt = in.readString();
        empVisaInfo = in.readString();
        empDlNo = in.readString();
        empDlDt = in.readString();
        empDlExpDt = in.readString();
        empHInsStartDt = in.readString();
        empHInsEndDt = in.readString();
        empNotes = in.readString();
        photoUrl = in.readString();
        docUrl = in.readString();

    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getEmpId() {
        return empId;
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

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpIqNo() {
        return empIqNo;
    }

    public void setEmpIqNo(String empIqNo) {
        this.empIqNo = empIqNo;
    }

    public String getEmpIqExp1() {
        return empIqExp1;
    }

    public void setEmpIqExp1(String empIqExp1) {
        this.empIqExp1 = empIqExp1;
    }

    public String getEmpIqExp2() {
        return empIqExp2;
    }

    public void setEmpIqExp2(String empIqExp2) {
        this.empIqExp2 = empIqExp2;
    }

    public String getEmpAddPer() {
        return empAddPer;
    }

    public void setEmpAddPer(String empAddPer) {
        this.empAddPer = empAddPer;
    }

    public String getEmpEmCon1() {
        return empEmCon1;
    }

    public void setEmpEmCon1(String empEmCon1) {
        this.empEmCon1 = empEmCon1;
    }

    public String getEmpEmCon2() {
        return empEmCon2;
    }

    public void setEmpEmCon2(String empEmCon2) {
        this.empEmCon2 = empEmCon2;
    }

    public String getEmpNat() {
        return empNat;
    }

    public void setEmpNat(String empNat) {
        this.empNat = empNat;
    }

    public String getEmpOcc() {
        return empOcc;
    }

    public void setEmpOcc(String empOcc) {
        this.empOcc = empOcc;
    }

    public String getEmpBtDt1() {
        return empBtDt1;
    }

    public void setEmpBtDt1(String empBtDt1) {
        this.empBtDt1 = empBtDt1;
    }

    public String getEmpBtDt2() {
        return empBtDt2;
    }

    public void setEmpBtDt2(String empBtDt2) {
        this.empBtDt2 = empBtDt2;
    }

    public String getEmpCountry() {
        return empCountry;
    }

    public void setEmpCountry(String empCountry) {
        this.empCountry = empCountry;
    }

    public String getEmpMStat() {
        return empMStat;
    }

    public void setEmpMStat(String empMStat) {
        this.empMStat = empMStat;
    }

    public String getEmpRel() {
        return empRel;
    }

    public void setEmpRel(String empRel) {
        this.empRel = empRel;
    }

    public String getEmpIns1() {
        return empIns1;
    }

    public void setEmpIns1(String empIns1) {
        this.empIns1 = empIns1;
    }

    public String getEmpIns2() {
        return empIns2;
    }

    public void setEmpIns2(String empIns2) {
        this.empIns2 = empIns2;
    }

    public String getEmpIq1() {
        return empIq1;
    }

    public void setEmpIq1(String empIq1) {
        this.empIq1 = empIq1;
    }

    public String getEmpIq2() {
        return empIq2;
    }

    public void setEmpIq2(String empIq2) {
        this.empIq2 = empIq2;
    }

    public String getEmpDl1() {
        return empDl1;
    }

    public void setEmpDl1(String empDl1) {
        this.empDl1 = empDl1;
    }

    public String getEmpDl2() {
        return empDl2;
    }

    public void setEmpDl2(String empDl2) {
        this.empDl2 = empDl2;
    }

    public String getEmpPp1() {
        return empPp1;
    }

    public void setEmpPp1(String empPp1) {
        this.empPp1 = empPp1;
    }

    public String getEmpPp2() {
        return empPp2;
    }

    public void setEmpPp2(String empPp2) {
        this.empPp2 = empPp2;
    }

    public String getEmpDep() {
        return empDep;
    }

    public void setEmpDep(String empDep) {
        this.empDep = empDep;
    }

    public String getEmpPpNo() {
        return empPpNo;
    }

    public void setEmpPpNo(String empPpNo) {
        this.empPpNo = empPpNo;
    }

    public String getEmpPpCity() {
        return empPpCity;
    }

    public void setEmpPpCity(String empPpCity) {
        this.empPpCity = empPpCity;
    }

    public String getEmpPpDt() {
        return empPpDt;
    }

    public void setEmpPpDt(String empPpDt) {
        this.empPpDt = empPpDt;
    }

    public String getEmpPpExpDt() {
        return empPpExpDt;
    }

    public void setEmpPpExpDt(String empPpExpDt) {
        this.empPpExpDt = empPpExpDt;
    }

    public String getEmpVisaInfo() {
        return empVisaInfo;
    }

    public void setEmpVisaInfo(String empVisaInfo) {
        this.empVisaInfo = empVisaInfo;
    }

    public String getEmpDlNo() {
        return empDlNo;
    }

    public void setEmpDlNo(String empDlNo) {
        this.empDlNo = empDlNo;
    }

    public String getEmpDlDt() {
        return empDlDt;
    }

    public void setEmpDlDt(String empDlDt) {
        this.empDlDt = empDlDt;
    }

    public String getEmpDlExpDt() {
        return empDlExpDt;
    }

    public void setEmpDlExpDt(String empDlExpDt) {
        this.empDlExpDt = empDlExpDt;
    }

    public String getEmpHInsStartDt() {
        return empHInsStartDt;
    }

    public void setEmpHInsStartDt(String empHInsStartDt) {
        this.empHInsStartDt = empHInsStartDt;
    }

    public String getEmpHInsEndDt() {
        return empHInsEndDt;
    }

    public void setEmpHInsEndDt(String empHInsEndDt) {
        this.empHInsEndDt = empHInsEndDt;
    }

    public String getEmpNotes() {
        return empNotes;
    }

    public void setEmpNotes(String empNotes) {
        this.empNotes = empNotes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(empId);
        dest.writeString(empName);
        dest.writeString(empIqNo);
        dest.writeString(empIqExp1);
        dest.writeString(empIqExp2);
        dest.writeString(empAddPer);
        dest.writeString(empEmCon1);
        dest.writeString(empEmCon2);
        dest.writeString(empNat);
        dest.writeString(empOcc);
        dest.writeString(empBtDt1);
        dest.writeString(empBtDt2);
        dest.writeString(empCountry);
        dest.writeString(empMStat);
        dest.writeString(empRel);
        dest.writeString(empIns1);
        dest.writeString(empIns2);
        dest.writeString(empIq1);
        dest.writeString(empIq2);
        dest.writeString(empDl1);
        dest.writeString(empDl2);
        dest.writeString(empPp1);
        dest.writeString(empPp2);
        dest.writeString(empDep);
        dest.writeString(empPpNo);
        dest.writeString(empPpCity);
        dest.writeString(empPpDt);
        dest.writeString(empPpExpDt);
        dest.writeString(empVisaInfo);
        dest.writeString(empDlNo);
        dest.writeString(empDlDt);
        dest.writeString(empDlExpDt);
        dest.writeString(empHInsStartDt);
        dest.writeString(empHInsEndDt);
        dest.writeString(empNotes);
        dest.writeString(photoUrl);
        dest.writeString(docUrl);
    }
}
