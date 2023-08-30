package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderDb = new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartnerDb = new HashMap<>();
    HashMap<String, List<String>> partnerOrderPair = new HashMap<>();
    HashMap<String, String> orderPartnerPair = new HashMap<>();

    public OrderRepository () {
        this.orderDb = new HashMap<>();
        this.deliveryPartnerDb = new HashMap<>();
        this.partnerOrderPair = new HashMap<>();
        this.orderPartnerPair = new HashMap<>();
    }

    public void addOrder(Order order) {
        orderDb.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner newPartner = new DeliveryPartner(partnerId);
        deliveryPartnerDb.put(partnerId, newPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderPartnerPair.put(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        Order order = orderDb.get(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        DeliveryPartner deliveryPartner = deliveryPartnerDb.get(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        int count = 0;
        count = partnerOrderPair.get(partnerId).size();
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrderPair.get(partnerId);
    }

    public List<String> getAllOrders() {

        //1st way
        //return new ArrayList<>(orderDb.keySet());
        //2nd way
        List<String> allOrders = new ArrayList<>();
        for(Map.Entry<String, Order> e : orderDb.entrySet()){
            allOrders.add(e.getKey());
        }
        return allOrders;
    }

    public Integer numberOfUnassignedOrders() {
        Integer totalOrders = orderDb.size();
        Integer assignedOrders = orderPartnerPair.size();
        Integer unassignedOrders = totalOrders - assignedOrders;
        return unassignedOrders;
    }
}
