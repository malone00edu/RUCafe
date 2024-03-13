package com.example.rucafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rucafe.MainActivity;
import com.example.rucafe.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.example.rucafe.model.Donut;
import com.example.rucafe.model.DonutType;
import com.example.rucafe.model.OnItemsClickListener;
import com.example.rucafe.model.Order;

/**
 * Activity for Donut ordering GUI display.
 * @author Taze Balbosa, Yulie Ying
 */
public class DonutActivity extends AppCompatActivity implements OnItemsClickListener {

    private static final int ZERO = 0;
    private static final int TYPE_1 = 0, TYPE_2 = 1, TYPE_3 = 2, TYPE_4 = 3, TYPE_5 = 4, TYPE_6 = 5,
            TYPE_7 = 6, TYPE_8 = 7, TYPE_9 = 8, TYPE_10 = 9, TYPE_11 = 10, TYPE_12 = 11;
    private static final int FIRST_THREE_DONUT_TYPES = 3;
    private static final int FIRST_SEVEN_DONUT_TYPES = 7;
    private ArrayList<Item> items = new ArrayList<>();
    ArrayList<Item> donutItems;
    ArrayList<Donut> donuts;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##0.00");

    private int [] itemImages = {R.drawable.yeast_donut, R.drawable.yeast_donut, R.drawable.yeast_donut,
            R.drawable.yeast_donut, R.drawable.cake_donut, R.drawable.cake_donut, R.drawable.cake_donut,
            R.drawable.cake_donut, R.drawable.donut_hole, R.drawable.donut_hole, R.drawable.donut_hole,
            R.drawable.donut_hole};

    private TextView subtotal;

    /**
     * Get the references of all instances of Views defined in the layout file, set up the list of
     * items to be display in the RecyclerView.
     * @param savedInstanceState Contains the previous pages loaded.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);
        donuts = (ArrayList<Donut>) getIntent().getSerializableExtra("Donuts");
        Button addToOrder = (Button) findViewById(R.id.btn_addToOrder);
        subtotal = (TextView) findViewById(R.id.tv_subtotal);
        subtotal.setText(getString(R.string.sub_total) + getString(R.string.dollar) + getString(R.string.default_price));

        RecyclerView rcview = findViewById(R.id.rcView_menu);
        donutItems = setupMenuItems();
        ItemsAdapter adapter = new ItemsAdapter(this, items);
        rcview.setAdapter(adapter);
        rcview.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListenerAdd(new ItemsAdapter.onItemClickListener(){
            /**
             * Listens for the click of an Item object's add button.
             * Adds subtotal of all donuts and displays the amount.
             * @param items The object to be clicked on.
             */
            @Override
            public void onItemClick(Item items){
                for(Item selectedDonutItem : donutItems) {
                    tallySelectedDonuts(selectedDonutItem, donuts);
                }
                subtotal.setText(getString(R.string.sub_total) + getString(R.string.dollar) +calculateAndDisplayPrice());
            }
        });

        adapter.setOnItemClickListenerRemove(new ItemsAdapter.onItemClickListener(){
            /**
             * Listens for the click of an Item object's remove button.
             * Adds subtotal of all donuts and displays the amount.
             * @param items The object to be clicked on.
             */
            @Override
            public void onItemClick(Item items){
                for(Item selectedDonutItem : donutItems) {
                    tallySelectedDonuts(selectedDonutItem, donuts);
                }
                subtotal.setText(getString(R.string.sub_total) + getString(R.string.dollar) + calculateAndDisplayPrice());
            }
        });

        addToOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * Listens for the click of a add to order button.
             * @param view The view to be clicked on.
             */
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Add to order");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    /**
                     * Add donut(s) to your order if respective button was clicked.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        for (Item selectedDonutItem : donutItems) {
                            tallySelectedDonuts(selectedDonutItem, donuts);
                        }
                        if (Double.parseDouble(calculateAndDisplayPrice()) > ZERO) {
                            for (Donut donut : donuts) {
                                if (donut.getQuantity() > ZERO) {
                                    Order.getCurrentOrder().add(donut);
                                }
                            }
                            returnToMainActivity();
                            Toast.makeText(DonutActivity.this,
                                    "Donut(s) added to your order.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(DonutActivity.this,
                                    " No donut(s) to add to your order.", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    /**
                     * Backs out of adding donut(s) to your order if respective button was clicked.
                     * @param dialog The dialog that received the click.
                     * @param which The button that was clicked.
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DonutActivity.this,
                                " Donut(s) was not added to your order.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    /**
     * Helper method that updates the Donut object by viewing an Item object's quantity
     * and adjusting accordingly.
     * @param selectedDonutItem The quantity selected from a particular donut object Item.
     * @param donuts The Donut objects whose quantity will be updated.
     */
    private void tallySelectedDonuts(Item selectedDonutItem, ArrayList<Donut> donuts) {
        switch (selectedDonutItem.getItemName()) {
            case "Yeast Donut: Maple":
                donuts.get(TYPE_1).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Yeast Donut: Glazed":
                donuts.get(TYPE_2).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Yeast Donut: Vanilla Glazed":
                donuts.get(TYPE_3).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Yeast Donut: Chocolate Glazed":
                donuts.get(TYPE_4).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Maple":
                donuts.get(TYPE_5).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Vanilla":
                donuts.get(TYPE_6).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Boston Creme":
                donuts.get(TYPE_7).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Chocolate Glazed":
                donuts.get(TYPE_8).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Custard":
                donuts.get(TYPE_9).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Lemon Cream":
                donuts.get(TYPE_10).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Raspberry Jelly":
                donuts.get(TYPE_11).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Strawberry Jelly":
                donuts.get(TYPE_12).setQuantity(selectedDonutItem.getItemQuantity());break;
            default:
        }
    }

    /**
     * Helper method to set up the data (the Model of the MVC).
     */
    private ArrayList<Item> setupMenuItems() {
        String[] itemNames = getResources().getStringArray(R.array.itemNames);
        for (int i = 0; i < itemNames.length; i++) {
            String unitPrice;
            if (i <= FIRST_THREE_DONUT_TYPES) unitPrice = String.valueOf(DonutType.YEAST_DONUT.getPrice());
            else if (i <= FIRST_SEVEN_DONUT_TYPES) unitPrice = String.valueOf(DonutType.CAKE_DONUT.getPrice());
            else unitPrice = String.valueOf(DonutType.DONUT_HOLE.getPrice());
            items.add(new Item(itemNames[i], itemImages[i], unitPrice, ZERO));
        }
        return items;
    }

    /**
     * Calculates the price of the current donut order to be displayed.
     * @return
     */
    private String calculateAndDisplayPrice() {
        double totalPrice = ZERO;
        for (Donut donut : donuts) {
            donut.itemPrice();
            totalPrice += donut.getPrice();
        }
        return DECIMAL_FORMAT.format(totalPrice);
    }

    /**
     * The listener method that must be implemented from OnItemsClickListener interface.
     * @param items
     */
    @Override
    public void onItemClick(Item items) {

    }

    /**
     * Returns the user to the main activity of this app.
     */
    private void returnToMainActivity() {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
    }
}
