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
        this.orderDb = new HashMap<String, Order>();
        this.deliveryPartnerDb = new HashMap<String, DeliveryPartner>();
        this.partnerOrderPair = new HashMap<String, List<String>>();
        this.orderPartnerPair = new HashMap<String, String>();
    }

    public void addOrder(Order order) {
        orderDb.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner newPartner = new DeliveryPartner(partnerId);
        deliveryPartnerDb.put(partnerId, newPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if (orderDb.containsKey(orderId) && deliveryPartnerDb.containsKey(partnerId)){
            orderPartnerPair.put(orderId, partnerId);
            List<String> orderList = new ArrayList<>()  ;
            //add orders linked to the partner to partner order pair
            if (partnerOrderPair.containsKey(partnerId)){
                orderList = partnerOrderPair.get(partnerId);
            }
            orderList.add(orderId);
            partnerOrderPair.put(partnerId, orderList);

            //update the size of partner due to addition of new order to the partner
            DeliveryPartner deliveryPartner = deliveryPartnerDb.get(partnerId);
            deliveryPartner.setNumberOfOrders(orderList.size());
        }
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
        return partnerOrderPair.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrderPair.get(partnerId);
    }

    public List<String> getAllOrders() {

        //1st way
        //return new ArrayList<>(orderDb.keySet());
        //2nd way
        List<String> allOrders = new ArrayList<>();
        //1st way
//        for(String key : orderDb.keySet()){
//            allOrders.add(key);
//        }
        //2nd way
        for(Map.Entry<String, Order> e : orderDb.entrySet()){
            allOrders.add(e.getKey());
        }
        return allOrders;
    }

    public Integer numberOfUnassignedOrders() {
        return orderDb.size() - orderPartnerPair.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int newTime, String partnerId) {
        int count = 0;
        List<String> orders = new ArrayList<>();
        orders = partnerOrderPair.get(partnerId);
        for(String orderId : orders) {
            int deliveryTime = orderDb.get(orderId).getDeliveryTime();
            if (deliveryTime > newTime) count++;
        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastDeliveryTime = 0;
        List<String> allOrderIds = new ArrayList<>();
        allOrderIds = partnerOrderPair.get(partnerId);
        for(String orderId : allOrderIds){
            int deliveryTime = orderDb.get(orderId).getDeliveryTime();
            lastDeliveryTime = Math.max(deliveryTime, lastDeliveryTime);
        }
        // alternate way of above for loop
//        for(int i = 0; i<allOrderIds.size(); i++) {
//            if(lastDeliveryTime < orderDb.get(allOrderIds.get(i)).getDeliveryTime()) {
//                lastDeliveryTime = orderDb.get(allOrderIds.get(i)).getDeliveryTime();
//            }
//        }
        return lastDeliveryTime;
    }

    public void deletePartnerById(String partnerId) {
        //Get all orders by the delivery partner
        List<String> allOrdersByDeliveryPartner = new ArrayList<>();
        allOrdersByDeliveryPartner = partnerOrderPair.get(partnerId);
        //remove all orders from orderPartnerPair database to push all his assigned orders to unassigned orders
        for(String orderId : allOrdersByDeliveryPartner) {
            orderPartnerPair.remove(orderId);
        }
        //remove delivery partner from partnerOrderPair database
        partnerOrderPair.remove(partnerId);
        //at last remove delivery partner from delivery partner data base
        deliveryPartnerDb.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        //get delivery partner id assigned to the orderId
        String partnerId = orderPartnerPair.get(orderId);
        //delete orderId-partnerId pair from orderPartnerPair database
        orderPartnerPair.remove(orderId);
        //delete orderId from list of orders assigned to the delivery partner from partnerOderPair database
        partnerOrderPair.get(partnerId).remove(orderId);
        //update the number of orders now the delivery partner has
        deliveryPartnerDb.get(partnerId).setNumberOfOrders(partnerOrderPair.get(partnerId).size());
        //at last delete order from order database
        orderDb.remove(orderId);
    }
}
