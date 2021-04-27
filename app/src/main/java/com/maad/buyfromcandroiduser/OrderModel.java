package com.maad.buyfromcandroiduser;

public class OrderModel{

    private String userID;
    private String title;
    private int quantity;
    private String image;

    public OrderModel(){}

    public OrderModel(String userID, String title, int quantity, String image) {
        this.userID = userID;
        this.title = title;
        this.quantity = quantity;
        this.image = image;
    }

    public String getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }
}
