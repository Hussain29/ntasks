package com.example.ntasks;
public class putPDF {

    public String name;
    public String url;

    public putPDF(){

    }
    public putPDF(String name, String url){
        this.name=name;
        this.url=url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}