package com.example.ntasks;
public class File {
    public String name;
    public String url;
    public String timestamp;

    // Required default constructor for Firebase
    public File() {}

    public File(String name, String url, String timestamp) {
        this.name = name;
        this.url = url;
        this.timestamp = timestamp;
    }
}