package com.example.rucafe.model;

import com.example.rucafe.activity.Item;

/**
 * An interface that lists for the click of an Item object in the RecyclerView of ItemsAdapter.
 * @author Taze Balbosa, Yulie Ying
 */
public interface OnItemsClickListener {

    /**
     * The method that listens for the click of an Item object.
     * @param items The item that is clicked on.
     */
    void onItemClick(Item items);

}
