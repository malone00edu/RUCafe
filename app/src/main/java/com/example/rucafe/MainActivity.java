package com.example.rucafe;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rucafe.activities.*;
import com.example.rucafe.models.Customizable;
import com.example.rucafe.models.Donut;
import com.example.rucafe.models.DonutType;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This is an example of an Activity where a RecyclerView is used.
 * @author Lily Chang
 */
public class MainActivity extends AppCompatActivity {

    private static final int ZERO = 0;

    // XML References
    private ImageButton coffeeImageButton;
    private ImageButton donutImageButton;
    private ImageButton orderImageButton;
    private ImageButton storeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign buttons by id
        coffeeImageButton = (ImageButton) findViewById(R.id.coffeeImageButton);
        donutImageButton = (ImageButton) findViewById(R.id.donutImageButton);
        orderImageButton = (ImageButton) findViewById(R.id.orderImageButton);
        storeImageButton = (ImageButton) findViewById(R.id.storeImageButton);

        // Add on click handler for order coffee button
        coffeeImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToOrderingCoffee();
            }
        });

        // Add on click listener for order donut button
        donutImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToOrderingDonut();
            }
        });

        // Add on click listener for current order button
        orderImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToCurrentOrder();
            }
        });

        // Add on click listener for store orders button
        storeImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToStoreOrders();
            }
        });
    }

    /**
     * Navigate to the OrderingCoffeeActivity
     */
    void navigateToOrderingCoffee() {
        Intent gotoOrderCoffee = new Intent(this, CoffeeActivity.class);
        startActivity(gotoOrderCoffee);
    }

    /**
     * Navigate to the OrderingDonutActivity
     */
    void navigateToOrderingDonut() {
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
        Intent gotoOrderDonut = new Intent(this, DonutActivity.class);
        gotoOrderDonut.putExtra("Donuts", donuts);
        startActivity(gotoOrderDonut);
    }

    /**
     * Navigate to the CurrentOrderActivity
     */
    void navigateToCurrentOrder() {
        Intent gotoCurrentOrder = new Intent(this, OrderActivity.class);
        startActivity(gotoCurrentOrder);
    }

    /**
     * Navigate to the StoreOrdersActivity
     */
    void navigateToStoreOrders() {
        Intent gotoStoreOrders = new Intent(this, StoreActivity.class);
        startActivity(gotoStoreOrders);
    }

    //private Donut setupDonutObjects(Donut donuts) {
    //}
}