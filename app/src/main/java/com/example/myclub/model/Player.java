package com.example.myclub.model;

import java.io.Serializable;

public class Player  implements Serializable {
    private  String id;
    private  String name;
    private  int age ;
    private  double height;
    private  double weight;
    private  String phone;
    private  String email;
    private  String urlAvatar;
    private  String position;

    public Player() {
    }

    public Player(String name, int age, double height, double weight, String phone, String email, String urlAvatar, String position) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.phone = phone;
        this.email = email;
        this.urlAvatar = urlAvatar;
        this.position = position;
    }

    public Player(String id, String name, int age, double height, double weight, String phone, String email, String urlAvatar, String position) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.phone = phone;
        this.email = email;
        this.urlAvatar = urlAvatar;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
