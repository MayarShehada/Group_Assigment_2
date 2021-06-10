package com.birzeit.group_assignment_2.Models;

public class Cart {

    String id;
    String productName;
    String productPrice;
    String productPhoto;

    public Cart(String id, String productName, String productPrice, String productPhoto) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPhoto = productPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productPhoto='" + productPhoto + '\'' +
                '}';
    }
}
