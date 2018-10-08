package com.example.burn.cis425project.Model;

public class SellOrder {
    private String phone;
    private String name;
    private String eventName;
    private String eventPrice;

    public SellOrder() {
    }

    public SellOrder(String phone, String name, String eventName, String eventPrice) {
        this.phone = phone;
        this.name = name;
        this.eventName = eventName;
        this.eventPrice = eventPrice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }
}
