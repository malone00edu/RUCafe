package com.example.rucafe.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Stores and accesses order information for Order object.
 * @author Taze Balbosa, Yulie Ying
 */
public class Order implements Customizable{

    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,##0.00");
    private static final double NJ_TAX = 0.06625;
    private static final int INITIAL_ID = 1;
    private static final double ZERO = 0;

    private double salesTax = ZERO;
    private double subTotal = ZERO;
    private double total = ZERO;

    private MenuItems menuItems = new MenuItems(1);
    private int orderId;
    private ArrayList<MenuItems> orderList;
    private static Order currentOrder;

    /**
     * Creates an Order object that stores information for added menu items.
     */
    public Order() {
        this.orderId = INITIAL_ID;
        orderList = new ArrayList<>();
    }

    /**
     * Sets the value of the order number.
     * @param orderId The value of the order number.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Accesses and returns the ArrayList of the Order object.
     * @return The menu list within the order object.
     */
    public ArrayList<MenuItems> getOrderList() {
        return orderList;
    }

    /**
     * Accesses and returns the value of the subtotal.
     * @return The value of the subtotal.
     */
    public double getSubTotal() {
        return this.subTotal;
    }

    /**
     * Adds a MenuItems object to the ArrayList within the Order object.
     * @param obj The MenuItems  to be added to an ArrayList object.
     * @return True if MenuItems object was added, false if otherwise.
     */
    @Override
    public boolean add(Object obj) {
        if (obj != null) {
            menuItems = (MenuItems) obj;
            orderList.add(menuItems);
            this.subTotal += menuItems.price;
            return true;
        }
        return false;
    }

    /**
     * Removes a MenuItems object from the ArrayList within the Order object.
     * @param obj The MenuItems to be removed from an ArrayList object.
     * @return True if MenuItems object was removed, false if otherwise.
     */
    @Override
    public boolean remove(Object obj) {
        if (obj != null && !orderList.isEmpty()) {
            subTotal -= orderList.get(orderList.indexOf(obj)).price;
            orderList.remove(obj);
            if(orderList.isEmpty()) subTotal = 0;
            return true;
        }
        return false;
    }

    /**
     * Calculates the sales tax of the Order object and returns the value.
     * @return The value of the calculated sales tax.
     */
    public double calcSalesTax() {
        this.salesTax = subTotal * NJ_TAX;
        return salesTax;
    }

    /**
     * Checks to see if the ArrayList within the order object is empty.
     * @return True is the ArrayList object is empty, false if otherwise.
     */
    public boolean isMenuEmpty() {
        return orderList.isEmpty();
    }

    /**
     * Creates a string that is formatted with Order object information.
     * @return String with Order information
     */

    /**
     * Creates an Order instance if one does not exist.
     * @return The created Order instance.
     */
    public static Order getCurrentOrder() {
        if(currentOrder == null) {
            currentOrder = new Order();
        }
        return currentOrder;
    }

    /**
     * Submits the current order to StoreOrders.
     */
    public void submitOrder() {
        StoreOrders.getCurrentOrder().add(this);
        currentOrder = null;
    }

    /**
     * Creates a string that is formatted with current Order object information.
     * @return String with Order information.
     */
    @Override
    public String toString() {
        String orderStringFormat = "";
        orderStringFormat += "Order #" + this.orderId + "\n";
        for (int i = 0; i < orderList.size(); i++) {
            orderStringFormat += orderList.get(i).toString() + "\n";
        }
        return orderStringFormat;
    }
}
