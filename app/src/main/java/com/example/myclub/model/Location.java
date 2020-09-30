package com.example.myclub.model;

public class Location {
    private double longitude;
    private double latitude;
    private String geoHash;

    public Location(double longitude, double latitude, String geoHash) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.geoHash = geoHash;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }
}
