package com.example.restaurantpicker.Models;

/**
 * Created by Shoukhin on 5/29/2018.
 */

public class Restaurant {
    private String id;
    private String name;
    private String phone;
    private String image;

    public String getId() {
        return id;
    }

    public Restaurant() {
    }

    public Restaurant(String id, String name, String phone, String image) {

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
