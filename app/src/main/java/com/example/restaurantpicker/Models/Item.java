package com.example.restaurantpicker.Models;

/**
 * Created by Shoukhin on 6/1/2018.
 */

public class Item {
    private String id;
    private String name;
    private String price;
    private String imageURL;
    private String restaurantID;

    public Item() {

    }

    public Item(String id, String name, String price, String imageURL, String restaurantID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }
}
