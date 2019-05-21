package com.basharkablan.finalproject;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String image;
    private String price;
    private String details;

    public Item() { }

    public Item(String name, String image, String price, String details){
        this.name = name;
        this.image = image;
        this.price = price;
        this.details = details;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails(){
        return this.details;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public int compareTo(Item other) {
        return  this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
