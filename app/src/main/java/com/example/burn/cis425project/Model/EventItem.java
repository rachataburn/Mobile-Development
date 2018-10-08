package com.example.burn.cis425project.Model;

public class EventItem {
    private String Image, MenuId, Name, Price;

    public EventItem() {
    }

    public EventItem(String image, String menuId, String name, String price) {
        Image = image;
        MenuId = menuId;
        Name = name;
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
