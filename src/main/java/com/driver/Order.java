package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id = id;
        // The deliveryTime has to be converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        String[] deliveryTimeArray = deliveryTime.split(":");
        int newDeliveryTime = Integer.parseInt(deliveryTimeArray[0])*60 + Integer.parseInt(deliveryTimeArray[1]);

        this.deliveryTime = newDeliveryTime;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    //why its done, need to find out
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", deliveryTime=" + deliveryTime +
                '}';
    }

}



