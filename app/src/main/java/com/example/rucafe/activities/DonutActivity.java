package com.example.rucafe.activities;

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
import com.example.rucafe.models.*;

public class DonutActivity extends AppCompatActivity implements OnItemsClickListener{


    private static final int ZERO = 0;
    //Declare an instance of ArrayList to hold the items to be display with the RecyclerView
    private ArrayList<Item> items = new ArrayList<>();
    ArrayList<Item> donutItems;
    ArrayList<Donut> donuts;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##0.00");
    private String donutsPriceString = "";

    /* All the images associated with the menu items are stored in the res/drawable folder
     *  Each image are accessed with the resource ID, which is an integer.
     *  We need an array of integers to hold the resource IDs. Make sure the index of a given
     *  ID is consistent with the index of the associated menu item in the ArrayList.
     *  An image resource could also be an URI.
     */

    private int [] itemImages = {R.drawable.yeast_donut, R.drawable.yeast_donut, R.drawable.yeast_donut,
            R.drawable.yeast_donut, R.drawable.cake_donut, R.drawable.cake_donut, R.drawable.cake_donut,
            R.drawable.cake_donut, R.drawable.donut_hole, R.drawable.donut_hole, R.drawable.donut_hole,
            R.drawable.donut_hole};

    private String unitPrice = "";
    private int itemQuantity = ZERO;
    
    private TextView subtotal;
    private Button  addToOrder;

    /**
     * Get the references of all instances of Views defined in the layout file, set up the list of
     * items to be display in the RecyclerView.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);
        donuts = (ArrayList<Donut>) getIntent().getSerializableExtra("Donuts");
        addToOrder = (Button) findViewById(R.id.btn_addToOrder);
        subtotal = (TextView) findViewById(R.id.tv_subtotal);
        subtotal.setText("0.00");

        RecyclerView rcview = findViewById(R.id.rcView_menu);
        donutItems = setupMenuItems(); //add the list of items to the ArrayList
        ItemsAdapter adapter = new ItemsAdapter(this, items); //create the adapter
        rcview.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        rcview.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListenerAdd(new ItemsAdapter.onItemClickListener(){
            @Override
            public void onItemClick(Item items){
                //Set your TextView here when card is Clicked on
                for(Item selectedDonutItem : donutItems) {
                    tallySelectedDonuts(selectedDonutItem, donuts);
                }
                subtotal.setText(calculateAndDisplayPrice());
            }
        });

        adapter.setOnItemsClickListenerRemove(new ItemsAdapter.onItemClickListener(){
            @Override
            public void onItemClick(Item items){
                //Set your TextView here when card is Clicked on
                for(Item selectedDonutItem : donutItems) {
                    tallySelectedDonuts(selectedDonutItem, donuts);
                }
                subtotal.setText(calculateAndDisplayPrice());
            }
        });

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Add to order");
                //handle the "YES" click
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
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
                    //handle the "NO" click
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
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

    private void tallySelectedDonuts(Item selectedDonutItem, ArrayList<Donut> donuts) {
        switch (selectedDonutItem.getItemName()) {
            case "Yeast Donut: Maple":
                donuts.get(0).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Yeast Donut: Glazed":
                donuts.get(1).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Yeast Donut: Vanilla Glazed":
                donuts.get(2).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Yeast Donut: Chocolate Glazed":
                donuts.get(3).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Maple":
                donuts.get(4).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Vanilla":
                donuts.get(5).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Boston Creme":
                donuts.get(6).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Cake Donut: Chocolate Glazed":
                donuts.get(7).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Custard":
                donuts.get(8).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Lemon Cream":
                donuts.get(9).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Raspberry Jelly":
                donuts.get(10).setQuantity(selectedDonutItem.getItemQuantity());break;
            case "Donut Hole: Strawberry Jelly":
                donuts.get(11).setQuantity(selectedDonutItem.getItemQuantity());break;
            default:
        }
    }

    /**
     * Helper method to set up the data (the Model of the MVC).
     */
    private ArrayList<Item> setupMenuItems() {
        /*
         * Item names are defined in a String array under res/string.xml.
         * Your item names might come from other places, such as an external file, or the database
         * from the backend.
         */
        String[] itemNames = getResources().getStringArray(R.array.itemNames);
        /* Add the items to the ArrayList.
         * Note that I use hardcoded prices for demo purpose. This should be replaced by other
         * data sources.
         */

        for (int i = 0; i < itemNames.length; i++) {
            if (i <= 3) unitPrice = String.valueOf(DonutType.YEAST_DONUT.getPrice());
            else if (i <= 7) unitPrice = String.valueOf(DonutType.CAKE_DONUT.getPrice());
            else unitPrice = String.valueOf(DonutType.DONUT_HOLE.getPrice());
            items.add(new Item(itemNames[i], itemImages[i], unitPrice, itemQuantity));
        }
        return items;
    }

    private String calculateAndDisplayPrice() {
        double totalPrice = 0;
        for (Donut donut : donuts) {
            donut.itemPrice();
            totalPrice += donut.getPrice();
        }
        donutsPriceString = DECIMAL_FORMAT.format(totalPrice);
        return donutsPriceString;
    }

    @Override
    public void onItemClick(Item items) {

    }

    private void returnToMainActivity() {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
    }
}
