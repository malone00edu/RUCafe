package com.example.rucafe.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
     * Returns the matching order within the list of orders.
     * @param orderId the order number in question.
     * @return  the found order within the list of orders or null if not found.
     */
    public Order seekOrder(int orderId) {
        for (int i = 0; i < orderBook.size(); i++) {
            if (orderBook.get(i).getOrderId() == orderId) {
                return orderBook.get(i);
            }
        }
        return null;
    }

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
     * Exports the store orders to selected destination file.
     * @param selectedFile Destination file for store orders.
     * @return true if store orders were exported, false if otherwise.
     */
    public boolean exportToDatabase(File selectedFile) {
        try {
            FileWriter penAndBook = new FileWriter(selectedFile);
            String storeOrderString = "";
            storeOrderString = toString();
            penAndBook.write(storeOrderString);
            penAndBook.close();
        }
        catch (IOException error) {
            return false;
        }
        return true;
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
