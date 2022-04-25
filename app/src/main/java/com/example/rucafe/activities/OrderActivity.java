package com.example.rucafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rucafe.MainActivity;
import com.example.rucafe.R;
import com.example.rucafe.models.MenuItem;
import com.example.rucafe.models.Order;

import java.text.DecimalFormat;

/**
 *
 */
public class OrderActivity extends AppCompatActivity {
    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("$###,##0.00");

    private TextView tv_totalPrice, tv_salesTax, tv_subtotal;
    private ListView lv_orderListView;
    private Button btn_removeItem, btn_placeOrder;
    private Order currentOrder;
    private MenuItem currentItem;
    double total, subtotal, salesTax;

    /**
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        currentItem = null;
        currentOrder = Order.getCurrentOrder();

        tv_totalPrice = (TextView) findViewById(R.id.tv_totalOrder);
        tv_subtotal = (TextView) findViewById(R.id.tv_subtotalOrder);
        tv_salesTax = (TextView) findViewById(R.id.tv_salesTaxOrder);
        lv_orderListView = (ListView) findViewById(R.id.lv_listOrder);
        btn_placeOrder = (Button) findViewById(R.id.btn_placeOrder);
        btn_removeItem = (Button) findViewById(R.id.btn_removeItemOrder);

        lv_orderListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_orderListView.setSelector(R.color.design_default_color_primary);
    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Order list view on item click listener
        lv_orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentItem = (MenuItem) lv_orderListView.getItemAtPosition(position);
                btn_removeItem.setEnabled(true);
            }
        });

        // Remove item from order listener
        btn_removeItem.setOnClickListener(v -> {
            this.removeItemFromOrder();
        });

        // Place order listener
        btn_placeOrder.setOnClickListener(v -> {
            this.placeOrder();
        });

        // Update UI and recompute price
        this.refreshUI();
        this.calculateAndDisplayPrice();
    }

    /**
     *
     */
    private void removeItemFromOrder() {
        currentOrder.remove(this.currentItem);
        // Update UI and recompute price
        this.refreshUI();
        this.calculateAndDisplayPrice();
    }

    /**
     *
     */
    private void refreshUI() {
        // Update UI
        lv_orderListView.clearChoices();
        lv_orderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentOrder.getOrderList()));
        this.btn_removeItem.setEnabled(false);

        // Check if place order button should be disabled
        btn_placeOrder.setEnabled(!this.currentOrder.isMenuEmpty());
    }

    /**
     *
     */
    private void calculateAndDisplayPrice() {
        // reset subtotal and recalc
        subtotal = currentOrder.getSubTotal();
        // Update subtotal formatted as currency
        tv_subtotal.setText(DECIMAL_FORMAT.format(subtotal));

        // reset sales tax and recalc
        salesTax = currentOrder.calcSalesTax();
        // Update sales tax formatted as currency
        tv_salesTax.setText(DECIMAL_FORMAT.format(salesTax));

        // reset total price and recalc
        total = subtotal + salesTax;
        // Update total formatted as currency
        tv_totalPrice.setText(DECIMAL_FORMAT.format(total));
    }

    /**
     *
     */
    private void placeOrder() {
        // finalize the store order, which adds it to StoreOrders
        currentOrder.submitOrder();

        // Show toast
        Toast.makeText(getBaseContext(), R.string.order_successfully_placed, Toast.LENGTH_SHORT).show();

        // Navigate back
        Intent gotoMainActivity = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivity);
    }
}
