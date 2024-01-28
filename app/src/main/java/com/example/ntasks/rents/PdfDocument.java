package com.example.ntasks.rents;

public class PdfDocument {
    private String docUrl;
    private String month;

    public PdfDocument() {
    }

    public PdfDocument(String docUrl, String month) {
        this.docUrl = docUrl;
        this.month = month;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
