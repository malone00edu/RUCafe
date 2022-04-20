package com.example.rucafe.models;

/**
 * Abstract class containing add and removing methods for an order.
 * @author Taze Balbosa, Yulie Ying
 */
public interface Customizable {
    /**
     * Adds an item to an order.
     * @param obj Object to be added to order.
     * @return true if added successfully, false otherwise.
     */
    boolean add(Object obj);
    /**
     * Removes an item from an order.
     * @param obj Object to be removed from order.
     * @return true if removed successfully, false otherwise.
     */
    boolean remove(Object obj);
}
