package com.example.restaurantpicker.Models;

/**
 * Created by Shoukhin on 6/6/2018.
 */

public class OrderHistory {
    private String restaurnatName;
    private String itemName;
    private String amount;
    private String price;
    private String timestamp;

    public OrderHistory() {

    }

    public String getRestaurnatName() {
        return restaurnatName;
    }

    public void setRestaurnatName(String restaurnatName) {
        this.restaurnatName = restaurnatName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
