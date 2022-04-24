package com.example.rucafe.activities;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rucafe.R;
import com.example.rucafe.models.Donut;
import com.example.rucafe.models.DonutType;
import com.example.rucafe.models.OnItemsClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * Must extend RecyclerView.Adapter, which will enforce you to implement 3 methods:
 *      1. onCreateViewHolder, 2. onBindViewHolder, and 3. getItemCount
 *
 * You must use the data type <thisClassName.yourHolderName>, in this example
 * <ItemAdapter.ItemHolder>. This will enforce you to define a constructor for the
 * ItemAdapter and an inner class ItemsHolder (a static class)
 * The ItemsHolder class must extend RecyclerView.ViewHolder. In the constructor of this class,
 * you do something similar to the onCreate() method in an Activity.
 * @author Lily Chang
 */
 public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    private static final int ZERO = 0;
    private OnItemsClickListener addListener = null;
    private OnItemsClickListener removeListener = null;
    Item addItem, removeItem;
    DonutActivity adapter;

    private Context context; //need the context to inflate the layout
    private ArrayList<Item> items; //need the data binding to each row of RecyclerView
    private ArrayList<Donut> viewDonuts;
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
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        viewDonuts = createDonuts();
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
        //assign values for each row
        holder.tv_name.setText(items.get(position).getItemName());
        holder.tv_price.setText(items.get(position).getUnitPrice());
        holder.im_item.setImageResource(items.get(position).getImage());
        holder.tv_quantity.setText(String.valueOf(items.get(position).getItemQuantity()));
        addItem = items.get(position);
        removeItem = items.get(position);
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return items.size(); //number of MenuItem in the array list.
    }



    /**
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price, tv_quantity;
        private ImageView im_item;
        private Button btn_add, btn_remove;
        private ConstraintLayout parentLayout; //this is the row layout

        int itemQuantity = ZERO;

        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_flavor);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            im_item = itemView.findViewById(R.id.im_item);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            tv_quantity.setText("0");
            setAddButtonOnClick(); //register the onClicklistener for the button on each row.
            setRemoveButtonOnClick();

            /* set onClickListener for the row layout,
             * clicking on a row will navigate to another Activity
             */
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ItemSelectedActivity.class);
                    intent.putExtra("ITEM", tv_name.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         *
         */

        private void setAddButtonOnClick() {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemQuantity++;
                    tv_quantity.setText(String.valueOf(itemQuantity));
                    items.get(getAdapterPosition()).setItemQuantity(itemQuantity);
                    viewDonuts.get(getAdapterPosition()).setQuantity(itemQuantity);
                    if (addListener != null) {
                        addListener.onItemClick(addItem);
                    }
                }
            });
        }

        private void setRemoveButtonOnClick() {
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemQuantity > ZERO) {
                        itemQuantity--;
                        tv_quantity.setText(String.valueOf(itemQuantity));
                        items.get(getAdapterPosition()).setItemQuantity(itemQuantity);
                        viewDonuts.get(getAdapterPosition()).setQuantity(itemQuantity);
                        if (removeListener != null) {
                            removeListener.onItemClick(removeItem);
                        }
                    }
                }
            });
        }
    }

    private ArrayList<Donut> createDonuts() {
        ArrayList<Donut> donuts = new ArrayList<>();
        donuts.add(new Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.getFlavor_1()));
        donuts.add(new Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.getFlavor_2()));
        donuts.add(new Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.getFlavor_3()));
        donuts.add(new Donut(ZERO, DonutType.YEAST_DONUT.getName(), DonutType.YEAST_DONUT.getFlavor_4()));
        donuts.add(new Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.getFlavor_1()));
        donuts.add(new Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.getFlavor_2()));
        donuts.add(new Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.getFlavor_3()));
        donuts.add(new Donut(ZERO, DonutType.CAKE_DONUT.getName(), DonutType.CAKE_DONUT.getFlavor_4()));
        donuts.add(new Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.getFlavor_1()));
        donuts.add(new Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.getFlavor_2()));
        donuts.add(new Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.getFlavor_3()));
        donuts.add(new Donut(ZERO, DonutType.DONUT_HOLE.getName(), DonutType.DONUT_HOLE.getFlavor_4()));
        return donuts;
    }

    public void setOnItemClickListenerAdd(OnItemsClickListener addListener) {
        this.addListener = addListener;
    }

    public void setOnItemsClickListenerRemove(OnItemsClickListener removeListener) {
        this.removeListener = removeListener;
    }

    public static class onItemClickListener implements OnItemsClickListener {
        @Override
        public void onItemClick(Item items) {
        }
    }
}
