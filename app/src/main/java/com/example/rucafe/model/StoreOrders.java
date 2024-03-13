package com.example.rucafe.model;
import java.util.ArrayList;

/**
 * Stores and accesses store order information for StoreOrders object.
 * @author Taze Balbosa, Yulie Ying
 */
public class StoreOrders implements Customizable {

    private static final int INITIAL_ID = 1;

    private int orderId = INITIAL_ID;
    private Order order = new Order();
    protected ArrayList<Order> orderBook;
    private static StoreOrders currentStoreOrder = null;

    /**
     * Creates a StoreOrders object that stores information for placed orders.
     */
    public StoreOrders() {
        orderBook = new ArrayList<>();
    }

    /**
     * Accesses list of orders.
     * @return List of orders.
     */
    public ArrayList<Order> getOrderBook() {return orderBook;}

    /**
     * Checks to see if any orders were placed.
     * @return true if no orders were placed, false if otherwise.
     */
    public boolean isOrderBookEmpty() {
        return orderBook.isEmpty();
    }

    /**
     * Adds an order object to the list of orders.
     * @param obj Order object to be added.
     * @return true if order was added, false if otherwise.
     */
    public boolean add(Object obj) {
        if (obj != null) {
            order = (Order) obj;
            order.setOrderId(orderId);
            orderId++;
            orderBook.add(order);
            return true;
        }
        return false;
    }

    /**
     * Removes an order object to the list of orders.
     * @param obj Order object to be removed
     * @return true if order was removed, false if otherwise
     */
    public boolean remove(Object obj) {
        if (obj != null) {
            order = (Order) obj;
            if (orderBook.contains(order)) {
                orderBook.remove(order);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Creates and returns a StoreOrder object.
     * @return The created StoreOrder object.
     */
    public static StoreOrders getCurrentOrder() {
        if (currentStoreOrder == null) {
            currentStoreOrder = new StoreOrders();
        }
        return currentStoreOrder;
    }

    /**
     * Creates a string that is formatted with StoreOrder object information.
     * @return String with StoreOrder information.
     */
    @Override
    public String toString() {
        String allOrdersAsString = "";
        for (Order value : orderBook) {
            allOrdersAsString += value.toString() + "\n";
        }
        return allOrdersAsString;
    }
}
