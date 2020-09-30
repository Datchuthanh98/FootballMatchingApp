package com.example.myclub.model;
import com.google.firebase.Timestamp;

public class Player {
    private String id;
    private String email;
    private String password;
    private String fullName;
    private String urlCover;
    private String urlAvatar;
    private String address;
    private Timestamp birthday;
    private String phone;
    private int height;
    private int weight;
    private String introduction;
    private PositionPlayer positionPlayer;
    private Location location;
    private Timestamp lastUpdateNotificationTimestamp;

    public Player() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public PositionPlayer getPositionPlayer() {
        return positionPlayer;
    }

    public void setPositionPlayer(PositionPlayer positionPlayer) {
        this.positionPlayer = positionPlayer;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Timestamp getLastUpdateNotificationTimestamp() {
        return lastUpdateNotificationTimestamp;
    }

    public void setLastUpdateNotificationTimestamp(Timestamp lastUpdateNotificationTimestamp) {
        this.lastUpdateNotificationTimestamp = lastUpdateNotificationTimestamp;
    }
}
