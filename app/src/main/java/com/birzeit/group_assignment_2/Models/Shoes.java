package com.birzeit.group_assignment_2.Models;

public class Shoes {

    String id ;
    String name;
    String brand ;
    String categoryName ;
    String price ;
    String rate ;
    String photo ;

    public Shoes(String id, String name, String brand, String categoryName, String price, String rate, String photo) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.categoryName = categoryName;
        this.price = price;
        this.rate = rate;
        this.photo = photo;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", price='" + price + '\'' +
                ", rate='" + rate + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
