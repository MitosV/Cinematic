package com.mitosv.cinematic.util;

public class Video {

    private String url;

    private String name;

    public Video(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Video(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

}
