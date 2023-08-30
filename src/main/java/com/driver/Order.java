package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to be converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        String[] deliveryTimeArray = deliveryTime.split(":");
        int newDeliveryTime = Integer.parseInt(deliveryTimeArray[0]) + Integer.parseInt(deliveryTimeArray[1]);

        this.id = id;
        this.deliveryTime = newDeliveryTime;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
