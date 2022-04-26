package com.example.rucafe.activities;

import java.io.Serializable;

/**
 * This class defines the data structure of an item to be displayed in the RecyclerView
 * @author Taze Balbosa, Yulie Ying
 */
public class Item implements Serializable {
	private static final int ONE = 1;
	private String itemName;
	private int image;
	private String unitPrice;
	 int itemQuantity;


	/**
	 * Parameterized constructor for an Item object.
	 * @param itemName
	 * @param image
	 * @param unitPrice
	 */
	public Item(String itemName, int image, String unitPrice, int itemQuantity) { //Added itemQuantity
		this.itemName = itemName;
		this.image = image;
		this.unitPrice = unitPrice;
		this.itemQuantity = itemQuantity;
	}

	/**
	 * Getter method that returns the item name of an item.
	 * @return the item name of an item.
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Getter method that returns the image of an item.
	 * @return the image of an item.
	 */
	public int getImage() {
		return image;
	}

	/**
	 * Getter method that returns the unit price.
	 * @return the unit price of the item.
	 */
	public String getUnitPrice() {
		return unitPrice;
	}

	/**
	 * Getter method that returns the item's quantity.
	 * @return
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}

	/**
	 * A method that adds one to item's quantity.
	 */
	public void addOne() {
		this.itemQuantity = itemQuantity + ONE;
	}

	/**
	 * A method that removes one from an items's quantity.
	 */
	public void removeOne() {
		this.itemQuantity = itemQuantity - ONE;
	}
}

