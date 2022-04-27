package com.example.rucafe.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//**************
import android.widget.*; //Java automatically consolidated all the imports for widgets to this.
//**************
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rucafe.R;
import com.example.rucafe.models.OnItemsClickListener;

import java.util.ArrayList;
/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * Must extend RecyclerView.Adapter, which will enforce you to implement 3 methods:
 *      1. onCreateViewHolder, 2. onBindViewHolder, and 3. getItemCount
 * @author Taze Balbosa, Yulie Ying
 */
 public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    private static final int ZERO = 0;
    private OnItemsClickListener addListener = null;
    private OnItemsClickListener removeListener = null;
    Item addItem, removeItem;
    DonutActivity adapter;

    private Context context;
    private ArrayList<Item> items;

    /**
     * Constructor used to initialize the adapter with an arraylist of items.
     * @param context Used to initialize the adapter.
     * @param items An arraylist of item objects.
     */
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }
    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        adapter = new DonutActivity();
        return new ItemsHolder(view);
    }


    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        holder.tv_name.setText(items.get(position).getItemName());
        holder.tv_price.setText(items.get(position).getUnitPrice());
        holder.im_item.setImageResource(items.get(position).getImage());
        holder.tv_quantity.setText(String.valueOf(items.get(position).getItemQuantity()));
        addItem = items.get(position);
        removeItem = items.get(position);
        holder.setAddButtonOnClick();
        holder.setRemoveButtonOnClick();
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price, tv_quantity;
        private ImageView im_item;
        private Button btn_add, btn_remove;
        private ConstraintLayout parentLayout;

        /**
         * A constructor method for the ItemsHolder that takes views to hold.
         * @param itemView
         */
        public ItemsHolder( View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_flavor);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            im_item = itemView.findViewById(R.id.im_item);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            tv_quantity.setText(String.valueOf(R.string.initial_quantity));
        }

        /**
         * A method that listens for the click of the add quantity button
         * Adds one to the respective item's quantity.
         */
        private void setAddButtonOnClick() {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.get(getAdapterPosition()).addOne();
                    tv_quantity.setText(String.valueOf(items.get(getAdapterPosition()).getItemQuantity()));
                    if (addListener != null) {
                        addListener.onItemClick(addItem);
                    }
                }
            });
        }

        /**
         * A method that listens for the click of the remove quantity button.
         * Removes one from the respective item's quantity.
         */
        private void setRemoveButtonOnClick() {
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (items.get(getAdapterPosition()).getItemQuantity() > ZERO) {
                        items.get(getAdapterPosition()).removeOne();
                        tv_quantity.setText(String.valueOf(items.get(getAdapterPosition()).getItemQuantity()));
                        if (removeListener != null) {
                            removeListener.onItemClick(removeItem);
                        }
                    }
                }
            });
        }
    }

    /**
     *
     * @param addListener Listens for the click of the add button.
     */
    public void setOnItemClickListenerAdd(OnItemsClickListener addListener) {
        this.addListener = addListener;
    }

    /**
     *
     * @param removeListener Listens for the click ofthe remove button.
     */
    public void setOnItemClickListenerRemove(OnItemsClickListener removeListener) {
        this.removeListener = removeListener;
    }

    /**
     * The method that must be implemented by the decree of the OnItemsClickListener Interface.
     */
    public static class onItemClickListener implements OnItemsClickListener {
        @Override
        public void onItemClick(Item items) {
        }
    }

}
