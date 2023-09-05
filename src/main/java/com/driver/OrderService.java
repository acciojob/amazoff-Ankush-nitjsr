package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        Order order = orderRepository.getOrderById(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        DeliveryPartner deliveryPartner = orderRepository.getPartnerById(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer numberOfUnassignedOrders() {
        return orderRepository.numberOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] timeArray = time.split(":");
        int newTime = Integer.parseInt(timeArray[0])*60 + Integer.parseInt(timeArray[1]);
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(newTime, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        //get the last time (in Integer) from repo
        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        //convert time (in Integer) to time (in String)
        //for hours
        String HH = String.valueOf(time / 60);
        if (HH.length() < 2) {
            HH = '0' + HH;
        }
        //for minutes
        String MM = String.valueOf(time % 60);
        if (MM.length() < 2) {
            MM = '0' + MM;
        }
        //final string to return
        return HH + ':' + MM;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
