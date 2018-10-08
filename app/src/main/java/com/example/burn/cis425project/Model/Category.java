package com.example.burn.cis425project.Model;

public class Category {
    private String Name;
    private String Image;
    private String Price;
    private String Description;

    public Category() {
    }

    public Category(String name, String image, String price, String description) {
        Name = name;
        Image = image;
        Price = price;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
