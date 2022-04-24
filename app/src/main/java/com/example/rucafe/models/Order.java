package com.example.rucafe.models;

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
    private static final int NOT_FOUND = -1;
    private static final double ZERO = 0;

    private double salesTax = ZERO;
    private double subTotal = ZERO;
    private double total = ZERO;

    private MenuItem menuItem = new MenuItem(1);
    private int orderId;
    private ArrayList<MenuItem> orderList;
    private static Order currentOrder;

    /**
     * Creates an Order object that stores information for added menu items.
     */
    public Order() {
        this.orderId = INITIAL_ID;
        orderList = new ArrayList<>();
    }

    /**
     * Accesses and returns the value of the order number.
     * @return the value of the order number.
     */
    public int getOrderId() {
        return this.orderId;
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
    public ArrayList<MenuItem> getOrderList() {
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
     * Accesses and returns the ArrayList size of the Order object.
     * @return The ArrayList size of the Order Object.
     */
    public int getListSize() {
        return orderList.size();
    }

    /**
     * Accesses and returns a MenuItem of the ArrayList within the Order object.
     * @param index The index within the ArrayList
     * @return The MenuItem within the ArrayList of the Order object.
     */
    public String getMenuItem(int index) {
        return orderList.get(index).toString();
    }

    /**
     * Adds a MenuItem object to the ArrayList within the Order object.
     * @param obj The MenuItem  to be added to an ArrayList object.
     * @return True if MenuItem object was added, false if otherwise.
     */
    @Override
    public boolean add(Object obj) {
        if (obj != null) {
            menuItem = (MenuItem) obj;
            orderList.add(menuItem);
            this.subTotal += menuItem.price;
            return true;
        }
        return false;
    }

    /**
     * Removes a MenuItem object from the ArrayList within the Order object.
     * @param obj The MenuItem to be removed from an ArrayList object.
     * @return True if MenuItem object was removed, false if otherwise.
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
     * Calculates the total price of the Order object and returns the value.
     * @return The value of the total price.
     */
    public double calcTotalOrder() {
        this.total = subTotal + salesTax;
        return total;
    }

    /**
     * Finds and returns the index of the MenuItem within the ArrayList of the Order object.
     * @param itemId The ID of the MenuItem in question.
     * @return The index of the MenuItem if found, returns NOT_FOUND if otherwise.
     */
    public int seekMenuItem(String itemId) {
        for (int i = 0; i < orderList.size(); i++) {
            if (itemId.equals(orderList.get(i).toString())) return i;
        }
        return NOT_FOUND;
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
    @Override
    public String toString() {
        String orderStringFormat = "";
        String totalStringFormat = "$" + DECIMAL_FORMAT.format(this.total);
        orderStringFormat += "Order #" + this.orderId + "::Total: " + totalStringFormat + "\n";
        for (int i = 0; i < orderList.size(); i++) {
            orderStringFormat += orderList.get(i).toString() + "\n";
        }
        return orderStringFormat;
    }
}
